package com.tommytony.io;

import java.lang.reflect.Field;

import sun.misc.Unsafe;

public class UnsafeMemory {

	private static final Unsafe unsafe;
	
	static {
		try {
			Field f = Unsafe.class.getDeclaredField("theUnsafe");
			f.setAccessible(true);
			unsafe = (Unsafe) f.get(null);
		} catch(Exception e) {
			throw new RuntimeException();
		}
	}
	
	protected static final long byteArrayOffset = unsafe.arrayBaseOffset(byte[].class);
	protected static final int SIZE_OF_INT = 4;
	protected static final int SIZE_OF_BYTE = 1;
	protected static final int SIZE_OF_LONG = 8;
	
	private int pos = 0;
	private final byte[] buffer;
	
	public UnsafeMemory(final byte[] buffer) {
		if(buffer == null)
			throw new NullPointerException("tried to allocated unsafe memory with null buffer");
		this.buffer = buffer;
	}
	
	public void reset() {
		this.pos = 0;
	}
	
	public void putInt(final int value) {
		unsafe.putInt(buffer, byteArrayOffset + pos, value);
		pos += SIZE_OF_INT;
	}
	
	public int getInt() {
		int value = unsafe.getInt(buffer, byteArrayOffset + pos);
		pos += SIZE_OF_INT;
		return value;
	}
	
	public void putLong(final long value) {
		unsafe.putLong(buffer, byteArrayOffset + pos, value);
		pos += SIZE_OF_LONG;
	}
	
	public long getLong() {
		long value = unsafe.getLong(buffer, byteArrayOffset + pos);
		pos += SIZE_OF_LONG;
		return value;
	}
	
	public void putByteArray(final byte[] values) {
		putInt(values.length);
		
		long bytesToCopy = values.length << 3;
		unsafe.copyMemory(values, byteArrayOffset, buffer, byteArrayOffset + pos, bytesToCopy);
		pos += bytesToCopy;
	}
	
	public byte[] getByteArray() {
		int size = getInt();
		byte[] values = new byte[size];
		long bytesToCopy = values.length << 3;
		unsafe.copyMemory(buffer, byteArrayOffset + pos, values, byteArrayOffset, bytesToCopy);
		pos += bytesToCopy;
		return values;
	}
	
}
