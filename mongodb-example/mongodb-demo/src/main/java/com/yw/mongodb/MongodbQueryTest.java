package com.yw.mongodb;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.junit.Before;
import org.junit.Test;

import javax.print.Doc;
import java.util.regex.Pattern;

/**
 * @author yangwei
 */
public class MongodbQueryTest {
    private MongoClient client;
    private MongoDatabase database;

    @Before
    public void init() {
        client = new MongoClient("59.110.227.7", 27017);
        database = client.getDatabase("kaikeba");
    }

    @Test
    public void testQuery1() {
        MongoCollection<Document> collection = database.getCollection("list1");
        // 获取第一个记录
        Document first = collection.find().first();
        // 根据key值获得数据
        System.out.println(first.get("name"));

        // 查询指定字段
        Document queryDoc = new Document();
        queryDoc.append("_id", 0); // 不包含 _id
        queryDoc.append("name", 1); // 包含 name

        FindIterable<Document> documents = collection.find().projection(queryDoc);
        for (Document document : documents) {
            System.out.println(document.toJson());
        }
    }

    @Test
    public void testQuery2() {
        MongoCollection<Document> collection = database.getCollection("list1");
        // 按指定字段排序
        Document queryDoc = new Document();
        queryDoc.append("age", -1);

        FindIterable<Document> documents = collection.find().sort(queryDoc);
        for (Document document : documents) {
            System.out.println(document.toJson());
        }
    }

    @Test
    public void testFilters1() {
        MongoCollection<Document> collection = database.getCollection("list1");
        // 查询 name 等于 zhangsan 的记录
        FindIterable<Document> documents = collection.find(Filters.eq("name", "zhangsan"));
        for (Document document : documents) {
            System.out.println(document.toJson());
        }
    }

    @Test
    public void testFilters2() {
        MongoCollection<Document> collection = database.getCollection("list1");
        // 查询 age 大于 3 的记录
        FindIterable<Document> documents = collection.find(Filters.gt("age", 3));
        for (Document document : documents) {
            System.out.println(document.toJson());
        }
    }

    @Test
    public void testFilters3() {
        MongoCollection<Document> collection = database.getCollection("list1");
        // 查询 age 小于 3 的记录 或 anme = zhangfei 的记录
        FindIterable<Document> documents = collection.find(Filters.or(
                Filters.eq("name", "zhangfei"), Filters.lt("age", 3))
        );
        for (Document document : documents) {
            System.out.println(document.toJson());
        }
    }

    @Test
    public void testLike() {
        MongoCollection<Document> collection = database.getCollection("list1");
        // 利用正则 模糊匹配 包含 zhang 的
        Pattern pattern = Pattern.compile("^.*zhang.*$", Pattern.CASE_INSENSITIVE);
        BasicDBObject query = new BasicDBObject();
        query.put("name", pattern);

        FindIterable<Document> documents = collection.find(query);
        for (Document document : documents) {
            System.out.println(document.toJson());
        }
    }

    @Test
    public void testPage() {
        MongoCollection<Document> collection = database.getCollection("list1");
        Pattern pattern = Pattern.compile("^.*zh.*$", Pattern.CASE_INSENSITIVE);
        BasicDBObject query = new BasicDBObject();
        query.put("name", pattern);

        // 排序：按年龄倒序
        BasicDBObject sort = new BasicDBObject();
        sort.put("gae", -1);
        // 获得总条数
        System.out.println("共计: " + collection.count() + " 条记录");
        // 取出第1至3条记录
        FindIterable<Document> documents = collection.find(query).sort(sort).skip(0).limit(3);
        for (Document document : documents) {
            System.out.println(document.toJson());
        }
    }
}
