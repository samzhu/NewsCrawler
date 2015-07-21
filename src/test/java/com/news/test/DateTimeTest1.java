package com.news.test;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class DateTimeTest1 {

	public static void main(String[] args) {
		DateTimeTest1.test2();

	}
	
	
	public static void test1(){
		Instant timestamp = Instant.now();
		System.out.println(timestamp);
		//2015-07-20T02:00:15.638Z
		
		Instant time = Calendar.getInstance().toInstant();
		System.out.println(time);
		//2015-07-20T02:00:15.638Z
	}
	
	public static void test2(){
		ZoneId defaultZone = TimeZone.getDefault().toZoneId();
		System.out.println(defaultZone);
		//Asia/Taipei
		
		ZonedDateTime gregorianCalendarDateTime = new GregorianCalendar().toZonedDateTime();
		System.out.println(gregorianCalendarDateTime);
		//2015-07-20T10:05:18.825+08:00[Asia/Taipei]
	}
	
}
