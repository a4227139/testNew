package com.io.nio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordsCountNormalNIO {

	public static Map<String, Integer> map = new HashMap<String, Integer>();
	static final int BUFFER_SIZE = 1024 * 10;
	static byte[] left = new byte[0];
	static final int LF = 10;// ���з� ASCII
	static final int CR = 13;// �س��� ASCII

	@SuppressWarnings("resource")
	public static void main(String args[]) throws Exception {
		File fin = new File("E:\\Alllog.log");
		long start = System.currentTimeMillis();
		FileChannel fcin = new RandomAccessFile(fin, "r").getChannel();
		ByteBuffer buf = ByteBuffer.allocate(BUFFER_SIZE);
		readFileByLine(fcin, buf);
		long end = System.currentTimeMillis();
		System.out.println("spend time :" + (end - start));
		show();
	}

	public static void readFileByLine(FileChannel fcin, ByteBuffer buf) {
		byte[] lineByte = new byte[0];
		String encode = "GBK";//"UTF-8"
		try {
			while (fcin.read(buf) != -1) {
				int size = buf.position();//��ȡ�������λ�ã��൱�ڶ�ȡ�ĳ���
				byte[] byteArr = new byte[size];
				buf.rewind();// rewind�����ض�,�佫position���0,��������,�޷�ʹ�������get����
				buf.get(byteArr);//����b
				buf.clear();
				
				int startNum = 0;
				boolean hasLF = false;// �Ƿ��л��з�
				for (int i = 0; i < size; i++) {
					if (byteArr[i] == LF) {
						hasLF = true;
						int leftNum = left.length;
						int lineNum = i - startNum;
						lineByte = new byte[leftNum + lineNum];// �����С�Ѿ�ȥ�����з�
						System.arraycopy(left, 0, lineByte, 0, leftNum);// �����lineByte[0]~lineByte[leftNum-1]
						System.arraycopy(byteArr, startNum, lineByte, leftNum, lineNum);// ���lineByte[leftNum]~lineByte[leftNum+lineNum-1]
						left = new byte[0];
						String line = new String(lineByte,encode);// һ���������ַ���(�����˻��кͻس�)
						deal(line);
						// System.out.println(line);
						// ���˻س����ͻ��з�
						if (i + 1 < size && byteArr[i + 1] == CR) {
							startNum = i + 2;
						} else {
							startNum = i + 1;
						}
					}
				}
				if (hasLF) {
					left = new byte[byteArr.length - startNum];
					System.arraycopy(byteArr, startNum, left, 0, left.length);
				} else {// ���ݵ��ζ�ȡ�����ݲ���һ�е����
					byte[] toleft = new byte[left.length + byteArr.length];
					System.arraycopy(left, 0, toleft, 0, left.length);
					System.arraycopy(byteArr, 0, toleft, left.length, byteArr.length);
					left = toleft;
				}
			}
			if (left != null && left.length > 0) {// �����ļ����һ��û�л��е����
				String line = new String(left, 0, left.length, encode);
				deal(line);
				// System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void deal(String line) {
		Pattern pattern = Pattern.compile("[A-Z]?[a-z]+");
		Matcher matcher = pattern.matcher(line);
		while (matcher.find()) {
			String word = matcher.group();
			if (map.containsKey(word)) {
				map.put(word, map.get(word) + 1);
			} else {
				map.put(word, 1);
			}
		}
	}

	public static void show() {
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			System.out.println(entry.getKey() + " : " + entry.getValue());
		}
	}
}
