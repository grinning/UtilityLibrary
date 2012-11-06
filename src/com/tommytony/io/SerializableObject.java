package com.tommytony.io;

import java.io.Serializable;
import java.nio.ByteBuffer;

public class SerializableObject implements Serializable {

	private static final long serialVersionUID = -120389L;
	
	private final long sourceId;
	private final Object obj;
	private final byte[] arrayOfObj;
	
	public SerializableObject(long sourceId, Object obj, byte[] arrayOfObj) {
		this.sourceId = sourceId;
		this.obj = obj;
		this.arrayOfObj = arrayOfObj;
	}
	
	protected void write(final ByteBuffer bytebuffer) {
		bytebuffer.putLong(this.sourceId);
		bytebuffer.putInt(arrayOfObj.length);
		bytebuffer.put(arrayOfObj);
	}
	
	protected void write(final UnsafeMemory buffer) {
		buffer.putLong(sourceId);
		buffer.putByteArray(arrayOfObj);
	}
	

}
