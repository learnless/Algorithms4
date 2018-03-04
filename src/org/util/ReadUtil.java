package org.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 扫描文件
 * @author Administrator
 *
 */
public class ReadUtil {

	/**
	 * 获取int数组
	 * @param file
	 * @return
	 */
	public static int[] getInt(String file) {
		String fileName = getFileName(file);
		Scanner sc = null;
		try {
			sc = new Scanner(new FileInputStream(fileName));
			List<Integer> list = new ArrayList<>();
			while (sc.hasNext()) {
				list.add(sc.nextInt());
			}
			int[] array = new int[list.size()];
			for (int i = 0; i < list.size(); i++) {
				array[i] = list.get(i);
			}

			return array;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			sc.close();
		}
		return null;
	}
	
	/**
	 * 获取double数组
	 * @param path
	 * @param file
	 * @return
	 */
	public static double[] getDouble(String file) {
		String fileName = getFileName(file);
		Scanner sc = null;
		try {
			sc = new Scanner(new FileInputStream(fileName));
			List<Double> list = new ArrayList<>();
			while(sc.hasNext()) {
				list.add(sc.nextDouble());
			}
			double[] d = new double[list.size()];
			for (int i = 0; i < list.size(); i++) {
				d[i] = list.get(i);
			}
			return d;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			sc.close();
		}
		
		return null;
	}
	
	/**
	 * 获取String数组
	 */
	public static String[] getString(String file) {
		String fileName = getFileName(file);
		Scanner sc = null;
		try {
			sc = new Scanner(new FileInputStream(fileName));
			List<String> list = new ArrayList<>();
			while(sc.hasNext()) {
				list.add(sc.next());
			}
			String[] d = new String[list.size()];
			for (int i = 0; i < list.size(); i++) {
				d[i] = list.get(i);
			}
			return d;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			sc.close();
		}
		
		return null;
	}
	
	/**
	 * 获取行数组
	 */
	public static String[] getLine(String file) {
		String fileName = getFileName(file);
		Scanner sc = null;
		try {
			sc = new Scanner(new FileInputStream(fileName));
			List<String> list = new ArrayList<>();
			while(sc.hasNext()) {
				list.add(sc.nextLine());
			}
			String[] d = new String[list.size()];
			for (int i = 0; i < list.size(); i++) {
				d[i] = list.get(i);
			}
			return d;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			sc.close();
		}
		
		return null;
	}

	/**
	 * 获取bin目录文件路径
	 * @param file
	 * @return
	 */
	private static String getFileName(String file) {
//		String path = Class.class.getClass().getResource("/").getPath();
		return System.getProperty("user.dir") + File.separator + file;
	}

	public static void main(String[] args) {
		String[] a = getString("tobe.txt");
		System.out.println(a);
	}

}
