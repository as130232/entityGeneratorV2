package com.util.entityGeneratorV2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.util.entityGeneratorV2.config.AppConfig;
import com.util.entityGeneratorV2.entityBulider.EntityBulider;
import com.util.entityGeneratorV2.model.Table;
import com.util.entityGeneratorV2.util.DataBaseUtil;

@SpringBootApplication
public class EntityGeneratorV2Application {

	public static void main(String[] args) {
		Connection connection = null;
		List<Table> tableList = null;
		try {
			Class.forName(AppConfig.driverName);
			connection = DriverManager.getConnection(AppConfig.url, AppConfig.username, AppConfig.psw);
			tableList = DataBaseUtil.getAllTables(connection);
			EntityBulider entityBulider = new EntityBulider();
			for (Table table : tableList) {
				System.out.println("Start build " + table.getTableName() + " entity.");
				entityBulider.buildEntity(table);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
