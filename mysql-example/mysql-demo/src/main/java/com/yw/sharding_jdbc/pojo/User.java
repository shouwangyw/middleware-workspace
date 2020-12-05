package com.yw.sharding_jdbc.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author yangwei
 * @date 2020-10-03 15:28
 */
@Data
@Accessors(chain = true)
public class User {
    private int id;
    private String name;
    private int age;
    private String address;
}
