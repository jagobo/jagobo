package com.bc.cas.manager;

import com.bc.cas.dao.BookDao;
import com.bc.cas.model.entity.Book;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.apache.lucene.document.Field.*;

/**
 * @Copyright 2012-2016 duenboa 版权所有
 * @Author Created by Administrator on 2016/11/29.
 * @Version V 1.0.0
 * @Desc 索引管理器
 */

@Repository
public class IndexManager {


    @Autowired
    private BookDao bookDao;

    FSDirectory dir = FSDirectory.open(new File("d:/lucene/index03"));

    public IndexManager() throws IOException {
    }


    /**
     * 创建索引
     */
    @Test
    public void createIndex() throws IOException {

        List<Book> bookList = bookDao.findAll();

//        FSDirectory dir = FSDirectory.open(new File("d:/lucene/index03"));
//        List<Document> docList = new ArrayList<Document>();
//        Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_4_9);
//        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_4_9, analyzer);
//        IndexWriter writer = new IndexWriter(dir, config);
//
//        if (CollectionUtils.isEmpty(bookList)) return;
//
//        Document doc;
//        for (Book book : bookList) {
//
//            doc = new Document();
//            doc.add(new StoredField("id", book.getId()));
//            doc.add(new StringField("name", book.getName(), Store.YES));
//            doc.add(new TextField("pic", book.getPic(), Store.YES));
//            doc.add(new TextField("description", book.getDescription(), Store.YES));
//            writer.addDocument(doc);
//        }
//        writer.commit();
//        writer.close();



    }


    /**
     * 创建索引2
     */
    public void index() {
        IndexWriter writer = null;
        try {
            writer = new IndexWriter(dir, new IndexWriterConfig(Version.LUCENE_4_9, new StandardAnalyzer(Version.LUCENE_4_9)));
            writer.deleteAll();
            Document doc = null;
            List<Book> bookList = bookDao.findAll();

            for (Book book : bookList) {
                doc = new Document();
                doc.add(new Field("id", String.valueOf(book.getId()), Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));
                doc.add(new Field("name", book.getName(), Store.YES, Index.ANALYZED));
                doc.add(new Field("pic", book.getPic(), Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));
                doc.add(new Field("description", book.getDescription(), Store.YES, Index.ANALYZED));
                writer.addDocument(doc);
            }
        } catch (CorruptIndexException e) {
            e.printStackTrace();
        } catch (LockObtainFailedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) writer.close();
            } catch (CorruptIndexException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
