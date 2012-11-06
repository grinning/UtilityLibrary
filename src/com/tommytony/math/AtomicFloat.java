package com.tommytony.math;

import java.util.concurrent.atomic.AtomicInteger;

import com.tommytony.io.CStdLib;

public class AtomicFloat {
	
	private long address;
	
	public AtomicFloat(float f) {
		this.address = CStdLib.malloc(4);
		CStdLib.unsafe.putFloat(this.address, f);
	}
	
	public AtomicFloat() {
		this.address = CStdLib.malloc(4);
	}
	
	public final boolean compareAndSwap(float num, float delta) {
		if(CStdLib.unsafe.getFloat(this.address) == num) {
			CStdLib.unsafe.putFloat(this.address, delta);
			return true;
		} else {
			return false;
		}
	}
	
	public final float fetchAndAdd() {
		CStdLib.unsafe.putFloat(this.address, CStdLib.unsafe.getFloat(this.address) + 1);
		return CStdLib.unsafe.getFloat(this.address);
	}
	
	AtomicInteger integer = new AtomicInteger();
	
	@Override
	public final void finalize() {
		CStdLib.free(this.address);
	}
}
