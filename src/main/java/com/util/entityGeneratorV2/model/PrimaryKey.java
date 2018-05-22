package com.util.entityGeneratorV2.model;

import lombok.Data;

@Data
public class PrimaryKey {
	private String tableCat;
	private String tableSchem;
	private String tableName;
	private String columnName;
	private short keySeq;
	private String pkName;
}
