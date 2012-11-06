package com.tommytony.sound;

import java.nio.ByteBuffer;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

public class VoiceProcessor {

	public static void input() {
		TargetDataLine line = null;
		AudioFormat format = new AudioFormat(500f, 16, 3, false, false);
		DataLine.Info info = new DataLine.Info(TargetDataLine.class, 
				format);
		if(!AudioSystem.isLineSupported(info)) {
			//poop
		}
		try {
			line = (TargetDataLine) AudioSystem.getLine(info);
			line.open(format, 10000);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		ByteBuffer buffer = ByteBuffer.allocate(10000);
		//start audio
		line.start();
		line.read(buffer.array(), 0, 10000);
		buffer.flip();
	}
}
