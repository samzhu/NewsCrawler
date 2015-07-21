package com.news.test;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.Chronology;
import java.time.chrono.IsoChronology;
import java.time.chrono.JapaneseChronology;
import java.time.chrono.MinguoChronology;
import java.time.chrono.MinguoDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DecimalStyle;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.news.entities.News;

public class GetData {

	public static void main(String[] args) {
		String baseUrl = "http://fund.bot.com.tw";
		LocalDate today = LocalDate.now();
		LocalDate startDate = LocalDate.of(2003, 6, 23);

		List<News> newslist = new ArrayList();
		for(int i = 0 ; i < 1000 ; i++){
			List newslisttmp = getNewsList(startDate.toString());
			newslist.addAll(newslisttmp);
			startDate = startDate.plusDays(1L);
		}
		
		for(News news:newslist){
			getNewsContent(news);
			System.out.println(news.getContent());
		}
	}

	public static List<News> getNewsList(String date){
		News news = null;
		List<News> newslist = new ArrayList();
		try {
			Document doc = Jsoup.connect(String.format("http://fund.bot.com.tw/Z/ZF/ZF_H_%s.djhtm", date)).get();
			Elements resultLinks = doc.select("table :gt(0) > td > a");
			for(Element el:resultLinks){
				news = new News();
				news.setLink(el.attr("href"));
				news.setLinktext(el.text());
				newslist.add(news);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("date=" + date + ", newslist.size=" + newslist.size());
		return newslist;
	}

	public static void getNewsContent(News news){
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
