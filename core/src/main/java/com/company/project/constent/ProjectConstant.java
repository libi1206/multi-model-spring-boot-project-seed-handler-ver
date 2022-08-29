package com.company.project.constent;

/**
 * 项目常量
 */
public final class ProjectConstant {
    /**
     * JWT生成密钥
     * Base64算法生成的字符串
     */
    public static final String JWT_SECRET = "SFlQRVJfQ0hBSU5fTkZU";

    /**
     * 密码加密和校验校验需要的盐
     */
    public static final String SALT_PREFIX = "$1$_";
    public static final String PASSWORD_MD5_SALT = SALT_PREFIX + "jndy2ldu";

    /**
     * 请求携带的token的header名称
     */
    public static final String TOKEN_HEADER_NAME = "Access-Token";
    public static final String UTF8 = "utf-8";


}
