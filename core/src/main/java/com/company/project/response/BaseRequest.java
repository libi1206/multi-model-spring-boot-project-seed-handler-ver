package com.company.project.response;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * @author :Libi
 * @version :1.0
 * @date :4/8/22 10:36 AM
 */
@Data
public final class BaseRequest {
    /**
     * 请求方平台名称，由溯源平台提供
     * */
    @NotBlank(message = "请求方平台名称")
    private String domain;

    /**
     * 请求方当前时间14
     * 20210831095301
     * */
    @Min(value = 14,message = "固定长度14位")
    @Max(value = 14,message = "固定长度14位")
    private String timeStamp;

    /**
     * 请求方业务唯一流水,使用uuid，中间不带-
     * */
    @NotBlank(message = "请求方业务唯一流水不允许为空")
    @Max(value = 32,message = "请求方业务唯一流水最大长度是32")
    private String transIDO;

    /**
     * 签名方式
     * */
    @Nullable
    @Max(value = 32,message = "签名方式最大32")
    private String signMethod="MD5";

    /**
     * 签名(后续需要校验重放攻击需要使用)
     * */
    @Nullable
    @Max(value = 1024,message = "签名最大长度1024")
    private String sign;

    /**
     * 服务使用方token
     * */
    @Nullable
    private String accessToken;

    /**
     * 服务版本号，由溯源平台提供
     * */
    @NotBlank(message = "服务版本号不允许为空")
    private String apiVersion;

    /**
     * 日切值（8位日期）
     * 20210831
     * */
    @Min(value = 8,message = "日切值固定8位长度")
    @Max(value = 8,message = "日切值固定8位长度")
    private String cutOffDay;

    /**
     * 请求对象，可以为null
     * */
    @Nullable
    private JSONObject data;

    /**
     * 其他信息
     * */
    @Nullable
    private JSONObject reqExt;
}
