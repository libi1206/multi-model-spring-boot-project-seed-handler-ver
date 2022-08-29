package com.company.project.response;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.company.project.constent.Code;
import lombok.Data;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;


/**
 * @author libi
 */
@Data
public final class BaseResponse {

    /**
     * 请求方平台名称，由溯源平台提供
     */
    @NotBlank(message = "请求方平台名称，由溯源平台提供")
    private String domain;

    /**
     * 业务流水，同请求报文
     */
    @NotBlank(message = "业务流水不允许为空")
    private String transIDO;
    /**
     * 溯源平台业务流水,uuid
     */
    @NotBlank(message = "溯源平台业务流水不允许为空")
    private String transIDH;

    /***
     * 溯源平台处理请求时间14位
     * */
    @Min(value = 14, message = "溯源平台处理请求时间14位")
    @Max(value = 14, message = "溯源平台处理请求时间14位")
    private String transIDHTime;

    /**
     * 应答编码
     */
    @NotNull
    private String rspCode;

    /**
     * 应答描述
     */
    @Max(value = 512)
    @Nullable
    private String rspDesc;

    /**
     * 内容为业务参数对象
     */
    @Nullable
    private Object data;

    /**
     * 其他信息
     */
    @Nullable
    private JSONObject rspExt;

    /**
     * <p>接口已经不能处理的时候，统一异常处理，会使用该方法，原则上不会使用</p>
     *
     * @param
     * @return
     * @author fuyongchao
     * @since 2022/1/9 16:20
     */
    public BaseResponse(String rspCode, String rspDesc, String transIDO, String transIDH) {
        this.rspCode = rspCode;
        this.rspDesc = rspDesc;
        this.transIDO = transIDO;
        this.transIDH = transIDH;
        this.transIDHTime = DateUtil.format(new Date(), "yyyyMMddHHmmss");
    }

    public BaseResponse(String rspCode, String rspDesc) {
        this.rspCode = rspCode;
        this.rspDesc = rspDesc;
    }

    public BaseResponse(Code code) {
        this.rspCode = code.getCode();
        this.rspDesc = code.getMsg();
    }


}
