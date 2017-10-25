package im.qingtui.platform.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 包扫描工具类，支持jar包中的类
 *
 * @author bowen
 * 2016/11/7 9:21
 */
public final class PackageScan {

    private static Logger logger = LoggerFactory.getLogger(PackageScan.class);

    private PackageScan() {
    }

    /**
     * 获取类集合
     *
     * @param packName 包名
     * @return 类集合
     */
    public static Set<Class<?>> getClasses(String packName) {
        return getClassList(packName, true, null);
    }

    /**
     * 根据包名获取class集合
     *
     * @param pkgName     包名
     * @param isRecursive 是否迭代
     * @param annotation  注解
     * @return 类集合
     */
    public static Set<Class<?>> getClassList(String pkgName, boolean isRecursive
            , Class<? extends Annotation> annotation) {
        // 第一个class类的集合
        Set<Class<?>> classes = new LinkedHashSet<Class<?>>();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();

        // 按文件的形式去查找
        String strFile = pkgName.replaceAll("\\.", "/");
        Enumeration<URL> urls = null;
        try {
            urls = loader.getResources(strFile);
        } catch (IOException e) {
            logger.error("包扫描异常", e);
        }
        if (urls != null) {
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                if (url != null) {
                    String protocol = url.getProtocol();
                    String pkgPath = url.getPath();
                    pkgPath = pkgPath.replaceAll("%20", " ");
                    if ("file".equals(protocol)) {
                        // 本地自己可见的代码
                        findClassName(classes, pkgName, pkgPath, isRecursive, annotation);
                    } else if ("jar".equals(protocol)) {
                        // 引用第三方jar的代码
                        try {
                            findClassName(classes, pkgName, url, isRecursive, annotation);
                        } catch (IOException e) {
                            logger.error("包扫描异常", e);
                        }
                    }
                }
            }
        }
        return classes;
    }

    private static void findClassName(Set<Class<?>> classes, String pkgName, String pkgPath, boolean isRecursive
            , Class<? extends Annotation> annotation) {
        if (classes == null) {
            return;
        }
        // 过滤出.class文件及文件夹
        File[] files = filterClassFiles(pkgPath);
        if (files != null) {
            for (File f : files) {
                String fileName = f.getName();
                if (f.isFile()) {
                    // .class 文件的情况
                    String clazzName = getClassName(pkgName, fileName);
                    addClassName(classes, clazzName, annotation);
                } else {
                    // 文件夹的情况
                    if (isRecursive) {
                        // 需要继续查找该文件夹/包名下的类
                        String subPkgName = pkgName + "." + fileName;
                        String subPkgPath = pkgPath + "/" + fileName;
                        findClassName(classes, subPkgName, subPkgPath, true, annotation);
                    }
                }
            }
        }
    }

    /**
     * 第三方Jar类库的引用。<br/>
     *
     * @throws IOException 文件读取错误
     */
    private static void findClassName(Set<Class<?>> classes, String pkgName, URL url, boolean isRecursive
            , Class<? extends Annotation> annotation) throws IOException {
        JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
        JarFile jarFile = jarURLConnection.getJarFile();
        Enumeration<JarEntry> jarEntries = jarFile.entries();
        while (jarEntries.hasMoreElements()) {
            JarEntry jarEntry = jarEntries.nextElement();
            String jarEntryName = jarEntry.getName(); // 类似：sun/security/internal/interfaces/TlsMasterSecret.class
            String clazzName = jarEntryName.replace("/", ".");
            int endIndex = clazzName.lastIndexOf(".");
            String prefix = null;
            if (endIndex > 0) {
                String prefixName = clazzName.substring(0, endIndex);
                endIndex = prefixName.lastIndexOf(".");
                if (endIndex > 0) {
                    prefix = prefixName.substring(0, endIndex);
                }
            }
            if (prefix != null && jarEntryName.endsWith(".class")) {
                if (prefix.equals(pkgName)) {
                    addClassName(classes, clazzName, annotation);
                } else if (isRecursive && prefix.startsWith(pkgName)) {
                    // 遍历子包名：子类
                    addClassName(classes, clazzName, annotation);
                }
            }
        }
    }

    private static File[] filterClassFiles(String pkgPath) {
        if (pkgPath == null) {
            return null;
        }

        File file = new File(pkgPath);

        // 接收 .class 文件 或 类文件夹
        return new File(pkgPath).listFiles(new FileFilter() {
            public boolean accept(File file) {
                return (file.isFile() && file.getName().endsWith(".class")) || file.isDirectory();
            }
        });
    }

    private static String getClassName(String pkgName, String fileName) {
        int endIndex = fileName.lastIndexOf(".");
        String clazz = null;
        if (endIndex >= 0) {
            clazz = fileName.substring(0, endIndex);
        }
        String clazzName = null;
        if (clazz != null) {
            clazzName = pkgName + "." + clazz;
        }
        return clazzName;
    }

    private static void addClassName(Set<Class<?>> clazzList, String clazzName
            , Class<? extends Annotation> annotation) {
        if (clazzList != null && clazzName != null) {
            Class<?> clazz = null;
            try {
                clazz = Class.forName(clazzName.replaceAll(".class", ""));
            } catch (ClassNotFoundException e) {
                System.out.println("");
            }
            if (clazz != null) {
                if (annotation == null) {
                    clazzList.add(clazz);
                } else if (clazz.isAnnotationPresent(annotation)) {
                    clazzList.add(clazz);
                }
            }
        }
    }
}
