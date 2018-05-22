package com.util.entityGeneratorV2.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class TemplateUtil {
	private static Map<String, StringBuilder> templateMap;
	//resource/template中的文字樣板名稱
	private static String[] names = { "EntityTemplate.txt", "ColumnAttributeTemplate.txt", "ColumnAttributeForPKTemplate.txt", 
			//"GetterSetterTemplate.txt", "KeyConstructorTemplate.txt",
			"MultiplePrimaryKeyTemplate.txt" };

	static {
		templateMap = new HashMap();
		for (String templateName : names) {
			StringBuilder sb = new StringBuilder();
			BufferedReader reader = null;
			try {
				try {
					//載入模板
					reader = new BufferedReader(new InputStreamReader(
							TemplateUtil.class.getClassLoader().getResourceAsStream("template/" + templateName)));
				} catch (NullPointerException e) {
					System.err.println("Template Not Found..");
				}
				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line + System.getProperty("line.separator"));
				}
				templateMap.put(templateName.replace(".txt", ""), sb);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public static StringBuilder getTemplate(String templateName) {
		StringBuilder sb = new StringBuilder(((StringBuilder) templateMap.get(templateName)).toString());
		return sb;
	}
}
