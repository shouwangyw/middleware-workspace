package com.yw.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yangwei
 */
public class MongodbDemo {
    private MongoClient client;

    @Before
    public void init() {
        client = new MongoClient("192.168.254.128", 27017);
    }

    @Test
    public void connectDB() {
        MongoDatabase database = client.getDatabase("test");
        Assert.assertNotNull(database);
        System.out.println("Connect successful: " + database.getName());
    }

    @Test
    public void createCollection() {
        MongoDatabase database = client.getDatabase("test");
        database.createCollection("col1");
        database.createCollection("col2");

        MongoCollection<Document> collection = database.getCollection("col");
        Assert.assertNotNull(collection);
        System.out.println("集合创建成功: " + collection.getNamespace());

        MongoIterable<String> collectionNames = database.listCollectionNames();
        for (String name : collectionNames) {
            System.out.println("Collection name: " + name);
        }
    }

    @Test
    public void insertDocuments() {
        MongoDatabase database = client.getDatabase("test");
        MongoCollection<Document> collection = database.getCollection("col1");
        List<Document> documents = new ArrayList<>();
        documents.add(new Document("name", "James").append("age", 34).append("sex", "男"));
        documents.add(new Document("name", "Wade").append("age", 36).append("sex", "男"));
        documents.add(new Document("name", "Cp3").append("age", 32).append("sex", "男"));

        collection.insertMany(documents);
        System.out.println("文档插入成功");
    }

    @Test
    public void findDocuments() {
        MongoDatabase database = client.getDatabase("test");
        MongoCollection<Document> collection = database.getCollection("col1");

        // 方式一
        FindIterable<Document> documents = collection.find();
        for (Document document : documents) {
            System.out.println(document.toJson());
        }

        // 方式二
        MongoCursor<Document> cursor = documents.iterator();
        while (cursor.hasNext()) {
            Document document = cursor.next();
            System.out.println(document.toJson());
        }
    }

    @Test
    public void updateDocuments() {
        MongoDatabase database = client.getDatabase("test");
        MongoCollection<Document> collection = database.getCollection("col1");

        collection.updateMany(Filters.eq("age", 32), new Document("$set", new Document("age", 20)));
        FindIterable<Document> documents = collection.find();
        for (Document document : documents) {
            System.out.println(document.toJson());
        }
    }

    @Test
    public void deleteDocuments() {
        MongoDatabase database = client.getDatabase("test");
        MongoCollection<Document> collection = database.getCollection("col1");
        // 删除符合条件的第一个文档
        collection.deleteOne(Filters.eq("age", 20));
        collection.deleteOne(Filters.eq("name", "James"));
        // 删除符合条件的所有文档
        collection.deleteMany(Filters.eq("age", 20));

        FindIterable<Document> documents = collection.find();
        for (Document document : documents) {
            System.out.println(document.toJson());
        }
    }
}
