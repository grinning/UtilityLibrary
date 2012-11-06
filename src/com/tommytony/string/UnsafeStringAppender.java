package com.tommytony.string;

import com.tommytony.io.CStdLib;

public class UnsafeStringAppender {

	private long address;
	private int index;
	
	public UnsafeStringAppender(String s) {
		this(s, 12L);
	}
	
	public UnsafeStringAppender(String s, long padding) {
		this(s.length() + padding);
		this.append(s);
	}
	
	public UnsafeStringAppender(long length) {
		this.address = CStdLib.malloc(length);
		this.index = 0;
	}
	
	public UnsafeStringAppender() {
		this(12L);
	}
	
	public void append(char c) {
		CStdLib.unsafe.putChar(this.address + (index * 2), c);
		this.index++;
	}
	
	public void append(String s) {
		for(int i = 0; i < s.length(); i++) {
			this.append(s.charAt(i));
			this.index++;
		}
	}
	
	@Override
	public String toString() {
		char[] chars = new char[this.index];
		for(int i = 0; i < this.index; i++) {
			chars[i] = CStdLib.unsafe.getChar(this.address + (index * 2));
		}
		return String.valueOf(chars);
	}
	
	public void deallocate() {
		CStdLib.free(this.address);
	}
}
