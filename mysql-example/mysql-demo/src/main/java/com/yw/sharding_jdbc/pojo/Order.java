package com.yw.sharding_jdbc.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author yangwei
 * @date 2020-10-03 15:29
 */
@Data
@Accessors(chain = true)
public class Order {
    private int orderId;
    private int userId;
    private String info;
}