package com.news.repository;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.news.entities.News;

public interface NewsRepository extends ElasticsearchRepository<News, String>{
	public List < News >  findByContent(String content);
}
