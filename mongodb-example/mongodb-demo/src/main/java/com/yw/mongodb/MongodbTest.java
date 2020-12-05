package com.yw.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yangwei
 * @date 2020-07-20 16:23
 */
public class MongodbTest {
    private static MongoClient mongoClient = new MongoClient("192.168.254.128", 27017);
    private static MongoDatabase database = mongoClient.getDatabase("kaikeba");
    private static MongoCollection<Document> collection = database.getCollection("test");

    @Test
    public void testDbConnection() {
        database = mongoClient.getDatabase("kaikeba");
        System.out.println("Connect to database successfully");
    }

    @Test
    public void testCreateCollection() {
        database.createCollection("test");
        System.out.println("Create collection successfully");
    }

    @Test
    public void testGetCollection() {
        collection = database.getCollection("test");
        System.out.println("Get collection successfully");
    }

    @Test
    public void testInsertDocument() {
        // 插入文档
        Document document = new Document("title", "MongoDB")
                .append("description", "database")
                .append("likes", 100)
                .append("by", "yw");
        List<Document> documents = new ArrayList<>();
        documents.add(document);
        collection.insertMany(documents);
        System.out.println("Document insert successfully");
    }

    /**
     * 测试检索所有文档
     * 1. 获取迭代器 FindIterable<Document>
     * 2. 获取游标 MongoCursor<Document>
     * 3. 通过游标遍历检索出的文档集合
     *
     * @throws Exception
     */
    @Test
    public void testFindDocument() {
        FindIterable<Document> findIterable = collection.find();
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        while (mongoCursor.hasNext()) {
            System.out.println(mongoCursor.next());
        }
    }

    /**
     * 测试更新文档，将文档中 likes=100的文档修改为 likes=200
     */
    @Test
    public void testUpdateDocument() {
        collection.updateMany(Filters.eq("likes", 100),
                new Document("$set", new Document("likes", 200)));
        // 检索查看结果
        FindIterable<Document> findIterable = collection.find();
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        while (mongoCursor.hasNext()) {
            System.out.println(mongoCursor.next());
        }
    }

    @Test
    public void testDeleteDocument() {
        // 删除符合条件的第一个文档
        collection.deleteOne(Filters.eq("likes", 200));
        // 删除所有符合条件的文档
        collection.deleteMany(Filters.eq("likes", 200));
        // 检索查看结果
        FindIterable<Document> findIterable = collection.find();
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        while (mongoCursor.hasNext()) {
            System.out.println(mongoCursor.next());
        }
    }
}
