package com.yw.sharding_jdbc.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shardingsphere.api.config.masterslave.MasterSlaveRuleConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.MasterSlaveDataSourceFactory;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author yangwei
 * @date 2020-10-03 15:04
 */
public class MasterSlaveDataSource {
    private static DataSource dataSource;

    public static DataSource getDataSource() {
        if (dataSource != null) {
            return dataSource;
        }
        try {
            return create();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private static DataSource create() throws SQLException{
        // 配置真实数据源
        Map<String, DataSource> dataSourceMap = new HashMap<>(2);

        // 配置第一个数据源
        DruidDataSource masterDataSource = new DruidDataSource();
        masterDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        masterDataSource.setUrl("jdbc:mysql://192.168.254.128:3306/test?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8");
        masterDataSource.setUsername("root");
        masterDataSource.setPassword("123456");
        dataSourceMap.put("master", masterDataSource);

        // 配置第二个数据源
        DruidDataSource slaveDataSource1 = new DruidDataSource();
        slaveDataSource1.setDriverClassName("com.mysql.cj.jdbc.Driver");
        slaveDataSource1.setUrl("jdbc:mysql://192.168.254.129:3306/test?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8");
        slaveDataSource1.setUsername("root");
        slaveDataSource1.setPassword("123456");
        dataSourceMap.put("slave1", slaveDataSource1);

        // 配置第三个数据源
        DruidDataSource slaveDataSource2 = new DruidDataSource();
        slaveDataSource2.setDriverClassName("com.mysql.cj.jdbc.Driver");
        slaveDataSource2.setUrl("jdbc:mysql://192.168.254.130:3306/test?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8");
        slaveDataSource2.setUsername("root");
        slaveDataSource2.setPassword("123456");
        dataSourceMap.put("slave2", slaveDataSource2);

        // 配置主从复制数据源
        MasterSlaveRuleConfiguration config = new MasterSlaveRuleConfiguration("masterSlaveDataSource", "master", Arrays.asList("slave1", "slave2"));

        // 创建数据源
        return MasterSlaveDataSourceFactory.createDataSource(dataSourceMap, config, new Properties());
    }
}
