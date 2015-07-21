package com.news.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.news.entities.News;
import com.news.service.NewsService;

@RestController
@RequestMapping(value = "/api")
public class NewsController {
	
	@Autowired
	private NewsService newsService;
	
	@RequestMapping(value = "/v1/news", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity query(
			@RequestParam(value="useracc", required=false) String useracc
			){
		ResponseEntity response = null;
		try {
			newsService.getNews();
			response = new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			response = new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	@RequestMapping(value = "/v1/newsall", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity query2(
			@RequestParam(value="useracc", required=false) String useracc
			){
		ResponseEntity response = null;
		try {
			Iterable<News> newslist = newsService.findAll();
			response = new ResponseEntity<Iterable>(newslist, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			response = new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
}
