package com.tommytony.io;

import java.nio.ByteBuffer;

public class Serialize {

	public static void write(SerializableObject obj, SerializeType type, Object buf) {
		if(type == SerializeType.BUFFER) {
			obj.write((ByteBuffer) buf);
		} else if(type == SerializeType.UNSAFE) {
			obj.write((UnsafeMemory) buf);
		}
	}
}
