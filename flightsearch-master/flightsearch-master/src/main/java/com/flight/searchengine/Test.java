package com.flight.searchengine;

import java.sql.Timestamp;
import java.util.Date;

public class Test {

	public static void main(String[] args) {
		System.out.println(Timestamp.valueOf("2020-09-17 06:00:00"));
		System.out.println(Timestamp.valueOf("2020-09-17 06:00:00").getTime());
		System.out.println(new Date(Timestamp.valueOf("2020-09-17 06:00:00").getTime()).getTime());
//		System.out.println(new Date(1600302600));
	}
}
