package common;

import im.qingtui.platform.common.SysConfigPlaceholder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author: hongya  Date: 16/7/22
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/META-INF/spring/*.xml"})
public class SysConfigPlaceholderTest {

    @Test
    public void testProps() {
        System.out.println(SysConfigPlaceholder.getStringProperty("jdbc.url"));
        System.out.println(SysConfigPlaceholder.getStringProperty("druid.maxOpenPreparedStatements"));
        System.out.println(SysConfigPlaceholder.getStringProperty("mybatis.mapper.basepackage"));
    }

}
