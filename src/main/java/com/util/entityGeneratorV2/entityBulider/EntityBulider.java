package com.util.entityGeneratorV2.entityBulider;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.text.StrSubstitutor;

import com.util.entityGeneratorV2.config.AppConfig;
import com.util.entityGeneratorV2.model.Column;
import com.util.entityGeneratorV2.model.Table;
import com.util.entityGeneratorV2.util.ColumnUtil;
import com.util.entityGeneratorV2.util.PrimaryKeyUtil;
import com.util.entityGeneratorV2.util.StringUtil;
import com.util.entityGeneratorV2.util.TemplateUtil;

public class EntityBulider {
	
	public void buildEntity(Table table) {
		StringBuilder beanTemplate = TemplateUtil.getTemplate("EntityTemplate");
		Map<String, String> valuesMap = new HashMap();
		valuesMap.put("entityPackageName", getEntityPackagePath(table));
		valuesMap.put("generatedDate", new Date().toLocaleString());
		valuesMap.put("tableName", getTableName(table));
		valuesMap.put("entityClassName", table.getEntityClassName());
		boolean isNeedMultiplePKAttribute = false;
		if (table.getPrimaryKeyList().size() > 1) {
			isNeedMultiplePKAttribute = true;
		}
		valuesMap.put("entityAllAttribute", getEntityAllAttribute(table));
		StrSubstitutor sub = new StrSubstitutor(valuesMap);
		String outputString = sub.replace(beanTemplate);
		boolean isPKClass = false;
		File entityFile = this.getOutputFile(isPKClass, table.getEntityClassName());
		
		//若是複合主鍵，則需另外建立複合主鍵java
		if (table.getPrimaryKeyList().size() > 1) {
			bulidMultiplePrimaryKeyClass(table);
		}
		
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(entityFile), "UTF-8"));
			writer.write(outputString);
		} catch (IOException e) {
			e.printStackTrace();
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private String getTableName(Table table) {
		return table.getTableName();
	}

	/**
	 * 建立複合主鍵class
	 * @author charles.chen
	 * @date 2018年5月22日 下午3:10:08
	 */
	private void bulidMultiplePrimaryKeyClass(Table table) {
		String multiplePrimaryKeyClassName = PrimaryKeyUtil.getMultiplePrimaryKeyClassName(table);
		System.out.println("Start build " + table.getTableName() + "'s PK class: " + multiplePrimaryKeyClassName);
		StringBuilder entityTemplate = TemplateUtil.getTemplate("MultiplePrimaryKeyTemplate");
		Map<String, String> valuesMap = new HashMap();
		valuesMap.put("entityPackageName", getEntityPackagePath(table));
		valuesMap.put("entityPrimaryKeyClassName", multiplePrimaryKeyClassName);
		boolean isNeedMultiplePKAttribute = false;
		valuesMap.put("entityAllAttribute", PrimaryKeyUtil.getMultiplePKEntityAllAttribute(table));
		StrSubstitutor sub = new StrSubstitutor(valuesMap);
		String outputString = sub.replace(entityTemplate);
		boolean isPKClass = true;
		File entityFile = this.getOutputFile(isPKClass, table.getEntityClassName());

		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(entityFile), "UTF-8"));
			writer.write(outputString);
		} catch (IOException e) {
			e.printStackTrace();
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private String getEntityPackagePath(Table table) {
//		String tableName = table.getTableName();
//		try {
//			String[] parts = tableName.split("_");
//			return AppConfig.rootPackageName + "." + parts[0].toLowerCase() + "."
//					+ parts[1].substring(0, 3).toLowerCase() + ".model";
//		} catch (Exception exception) {
//		}
		//根據動態配置
		String packagePath = AppConfig.packagePath;
		return packagePath;
	}

	private String getEntityAllAttribute(Table table) {
		StringBuilder sb = new StringBuilder();
		//判斷是否為複合主鍵的class
		boolean isMultiplePK = false;
		if(table.getPrimaryKeyList().size() > 1) {
			isMultiplePK = true;
			sb.append(ColumnUtil.getColumnAttributeForMultiPK(table));
		}
		
		List<Column> columnList = table.getColumnList();
		for (Column column : columnList) {
			sb.append(ColumnUtil.getColumnAttribute(isMultiplePK, column));
		}
		return sb.toString();
	}

	
	private File getOutputFile(boolean isPKClass, String className) {
		String path = null;
		//判斷是否為複合主鍵的class，若是需另外建立複合主鍵class
		if (isPKClass) {
			className = className.replace("Entity", "PK") + ".java";
		} else {
			className = className + ".java";
		}
		//File entityFile = new File(StringUtil.getOutputPath() + "/" + className);
		File entityFile = new File(AppConfig.fileOutputPath + "/" + className);
		//判斷該資料夾是否已存在
		if (entityFile.exists()) {
			entityFile.delete();
			try {
				entityFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			new File(AppConfig.fileOutputPath).mkdirs();
			try {
				entityFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return entityFile;
	}
}
