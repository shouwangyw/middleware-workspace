package com.yw.sharding_jdbc.service;

import com.yw.sharding_jdbc.datasource.MasterSlaveDataSource;
import com.yw.sharding_jdbc.pojo.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yangwei
 * @date 2020-10-03 15:31
 */
public class UserService {
    public boolean addUser(User user) throws Exception {
        DataSource dataSource = MasterSlaveDataSource.getDataSource();
        String sql = "insert into t_user0(name, age, address) values (?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, user.getName());
            ps.setInt(2, user.getAge());
            ps.setString(3, user.getAddress());
            return ps.execute();
        }
    }

    public List<User> getUserList() throws Exception {
        DataSource dataSource = MasterSlaveDataSource.getDataSource();
        String sql = "select id, name, age, concat(address, @@hostname) as address from t_user0";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<User> users = new ArrayList<>();
            while (rs.next()) {
                User user = new User()
                        .setId(rs.getInt("id"))
                        .setName(rs.getString("name"))
                        .setAge(rs.getInt("age"))
                        .setAddress(rs.getString("address"));

                users.add(user);
            }
            return users;
        }
    }
}
