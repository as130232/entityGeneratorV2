package com.util.entityGeneratorV2.model;

import com.util.entityGeneratorV2.util.StringUtil;

import lombok.Data;

@Data
public class Column {
	private String tableCat;
	private String tableSchem;
	private String tableName;
	private String columnName;
	private int dataType;
	private String typeName;
	private int columnSize;
	private int decimalDigits;
	private int numPrecRadix;
	private int nullable;
	private String remarks;
	private String columnDef;
	private int sqlDataType;
	private int sqlDatetimeSub;
	private int charOctetLength;
	private int ordinalPosition;
	private String isNullable;
	private String scopeCatalog;
	private String scopeSchema;
	private String scopeTable;
	private short sourceDataType;
	private String isAutoincrement;
	private String isGeneratedcolumn;
	private boolean isPk = false;


	public String getAttributeName() {
		String attributeName = StringUtil.toCamelCase(this.columnName);
		return StringUtil.toHeadLowerCase(attributeName);
	}

	public String getAttributeType() {
		String attributeType = "String";
		if (this.dataType == -5) {
			attributeType = "Long";
		} else if ((this.dataType == 5) || (this.dataType == 4)) {
			attributeType = "Integer";
		} else if ((this.dataType == 2) || (this.dataType == 3) || (this.dataType == 8)) {
			attributeType = "BigDecimal";
		} else if ((this.dataType == 1) || (this.dataType == 12) || (this.dataType == -9) || (this.dataType == -15)) {
			attributeType = "String";
		} else if ((this.dataType == 91) || (this.dataType == 93)) {
			attributeType = "Date";
		}
		return attributeType;
	}
}
