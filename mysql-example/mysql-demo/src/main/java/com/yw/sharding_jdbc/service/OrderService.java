package com.yw.sharding_jdbc.service;

import com.yw.sharding_jdbc.datasource.ShardingDataSource;
import com.yw.sharding_jdbc.pojo.Order;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * @author yangwei
 * @date 2020-10-03 15:31
 */
public class OrderService {
    public boolean addOrderInfo(Order order) throws Exception {
        DataSource dataSource = ShardingDataSource.getDataSource();
        String sql = "insert into t_order(order_id, user_id, info) values (?,?,?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, order.getOrderId());
            ps.setInt(2, order.getUserId());
            ps.setString(3, order.getInfo());

            return ps.execute();
        }
    }
}
