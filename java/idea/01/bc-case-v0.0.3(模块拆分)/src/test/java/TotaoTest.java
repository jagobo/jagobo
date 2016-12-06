import com.bc.cas.manager.IndexManager;
import com.bc.cas.manager.IndexUtil;
import org.apache.log4j.helpers.LogLog;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

/**
 * @Copyright 2012-2016 duenboa 版权所有
 * @Author Created by Administrator on 2016/11/29.
 * @Version V 1.0.0
 * @Desc 测试总类
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring/application*.xml"})
public class TotaoTest {

    private LogLog logger = LogLog.class.newInstance();

    @Autowired
    private IndexManager indexManager;

    public TotaoTest() throws IllegalAccessException, InstantiationException {
    }


    @Test
    public void indexManagerTest() throws IOException {
        logger.warn("=============================================start");
        indexManager.index();
        logger.warn("==============================================over");

    }


    @Test
    public void indexUtilTest() {
        IndexUtil util = new IndexUtil();
        util.index();

    }


}
