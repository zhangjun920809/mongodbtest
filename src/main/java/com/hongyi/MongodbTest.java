package com.hongyi;

import com.mongodb.client.*;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.junit.Test;

import java.util.Arrays;
import java.util.Set;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.set;

/**
 * @author User
 * @date 2020/12/8 16:02
 */
public class MongodbTest {
    public static MongoCollection<Document> conn = null;
    static{
        //创建链接，默认连接本地mongodb
        //MongoClient mongoClient = MongoClients.create();

        //创建指定连接
        MongoClient mongoClient = MongoClients.create("mongodb://192.168.2.199:27017");
        //获取指定库，如果不存在，会自动创建。
        MongoDatabase mydb = mongoClient.getDatabase("mydb");
        //访问指定的collection
        conn = mydb.getCollection("test");
    }
    public static void main(String[] args) {
        //insert(conn);
        //query(conn);
        //update(conn);
        delete(conn);
    }

    /**
     *  插入数据
     */
    public static void insert(MongoCollection<Document> conn){


        //java程序创建文档，需要使用Document类
        Document doc = new Document("name", "java110")
                .append("type", "语言")
                .append("count", 1)
                .append("versions", Arrays.asList("java8", "java9", "java14"))
                .append("info", new Document("x", 111).append("y", 222));
        //插入数据
        conn.insertOne(doc);
    }

    /**
     *  获取数据
     * @param conn
     */
    public  static void query(MongoCollection<Document> conn){
        //获取当前collections中，第一个文档
        Document first = conn.find().first();
        System.out.println(first.toJson());

        //遍历获取所有文档
        MongoCursor<Document> iterator = conn.find().iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next().toJson());
        }

        //根据条件过滤符合条件的记录
        //eq   com.mongodb.client.model.Filters
        Document i = conn.find(eq("name", "java110")).first();
        System.out.println(i.toJson());
        //{"_id": {"$oid": "5fcf392324cfcb7b1fa5372c"}, "name": "java110", "type": "语言", "count": 1, "versions": ["java8", "java9", "java14"], "info": {"x": 111, "y": 222}}

        //多个过滤条件可组合使用
        //FindIterable<Document> documents = conn.find(and(gt("i", 50), lte("i", 100)));
    }

    /**
     *  更新数据
     * @param conn
     */
    public static void update(MongoCollection<Document> conn){
        //单个更新
        UpdateResult updateResult = conn.updateOne(eq("name", "java556"), set("name", "java555"));
        System.out.println(updateResult);
        //AcknowledgedUpdateResult{matchedCount=1, modifiedCount=1, upsertedId=null}

        //批量更新
        UpdateResult updateResult1 = conn.updateMany(eq("count", 1), set("name", "88888"));
        System.out.println(updateResult1);
        //AcknowledgedUpdateResult{matchedCount=2, modifiedCount=2, upsertedId=null}

    }

    /**
     * 删除方法
     * @param conn
     */
    public static void delete(MongoCollection<Document> conn) {
        DeleteResult deleteResult = conn.deleteOne(eq("type", "database"));
        System.out.println(deleteResult);
        //AcknowledgedDeleteResult{deletedCount=1}

        //批量删除
        DeleteResult deleteResult1 = conn.deleteMany(eq("type", "database"));
    }
}


