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
@ContextConfiguration(locations = {"classpath*:/conf/core.xml", "classpath:/conf/platform-mybatis.xml"})
public class SysConfigPlaceholderTest {

    @Test
    public void testProps() {
        System.out.println(SysConfigPlaceholder.getContextProperty("jdbc.url"));
        System.out.println(SysConfigPlaceholder.getContextProperty("druid.maxOpenPreparedStatements"));
        System.out.println(SysConfigPlaceholder.getContextProperty("mybatis.mapper.basepackage"));
    }

}
