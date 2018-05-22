package com.util.entityGeneratorV2.model;

import java.util.List;

import com.util.entityGeneratorV2.util.StringUtil;

import lombok.Data;

@Data
public class Table {
	private String tableCat;
	private String tableSchem;
	private String tableName;
	private String tableType;
	private String remarks;
	private String typeCat;
	private String typeSchem;
	private String typeName;
	private String selfReferencingColName;
	private String refGeneration;
	private List<Column> columnList;
	private List<PrimaryKey> primaryKeyList;
	
	public String getEntityClassName() {
		return StringUtil.toCamelCase(this.tableName) + "Entity";
	}
}
