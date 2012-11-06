package com.tommytony.io;

import java.lang.reflect.Field;

import sun.misc.Unsafe;

public class CStdLib {

	public static Unsafe unsafe;
	
	static {
		try {
			Field f = Unsafe.class.getDeclaredField("theUnsafe");
			f.setAccessible(true);
			unsafe = (Unsafe) f.get(null);
		} catch(Exception e) {}
	}
	
	public static long malloc(long size) {
		if(size < 1)
			throw new RuntimeException("Cannot allocate negative size");
		return unsafe.allocateMemory(size);
	}
	
	private static long normalize(int value) {
		if(value >= 0) return value;
		return (~0L >>> 32) & value;
	}
	
	public static long sizeOf(Object object) {
		return unsafe.getAddress(normalize(unsafe.getInt(object, 4L)) + 12L);
	}
	
	public static void free(long l) {
		unsafe.freeMemory(l);
	}
	
	public static long calloc(long size, int elements) {
		return unsafe.allocateMemory(size * elements);
	}
}
