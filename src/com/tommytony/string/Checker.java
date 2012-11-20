package com.tommytony.string;

import java.util.regex.Pattern;

public class Checker {
	public static boolean isAlphanumeric(String str) {
		Pattern p = Pattern.compile("^[a-zA-Z0-9]*$");
		return p.matcher(str).find();
	}
	public static boolean isAlpha(String str) {
		Pattern p = Pattern.compile("^[a-zA-Z]*$");
		return p.matcher(str).find();
	}
	public static boolean isAlpha(char c) {
		Pattern p = Pattern.compile("^[a-zA-Z]*$");
		return p.matcher(Character.toString(c)).find();
	}
	public static boolean isNumeric(String str) {
		Pattern p = Pattern.compile("^[0-9]*$");
		return p.matcher(str).find();
	}
}
