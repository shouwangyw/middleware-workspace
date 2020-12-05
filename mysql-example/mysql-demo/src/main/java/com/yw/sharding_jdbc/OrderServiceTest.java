package com.yw.sharding_jdbc;

import com.yw.sharding_jdbc.pojo.Order;
import com.yw.sharding_jdbc.service.OrderService;

/**
 * @author yangwei
 * @date 2020-10-03 15:47
 */
public class OrderServiceTest {
    public static void main(String[] args) throws Exception {
        OrderService orderService = new OrderService();
        int userId = 10;
        for (int i = 1; i <= 20; i++) {
            if (i >= 10) {
                userId = 21;
            }
            Order order = new Order()
                    .setOrderId(i)
                    .setUserId(userId)
                    .setInfo("订单信息：user_id=" + userId + ",order_id=" + i);

            boolean result = orderService.addOrderInfo(order);
            if (result) {
                System.out.println("订单" + i + "添加成功");
            }
        }
    }
}
