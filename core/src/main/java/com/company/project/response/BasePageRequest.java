package com.company.project.response;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author :Libi
 * @version :1.0
 * @date :4/11/22 3:56 PM
 * 基础的分页参数，需要分页的接口参数需要继承这个类
 */
@Data
public class BasePageRequest {
    /**
     * 当前页
     */
    @NotNull(message = "当前页面不能为空")
    private Integer current;

    /**
     * 页面大小
     */
    @NotNull(message = "页面大小不能为空")
    private Integer size;
}
