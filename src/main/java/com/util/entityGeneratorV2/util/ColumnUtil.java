package com.util.entityGeneratorV2.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.text.StrSubstitutor;

import com.util.entityGeneratorV2.model.Column;
import com.util.entityGeneratorV2.model.Table;

public class ColumnUtil {
	public static String getColumnAttribute(boolean isMultiplePK, Column column) {
		StringBuilder sb = TemplateUtil.getTemplate("ColumnAttributeTemplate");
		Map<String, String> valuesMap = new HashMap();
		//判斷該欄位是否為PK，並增加對應PK annotation
		if ((column.isPk()) && ("yes".equals(column.getIsAutoincrement().trim().toLowerCase()))) {
			sb.insert(0, "\t@Id" + System.getProperty("line.separator")
					+ "\t@GeneratedValue(strategy = GenerationType.IDENTITY)" + System.getProperty("line.separator"));
		} else if (column.isPk() && !isMultiplePK) {
			//必須篩選非複合主鍵的class，複合主鍵class不需要加@Id
			sb.insert(0, "\t@Id" + System.getProperty("line.separator"));
		}
		valuesMap.put("columnName", column.getColumnName());
		valuesMap.put("attributeName", column.getAttributeName());
		valuesMap.put("attributeType", column.getAttributeType());

		StrSubstitutor sub = new StrSubstitutor(valuesMap);
		return sub.replace(sb.toString());
	}

	public static String getColumnAttributeForMultiPK(Table table) {
		StringBuilder sb = TemplateUtil.getTemplate("ColumnAttributeForPKTemplate");
		String primaryKeyClassName = PrimaryKeyUtil.getMultiplePrimaryKeyClassName(table);
		Map<String, String> valuesMap = new HashMap();
		valuesMap.put("attributeName", StringUtil.toHeadLowerCase(primaryKeyClassName));
		valuesMap.put("attributeType", primaryKeyClassName);
		StrSubstitutor sub = new StrSubstitutor(valuesMap);
		return sub.replace(sb.toString());
	}

}
