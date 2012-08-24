package com.tommytony.io;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class CheckSums {

	
	public static byte[] generateMD5Checksum(String data) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		return md.digest(data.getBytes(Charset.forName("UTF-8")));
	}
	
	public static String generateMD5ChecksumToString(String data) throws NoSuchAlgorithmException {
		return new String(CheckSums.generateMD5Checksum(data));
	}
	
	public static byte[] generateSHA1Checksum(String data) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		return md.digest(data.getBytes(Charset.forName("UTF-8")));
	}
	
	public static String generateSHA1ChecksumToString(String data) throws NoSuchAlgorithmException {
		return new String(CheckSums.generateSHA1Checksum(data));
	}
	
	/*
	 * Returns in the format of a byte array with 36 index
	 * the first 20 bytes are the sha-1 checksum
	 * the next 16 are md5 checksum
	 */
	public static byte[] generateSHA1AndMD5Checksum(String data) throws NoSuchAlgorithmException {
		byte[] checksum = new byte[36];
		byte[] md5_checksum;
		byte[] sha1_checksum;
		md5_checksum = MessageDigest.getInstance("MD5").digest(data.getBytes(Charset.forName("UTF-8")));
		sha1_checksum = MessageDigest.getInstance("SHA-1").digest(data.getBytes(Charset.forName("UTF-8")));
		for(int i = 0; i < sha1_checksum.length; i++) {
			checksum[i] = sha1_checksum[i];
		}
		for(int j = 0; j < md5_checksum.length; j++) {
			checksum[j + 20] = md5_checksum[j];
		}
		return checksum;
	}
	
	public static String generateSHA1AndMD5ChecksumToString(String data) throws NoSuchAlgorithmException {
		return new String(CheckSums.generateSHA1AndMD5Checksum(data));
	}
	
	public static byte[] generateIterativeMD5Checksum(String data, int iterations) throws NoSuchAlgorithmException {
		byte[] dataBytes = CheckSums.generateMD5Checksum(data);
		for(int i = 0; i < (iterations - 1); i++) {
			dataBytes = CheckSums.generateMD5Checksum(new String(dataBytes));
		}
		return dataBytes;
	}
	
	public static String generateIterativeMD5ChecksumToString(String data, int iterations) throws NoSuchAlgorithmException {
		return new String(CheckSums.generateIterativeMD5Checksum(data, iterations));
	}
	
	public static byte[] generateIterativeSHA1Checksum(String data, int iterations) throws NoSuchAlgorithmException {
		byte[] dataBytes = CheckSums.generateSHA1Checksum(data);
		for(int i = 0; i < (iterations - 1); i++) {
			dataBytes = CheckSums.generateSHA1Checksum(new String(dataBytes));
		}
		return dataBytes;
	}
	
	public static String generateIterativeSHA1ChecksumToString(String data, int iterations) throws NoSuchAlgorithmException {
		return new String(CheckSums.generateIterativeSHA1Checksum(data, iterations));
	}
	
	/*
	 * Returns data in format of a String Array
	 * the first index is the regular salt
	 * the second index is the MD5 Hashed message with salt
	 */
	
	public static String[] generateMD5ChecksumWithSalt(String data) throws NoSuchAlgorithmException {
		SecureRandom rand = SecureRandom.getInstance("SHA1PRNG");
		byte[] saltB = new byte[16];
		rand.nextBytes(saltB);
		String salt = new String(saltB);
		String[] strings = new String[2];
		strings[0] = salt;
		strings[1] = CheckSums.generateMD5ChecksumToString(salt + data);
	    return strings;
	}
	
	public static String[] generateIterativeMD5ChecksumWithSalt(String data, int iterations) throws NoSuchAlgorithmException {
		SecureRandom rand = SecureRandom.getInstance("SHA1PRNG");
		byte[] saltB = new byte[16];
		rand.nextBytes(saltB);
		String salt = new String(saltB);
		String[] strings = new String[2];
		strings[0] = salt;
		strings[1] = CheckSums.generateIterativeMD5ChecksumToString(salt + data, iterations);
		return strings;
	}
	
	public static String[] generateSHA1ChecksumWithSalt(String data) throws NoSuchAlgorithmException {
		SecureRandom rand = SecureRandom.getInstance("SHA1PRNG");
		byte[] saltB = new byte[16];
		rand.nextBytes(saltB);
		String salt = new String(saltB);
		String[] strings = new String[2];
		strings[0] = salt;
		strings[1] = CheckSums.generateSHA1ChecksumToString(salt + data);
		return strings;
	}
	
	public static String[] generateIterativeSHA1ChecksumWithSalt(String data, int iterations) throws NoSuchAlgorithmException {
		SecureRandom rand = SecureRandom.getInstance("SHA1PRNG");
		byte[] saltB = new byte[16];
		rand.nextBytes(saltB);
		String salt = new String(saltB);
		String[] strings = new String[2];
		strings[0] = salt;
		strings[1] = CheckSums.generateIterativeSHA1ChecksumToString(salt + data, iterations);
		return strings;
	}
}
