import com.bc.cas.manager.IndexManager;

import org.apache.log4j.BasicConfigurator;
import org.apache.lucene.queryparser.classic.ParseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@ContextConfiguration(locations = {"classpath:spring/application*.xml"})
public class LuceneTest {
    static {
        BasicConfigurator.configure();
    }

    private static final Logger logger = LoggerFactory.getLogger(LuceneTest.class);

    @Autowired
    private IndexManager indexManager;


    @Test
    public void indexTest() throws IOException {
        logger.info("================start");
        indexManager.testCreateIndex();
        logger.info("================over");

    }

    @Test
    public void queryTest() throws IOException, ParseException {
        logger.info("================start");
        indexManager.testQuery();
        logger.info("================over");

    }

    @Test
    public void testTermQueryTest() throws IOException, ParseException {
        logger.info("================start");
        indexManager.testTermQuery();
        logger.info("================over");
    }

    @Test
    public void testNumericQueryTest() throws IOException, ParseException {
        logger.info("================start");
        indexManager.testNumericQuery();
        logger.info("================over");
    }


}
