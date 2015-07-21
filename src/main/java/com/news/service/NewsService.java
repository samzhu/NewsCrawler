package com.news.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.news.entities.News;
import com.news.repository.NewsRepository;

@Service
public class NewsService {
	@Autowired
	private NewsRepository repository;
	
	public Iterable<News> findAll(){
		return repository.findAll();
	}
	
	public void deleteAll(){
		this.repository.deleteAll();
	}
	
	public void getNews(){
		LocalDate today = LocalDate.now();
		LocalDate startDate = LocalDate.of(2003, 6, 23);

		List<News> newslist = new ArrayList();
		for(int i = 0 ; i < 1 ; i++){
			List newslisttmp = getNewsList(startDate.toString());
			newslist.addAll(newslisttmp);
			startDate = startDate.plusDays(1L);
		}
		
		for(News news:newslist){
			getNewsContent(news);
			System.out.println(news.getContent());
			this.repository.save(news);
		}
	}
	
	private List<News> getNewsList(String date){
		News news = null;
		List<News> newslist = new ArrayList();
		try {
			Document doc = Jsoup.connect(String.format("http://fund.bot.com.tw/Z/ZF/ZF_H_%s.djhtm", date)).get();
			Elements resultLinks = doc.select("table :gt(0) > td > a");
			for(Element el:resultLinks){
				news = new News();
				news.setLink(el.attr("href"));
				news.setLinktext(el.text());
				news.setCreatedDateTime(new Date());
				newslist.add(news);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("date=" + date + ", newslist.size=" + newslist.size());
		return newslist;
	}

	private void getNewsContent(News news){
		Document doc;
		try {
			doc = Jsoup.connect(String.format("http://fund.bot.com.tw/%s", news.getLink())).get();
			//時間 (92/06/23 16:20:43)
			Element datetime = doc.select("table :gt(0) div.p01").first();
			//System.out.println(datetime.text().trim());
			//內容
			Element content = doc.select("table :gt(0) td.p1").first();
			news.setContent(content.text());

			//•相關個股:  1736喬山 •相關產業:  生物科技
			List relatedStocks = new ArrayList();
			List relatedIndustries = new ArrayList();
			Elements relateds = doc.select("table :gt(0) td.p2");
			for(Element el:relateds){
				String text = el.text();
				if(text.startsWith("•相關個股")){
					relatedStocks.add(text.split(":")[1].trim());
				}else{
					relatedIndustries.add(text.split(":")[1].trim());
				}
			}
			news.setRelatedStocks(relatedStocks);
			news.setRelatedIndustries(relatedIndustries);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
