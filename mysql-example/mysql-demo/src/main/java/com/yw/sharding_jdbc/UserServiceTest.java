package com.yw.sharding_jdbc;

import com.yw.sharding_jdbc.pojo.User;
import com.yw.sharding_jdbc.service.UserService;

import java.util.List;

/**
 * @author yangwei
 * @date 2020-10-03 16:15
 */
public class UserServiceTest {
    public static void main(String[] args) throws Exception {
        UserService userService = new UserService();

        User user = new User()
                .setName("武松")
                .setAge(23)
                .setAddress("清河县");

        boolean result = userService.addUser(user);
        if (result) {
            System.out.println("添加用户成功");
        }

        List<User> users = userService.getUserList();
        users.forEach(System.out::println);

        users = userService.getUserList();
        users.forEach(System.out::println);
    }
}
