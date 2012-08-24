package com.tommytony.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Compress {

	static final int BUFFER_SIZE = 4096;
	
	public static void Zip(File file, String outputFile) throws IOException {
		BufferedInputStream orgin = null;
		FileOutputStream dest = new FileOutputStream(outputFile);
		ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));
		byte[] data = new byte[Compress.BUFFER_SIZE];
		String[] files = file.list();
		
		for(int i = 0; i < files.length; i++) {
			orgin = new BufferedInputStream(new FileInputStream(files[i]), Compress.BUFFER_SIZE);
			out.putNextEntry(new ZipEntry(files[i]));
			int count;
			while((count = orgin.read(data, 0, Compress.BUFFER_SIZE)) != -1) {
				out.write(data, 0, count);
			}
			orgin.close();
		}
		out.close();
	}
	
	public static void gZip(File file, String outputFile) throws IOException {
		GZIPOutputStream gz = new GZIPOutputStream(new FileOutputStream(outputFile));
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(file), Compress.BUFFER_SIZE);
		byte[] data = new byte[Compress.BUFFER_SIZE];
		int count;
		while((count = in.read(data, 0, Compress.BUFFER_SIZE)) != -1) {
			gz.write(data, 0, count);
		}
		in.close();
		gz.flush();
		gz.close();
	}
}
