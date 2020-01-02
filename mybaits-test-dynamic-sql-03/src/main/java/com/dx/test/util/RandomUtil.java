package com.dx.test.util;

import java.util.Random;

public class RandomUtil {
	public static void main(String[] args) {
		String valueString=getSalt(12);
		System.out.println(valueString);
		System.out.println(valueString.length());
	}

	public static String getSalt(int size) {
		if (size < 10) {
			size = 10;
		}
		String[] smallCharArr = new String[] { "a", "b", "c", "d", "e", "f", "g", "h", "k", "l", "m", "n", "i", "j",
				"o", "p", "s", "t", "r", "u", "v", "q", "w", "x", "y", "z" };
		String[] bigCharArr = new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "K", "L", "M", "N", "I", "J", "O",
				"P", "S", "T", "R", "U", "V", "Q", "W", "X", "Y", "Z" };
		String[] numberArr = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		
		StringBuilder builder=new StringBuilder("");
		Random numberRandom=new Random();
		Random charRandom=new Random();
		for(int i=0;i<size;i++) {
			int numberRandomVal=numberRandom.nextInt(10);
			int charRandomVal=charRandom.nextInt(26);
			int modValue=i%3;
			if(modValue==0) {
				builder.append(smallCharArr[charRandomVal]);
			}else if(modValue==1){
				builder.append(bigCharArr[charRandomVal]);
			}else {
				builder.append(numberArr[numberRandomVal]);
			}
		}
		
		return builder.toString();
	}
}
