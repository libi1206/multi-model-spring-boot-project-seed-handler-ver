package com.company.project.constent;

import com.company.project.util.StringUtils;

import java.util.Map;

public enum Code {
    //通用部分
    SUCCESS("00000", "成功"),

    //参数校验部分
    PARAM_VERIFY_ERROR("10000", "${msg}"),
    PARAM_API_VERSION_NOT_FIND("10001", "缺少必要的请求参数版本号（apiVersion）"),
    PARAM_RSP_BODY_NOT_FIND("10002", "缺少必要的请求体参数"),
    PARAM_REQUEST_PARSE_ERROR("10003", "请求参数转换失败，请确认入参是否符合规范"),
    //权限校验部分
    AUTH_REFUSE("11001", "禁止接入系统，请使用被允许的系统访问"),
    AUTH_TOKEN_VERIFY_ERROR("11002","无效的接入token，请先登录"),
    AUTH_TOKEN_EXPIRED("11002","token过期，请重新登录"),
    AUTH_ACCESS_NOT_ALLOWED("11003", "接入用户不允许访问该资源，请提升用户权限"),
    AUTH_FILE_TYPE_NOT_ALLOWED("11004", "不被允许上传的文件类型"),



    //系统部分
    SYSTEM_ERROR("90000", "系统异常：${msg}"),
    SYSTEM_API_NOT_FIND("90001", "根据kindId=${kindId},apiVersion=${apiVersion}未获取到有效的接口配置，请联系系统管理员"),
    SYSTEM_ADAPTOR_NOT_FIND("90002", "根据类路径[${apiAdaptorClass}]未获取到有效的接口处理对象，请联系系统管理员"),
    SYSTEM_REQ_MODEL_NOT_FIND("90003", "根据类路径[${reqModelClass}]未获取到有效的Class信息，请联系系统管理员"),



    UNKNOWN_ABNORMAL("9999","未知异常");


    private String code;
    private String msg;

    // 构造方法
    Code(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public String getMsg(Map<String, String> params) {
        return StringUtils.replaceReg(msg, params);
    }

    public String  getCode() {
        return code;
    }

    public static String getMsgByCodeStr(String code) {
        for (Code e : Code.values()) {
            if (e.getCode().equals(code)) {
                return e.msg;
            }
        }
        throw new IllegalArgumentException("未定义的code码:" + code);
    }

    public static Code getCodeByCodeStr(String codeStr) {
        for (Code code : Code.values()) {
            if (code.getCode().equals(codeStr)) {
                return code;
            }
        }
        throw new IllegalArgumentException("未定义的code码:" + codeStr);
    }

}
