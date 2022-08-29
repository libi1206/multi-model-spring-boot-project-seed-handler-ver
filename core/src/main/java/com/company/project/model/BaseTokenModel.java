package com.company.project.model;

import lombok.Data;

/**
 * @author :Libi
 * @version :1.0
 * @date :2020-10-21 16:11
 * 这里装解析token后的信息
 * 这里是最基础的信息，如果（比如商户端）有自己独特的信息，可以继承这个类，在子类里放独特的信息
 */
@Data
public class BaseTokenModel {
    /**
     * 用户唯一id
     */
    private Integer userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 用户权限列表
     */
    private String authList;
}
