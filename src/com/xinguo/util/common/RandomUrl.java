package com.xinguo.util.common;

import java.util.Random;

public class RandomUrl {

	public static String getRandomUrl() {
		Random random = new Random(100);
		
		return "random="+random.nextLong();
	}
	
}
