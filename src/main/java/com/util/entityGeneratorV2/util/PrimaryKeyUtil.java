package com.util.entityGeneratorV2.util;

import java.util.List;

import com.util.entityGeneratorV2.model.Column;
import com.util.entityGeneratorV2.model.Table;

public class PrimaryKeyUtil {
	public static String getMultiplePrimaryKeyClassName(Table table) {
		String multiplePrimaryKeyClassName = StringUtil.toCamelCase(table.getTableName()) + "PK";
		return multiplePrimaryKeyClassName;
	}

//	public static String getKeyConstructorArgs(Table table) {
//		String primaryKeyClassName = getPrimaryKeyClassName(table);
//		StringBuilder sb = new StringBuilder();
//		sb.append(primaryKeyClassName + " " + StringUtil.toHeadLowerCase(primaryKeyClassName));
//		return sb.toString();
//	}
//
//	public static String getKeyConstructorArgsAssign(Table table) {
//		String primaryKeyClassName = getPrimaryKeyClassName(table);
//		StringBuilder sb = new StringBuilder();
//		sb.append("\t\tthis." + StringUtil.toHeadLowerCase(primaryKeyClassName) + " = "
//				+ StringUtil.toHeadLowerCase(primaryKeyClassName) + ";" + System.getProperty("line.separator"));
//		return sb.toString();
//	}

	/**
	 * 取得複合主鍵Entity中所有屬性，即複合主鍵
	 * @author charles.chen
	 * @date 2018年5月22日 下午4:12:00
	 * @param table
	 * @return String
	 */
	public static String getMultiplePKEntityAllAttribute(Table table) {
		StringBuilder sb = new StringBuilder();
		List<Column> columnList = table.getColumnList();
		
		//判斷是否為複合主鍵的class
		boolean isMultiplePK = false;
		if(table.getPrimaryKeyList().size() > 1) {
			isMultiplePK = true;
		}
		for (Column column : columnList) {
			if (column.isPk()) {
				sb.append(ColumnUtil.getColumnAttribute(isMultiplePK, column));
			}
		}
		return sb.toString();
	}

//	public static String getEntityAllGetterAndSetter(Table table) {
//		StringBuilder sb = new StringBuilder();
//		List<Column> columnList = table.getColumnList();
//		for (Column column : columnList) {
//			if (column.isPk()) {
//				sb.append(ColumnUtil.getColumnGetterAndSetter(column));
//			}
//		}
//		return sb.toString();
//	}
}
