//package com.bc.cas.manager;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FilenameFilter;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.StringReader;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import org.apache.lucene.analysis.Analyzer;
//import org.apache.lucene.analysis.TokenStream;
//import org.apache.lucene.document.Document;
//import org.apache.lucene.document.Field.Store;
//import org.apache.lucene.document.LongField;
//import org.apache.lucene.document.StoredField;
//import org.apache.lucene.document.StringField;
//import org.apache.lucene.document.TextField;
//import org.apache.lucene.index.DirectoryReader;
//import org.apache.lucene.index.IndexReader;
//import org.apache.lucene.index.IndexWriter;
//import org.apache.lucene.index.IndexWriterConfig;
//import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
//import org.apache.lucene.queryparser.classic.QueryParser;
//import org.apache.lucene.search.IndexSearcher;
//import org.apache.lucene.search.Query;
//import org.apache.lucene.search.ScoreDoc;
//import org.apache.lucene.search.TopDocs;
//import org.apache.lucene.search.TopScoreDocCollector;
//import org.apache.lucene.store.Directory;
//import org.apache.lucene.store.SimpleFSDirectory;
//import org.apache.lucene.util.Version;
//import org.junit.Test;
//import org.wltea.analyzer.lucene.IKAnalyzer;
//
//public class TestLucene {
//
//    // 待索引文件存放路径
//    private static String source_file_path = "c:\\txts";
//
//    // 生成的全文索引文件存储路径
//    private static String indexDir = "c:\\index_dir";
//
//    private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//    // 中文分词器IKAnalyzer,从http://code.google.com/p/ik-analyzer/downloads/list 下载最新源码放入项目中使用
//    private static Analyzer analyzer = new IKAnalyzer();
//
//    @Test
//    public void createIndex() {
//        Directory directory = null;
//        try {
//            // lucene有两种数据存储方式,文件系统或者内存,这里使用文件系统存储索引数据
//            directory = new SimpleFSDirectory(new File(indexDir));
//
//            // 生成全文索引的配置对象
//            IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_48,analyzer);
//            // 设置生成全文索引的方式为创建或者追加
//            config.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
//            // 创建真正生成全文索引的writer对象
//            IndexWriter indexWriter = new IndexWriter(directory, config);
//
//            // 读取待索引文件夹下的所有的.txt文件
//            File sourceFiles = new File(source_file_path);
//            String txtFiles[] = sourceFiles.list(new FilenameFilter() {
//                @Override
//                public boolean accept(File dir, String name) {
//                    if (name.endsWith(".txt")) { // 这里只对.txt文件创建索引
//                        return true;
//                    }
//                    return false;
//                }
//            });
//
//            // 遍历所有txt文件写入全文索引,如果数据来源是数据库则遍历数据库查询结果集即可
//            for (String txtFile : txtFiles) {
//                String file = source_file_path + File.separator + txtFile;
//                File input_txt_file = new File(file);
//
//                System.out.println("开始对" + txtFile + "建立索引");
//
//                Document doc = new Document(); // 约等于数据库的一行记录
//
//                // 以下生成各个字段的数据值
//                StringField name = new StringField("filename",	input_txt_file.getName(), Store.YES);
//                TextField content = new TextField("content", readFileContent(	file, "gbk"), Store.YES);
//                StringField path = new StringField("path",input_txt_file.getAbsolutePath(), Store.YES);
//                StoredField date = new StoredField("date",	df.format(new Date()));
//                LongField size = new LongField("size", input_txt_file.length(),Store.YES);
//
//                // 向Document对象中加入各个字段的值
//                doc.add(name);
//                doc.add(content);
//                doc.add(path);
//                doc.add(date);
//                doc.add(size);
//
//                // 向IndexWriter中增加新的一行记录
//                indexWriter.addDocument(doc);
//                // 提交数据内容
//                indexWriter.commit();
//            }
//            indexWriter.close();
//            directory.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }