package com.util.entityGeneratorV2.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

public class AppConfig {
	//public static String absoluteOrRelativePath = "D:/entityGeneratorV2";
	public static String absoluteOrRelativePath = System.getProperty("user.dir");
	public static String fileInputPath = absoluteOrRelativePath + "./db.ini";
	public static String fileOutputPath = absoluteOrRelativePath + "./entityGeneratorOutput";
	
	public static String url;
	public static String username;
	public static String psw;
	public static String catalog;
	public static String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	public static String schemaPattern = null;
	public static String tableNamePattern = null;
	public static String[] types = { "TABLE" };
	public static String outputPath = ".";
	public static String packagePath;
	public static String rootPackageName = "com.mes";

	static {
		Properties properties = new Properties();
		try {
			// properties.load(new FileInputStream(System.getProperty("user.dir") + "./db.ini"));
			properties.load(new FileInputStream(fileInputPath));
			String dbIp = properties.getProperty("dbip");
			String dbPort = properties.getProperty("dbport");
			String dbname = properties.getProperty("dbname");
			catalog = properties.getProperty("dbname");
			username = properties.getProperty("username");
			psw = properties.getProperty("psw");
			packagePath = properties.getProperty("packagePath");
			url = "jdbc:sqlserver://" + dbIp + ":" + dbPort + ";DatabaseName=" + dbname;
		} catch (IOException e) {
			System.err.println("AppConfig fail.");
			e.printStackTrace();
		}
	}
}
