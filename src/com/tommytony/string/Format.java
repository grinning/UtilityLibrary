package com.tommytony.string;

import java.util.Iterator;
import java.util.List;

public class Format {

	public static String formatListNumbers(List<Integer> l) {
		StringBuilder str = new StringBuilder();
		Iterator<Integer> i = l.iterator();
		while(i.hasNext()) {
			str.append(i.next().intValue());
			if(i.hasNext())
			    str.append(", ");
		}
		return str.toString();
	}
	
	public static String formatStringtoBasicSentence(String string) {
		StringBuilder str = new StringBuilder();
		str.append(Character.toTitleCase(string.charAt(0)));
		str.append(string.substring(1, string.length()));
		str.append('.');
		return str.toString();
	}
}
