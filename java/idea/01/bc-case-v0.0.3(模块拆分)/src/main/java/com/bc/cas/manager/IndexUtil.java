package com.bc.cas.manager;

import com.bc.cas.dao.BookDao;
import com.bc.cas.model.entity.Book;
import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import org.apache.log4j.helpers.LogLog;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


/**
 * @Copyright 2012-2016 duenboa 版权所有
 * @Author Created by Administrator on 2016/11/29.
 * @Version V 1.0.0
 * @Desc 索引创建查询工具类
 */

@Component
public class IndexUtil {

    private static final Logger logger = LoggerFactory.getLogger(IndexUtil.class);

    @Autowired
    private BookDao bookDao;

    static FSDirectory dir;

    static {
        try {
            dir = FSDirectory.open(Paths.get("d:/lucene/lucene052_index01"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 创建索引 -common
     */
    @Test
    public void createIndex(List<Document> docs) throws IOException {

        if (docs == null) return;

        // 自定义停用词
        List<String> strings = Arrays.asList("的", "在", "了", "呢", "，", "0", "：", ",", "是", "这", "那", "么");
        CharArraySet unUsefulWorlds = new CharArraySet(strings, true);

        // 加入系统默认停用词
        Iterator<Object> itor = SmartChineseAnalyzer.getDefaultStopSet().iterator();
        while (itor.hasNext()) unUsefulWorlds.add(itor.next());
        Analyzer analyzer = new SmartChineseAnalyzer(unUsefulWorlds);
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter writer = new IndexWriter(dir, config);
        writer.addDocuments(docs);
        writer.commit();
        writer.close();
    }


    /**
     * 执行查询 -common
     *
     * @param query
     * @return List<documentId> 返回文档id集合
     * @throws IOException
     * @throws ParseException
     */
    private static List<Long> doQuery(Query query) throws IOException, ParseException {
        IndexReader reader = DirectoryReader.open(dir);
        IndexSearcher searcher = new IndexSearcher(reader);

        //执行query,获取指定条数的顶行记录
        TopDocs topDocs = searcher.search(query, 10);
        System.out.println("搜索出的总记录数为: " + topDocs.totalHits);
        //评分文档集合
        ScoreDoc[] docs = topDocs.scoreDocs;
        List<Long> docIds = Lists.newArrayList();

        for (ScoreDoc doc : docs) {
            //获取索引文档id
            int id = doc.doc;
            //根据索引文档id查询文档对象
            Document document = searcher.doc(id);
            //保存索引文档中id这个term中的值(就是数据库中记录的id)到list中,返回出去用于查询实体
            docIds.add(Long.valueOf(document.get("id")));
            //打印
            System.out.println(Objects.toStringHelper("docuemnt").add("文档id", document.get("id")).add("文档名称", document.get("name")).add("文档图片", document.get("pic")).add("文档描述", document.get("description")).toString());
        }
        reader.close();
        return docIds;
    }


    /**
     * 测试新建索引
     */
    public void testCreateIndex() {
        List<Book> bookList = bookDao.findAll();
        if (CollectionUtils.isEmpty(bookList)) return;

        List<Document> docList = Lists.newArrayList();
        Document doc;
        for (Book book : bookList) {
            doc = new Document();
            //图书id, 不分词,不索引, 存储
            doc.add(new StringField("id", book.getId().toString(), Field.Store.YES));
            //图书名, 分词, 索引, 存储
            doc.add(new TextField("name", book.getName(), Field.Store.YES));
            //图书图片, 不分词,不索引,存储
            doc.add(new StringField("pic", book.getPic(), Field.Store.YES));
            //图书价格, 分词, 索引, 存储
            doc.add(new FloatField("price", book.getPrice().floatValue(), Field.Store.YES));
            //描述, 分词, 索引,不存储
            doc.add(new TextField("description", book.getDescription(), Field.Store.NO));
            docList.add(doc);
        }
        try {
            createIndex(docList);
        } catch (IOException e) {
            LogLog.error(e.getMessage(), e);
        }
    }

    /**
     * 查询
     *
     * @throws IOException
     */
    @Test
    public void testQuery() throws IOException, ParseException {

        /**
         *
         * 创建Query对象的2种方式:
         * 1. 根据QueryParser来创建 new QueryParser("description", new SmartChineseAnalyzer()).parse(" 查询条件串"); 返回值Query  //需要指定分词器
         * 2. 根据Query子类来创建   new TermQuery(new Term("description", "java"));   // 不需要指定分词器
         */
        QueryParser parser = new QueryParser("description", new SmartChineseAnalyzer());
        //Query queryAND = parser.parse("description:java AND lucene");
        //Query queryOR = parser.parse("description:java OR lucene");
        Query query = parser.parse("description:java");

        logger.info("===================QueryParser.parse执行" + query.toString());
        List<Long> ids = doQuery(query);
        List<Book> bookList = bookDao.findAll(ids);
        System.out.println("匹配到的实体=" + String.valueOf(bookList));
    }

    /**
     * TermQuery根据词项进行查询
     *
     * @throws IOException
     * @throws ParseException
     */
    public void testTermQuery() throws IOException, ParseException {
        TermQuery query = new TermQuery(new Term("description", "java"));
        logger.info("===================TermQuery执行" + query.toString());
        List<Long> ids = doQuery(query);
        List<Book> bookList = bookDao.findAll(ids);
        System.out.println("匹配到的实体=" + String.valueOf(bookList));
    }


    /**
     * NumericRangeQuery测试数值返范围查询
     *
     * @throws IOException
     * @throws ParseException
     */
    public void testNumericQuery() throws IOException, ParseException {
        // 参数说明: term名, 最小值, 最大值, 是否包含最小值, 是否包含最大值
        NumericRangeQuery<Float> query = NumericRangeQuery.newFloatRange("price", 30f, 50f, true, true);
        List<Long> ids = doQuery(query);
        List<Book> bookList = bookDao.findAll(ids);
        System.out.println(bookList);
    }

    /**
     * BooleanQuery组合查询
     *
     * @throws IOException
     * @throws ParseException
     */
    public void testBooleanQuery() throws IOException, ParseException {
        //创建BooleanQuery对象
        BooleanQuery query = new BooleanQuery();
        TermQuery query1 = new TermQuery(new Term("description", "java"));
        NumericRangeQuery<Float> query2 = NumericRangeQuery.newFloatRange("price", 30f, 50f, true, true);

        //构造组合查询
        /**
         * 1. MUSI与MUST表示"与"
         * 2. MUST与MUST_NOT前者包含, 后者不包含
         * 3. MUST_NOT与MUST_NOT没意义
         * 4. SHOULD与MUST表示MUST, SHOULD失去意义
         * 5. SHOULD与MUST_NOT相当于 MUST与MUST_NOT
         * 6. SHOULD与SHOULD表示"或"
         */
        query.add(query1, BooleanClause.Occur.MUST);
        query.add(query2, BooleanClause.Occur.MUST);
        List<Long> ids = doQuery(query);
        List<Book> bookList = bookDao.findAll(ids);
        System.out.println(bookList);
    }


}
