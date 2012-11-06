package com.tommytony.io;

import java.nio.ByteBuffer;

public class CStyleMemory {

	private ByteBuffer buffer;
	private long size;
	private boolean[] pageTable;
	private int[] sizes;
	private long pageTableIndex;
	
	public CStyleMemory(int size) {
		buffer = ByteBuffer.allocateDirect((int) size);
		this.size = CStdLib.malloc(4); /*int*/
		CStdLib.unsafe.putInt(this.size, size);
		this.pageTableIndex = CStdLib.malloc(4);
		CStdLib.unsafe.putInt(this.size, 0);
		this.pageTable = new boolean[size];
		this.sizes = new int[size];
		//total allocated memory = (off-heap: size + 4 + 4) (on-heap: size + size * 4)
	}
	
	public int malloc(int size) {
		int ret = this.allocateLoop(size, false);
		if(ret == 0)
			ret = this.allocateLoop(size, true);
		if(ret == -1)
			throw new RuntimeException("NOT AVAILABLE MEMORY");
		return ret;
	}
	
	private int allocateLoop(int size, boolean beenThroughLoop) {
		boolean broke = false;
		for(int i = CStdLib.unsafe.getInt(this.pageTableIndex); 
				i < CStdLib.unsafe.getInt(this.size); i++) {
			if(!pageTable[i]) {
				for(int k = i; k < k + size; k++) {
					if(!pageTable[k]) {
						continue;
					} else {
						broke = true;
						break;
					}
				}
				if(broke) {
					continue;
				} else {
					for(int j = i; j < j + size; j++) {
						pageTable[j] = true;
					}
					sizes[i] = size;
					return i;
				}
			}
		}
		if(beenThroughLoop) {
			System.out.println("NO MEMORY AVAILABLE!!!!");
			return -1;
		}
		return 0;
	}
	
	public void put(int pointer, byte[] bytes) {
		int expectedSize = sizes[pointer];
		if(expectedSize != bytes.length) {
			System.out.println("Expected: " + expectedSize + " Found: " + bytes.length);
		}
		for(int i = pointer; i < pointer + expectedSize; i++) {
			buffer.position(pointer);
			buffer.put(bytes, 0, expectedSize);
		}
	}
	
	public void free(int pointer) {
		int sizeroni = sizes[pointer];
		for(int i = pointer; i < pointer + sizeroni; i++) {
			this.pageTable[i] = false;
		}
		this.sizes[pointer] = 0;
	}
}
