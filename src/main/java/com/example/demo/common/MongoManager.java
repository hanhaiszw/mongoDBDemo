package com.example.demo.common;

import com.example.demo.model.StatisResult;
import com.example.demo.model.Student;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
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

    public List<StatisResult> aggregationTest() {
        // .limit .skip
        // 同时统计分组的个数，需要使用分流操作
        // 需要注意的是怎样接收结果，可以使用Document接收后，查看解结构，再写类接收
        // Aggregation.facet(
        //         Aggregation.count().as("count")
        // ).as("statis")
        //  .and(
        //       Aggregation.sort(sort),
        //       Aggregation.skip(Long.valueOf(offset)),
        //       Aggregation.limit(limit)
        //  ).as("resultList")
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(
                        Criteria.where("id").gte(0)
                ),
                Aggregation.group("grade").sum("score").as("totalScore").count().as("studentNum")
                        .avg("score").as("avgScore"),
                Aggregation.project("totalScore", "avgScore", "studentNum").and("_id")
                        .as("grade").andExclude("_id"),
                Aggregation.sort(new Sort(Sort.Direction.DESC, "avgScore"))
        );

        AggregationResults<StatisResult> statisResults = mongoTemplate.aggregate(aggregation, COLLECTION_NAME, StatisResult.class);
        return statisResults.getMappedResults();
    }


}
