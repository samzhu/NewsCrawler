package com.news.entities;

import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
@Document(indexName = "mitakev1", type = "news", shards = 10, replicas = 0, refreshInterval = "-1")
public class News {
	@Id
	private String id;
	@Version
	private Long version;
	private String postdate;
	private String link;
	private String linktext;
	private String content;
	@Field(type= FieldType.Nested, index = FieldIndex.not_analyzed)
	private List relatedStocks;
	@Field(type= FieldType.Nested, index = FieldIndex.not_analyzed)
	private List relatedIndustries;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd'T'HHmmss.SSS'Z'")
    @Field(type = FieldType.Date, format = DateFormat.basic_date_time, index = FieldIndex.not_analyzed)
    @CreatedDate
    private Date createdDateTime;
}
