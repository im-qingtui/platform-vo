package im.qingtui.platform.web.utils;

import im.qingtui.platform.sensitive.SensitiveLevel;

/**
 * 敏感参数
 *
 * @author yiya
 */
public class SensitiveParam {

    /**
     * 敏感参数名称
     */
    private String paramName = "";

    /**
     * 敏感级别 完全隐藏/部分显示
     */
    private SensitiveLevel level;

    /**
     * 打码比例
     */
    private double rate;

    /**
     * 参数所在uri
     */
    private String path;

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public SensitiveLevel getLevel() {
        return level;
    }

    public void setLevel(SensitiveLevel level) {
        this.level = level;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SensitiveParam that = (SensitiveParam) o;

        return paramName.equals(that.paramName);
    }

    @Override
    public int hashCode() {
        return paramName.hashCode();
    }
}
