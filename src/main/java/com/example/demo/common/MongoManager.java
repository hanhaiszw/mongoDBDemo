package com.example.demo.common;

import com.example.demo.model.Student;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * create by sunzhongwei on 2019/07/14
 * Desc:
 **/
@Component
public class MongoManager {
    private static final String COLLECTION_NAME = "student";

    @Autowired
    private MongoTemplate mongoTemplate;

    public Long getCount() {
        Long cnt = mongoTemplate.count(
                Query.query(Criteria.where("id").gte(2)
                ),
                Document.class, COLLECTION_NAME
        );
        return cnt;
    }

    public Student getStudentById(Long id) {
        Document document = mongoTemplate.findOne(
                Query.query(Criteria.where("id").is(id)),
                Document.class, COLLECTION_NAME
        );
        return document == null ? null : new Student(document);
    }


    public List<Student> getAllStudent() {
        DBObject obj = new BasicDBObject();
        //添加条件
        //obj.put("id", new BasicDBObject("$gte", id));

        BasicDBObject fieldsObject = new BasicDBObject();
        fieldsObject.put("_id", false);   // 不包含_id字段

        Query query = new BasicQuery(obj.toString(), fieldsObject.toString());

        List<Document> documentList = mongoTemplate.find(
                query,
                Document.class, COLLECTION_NAME
        );
        List<Student> studentList = new ArrayList<>();
        for (Document document : documentList) {
            studentList.add(new Student(document));
        }
        return studentList.size() == 0 ? null : studentList;
    }
}
