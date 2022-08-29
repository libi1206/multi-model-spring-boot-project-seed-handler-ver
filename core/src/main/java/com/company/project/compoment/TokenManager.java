package com.company.project.compoment;


import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.company.project.model.BaseTokenModel;
import com.company.project.util.JwtUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author libi
 * @since 2022/4/12 10:18
 */
@Component
public class TokenManager {
    private static final String TOKEN_INFO_CACHE = "TokenInfoCache";

    @Autowired
    private BizContextManager bizContextManager;

    /**
     * 生成token
     * token中携带的信息只能是 BaseTokenModel 或他的子类
     * @param tokenInfo
     * @param timeoutMillis
     * @return
     */
    public String createToken(BaseTokenModel tokenInfo, Long timeoutMillis) {
        return JwtUtil.createJwtToken(tokenInfo, timeoutMillis);
    }

    /**
     * 解析token，获取基础信息
     * @param token
     * @return
     */
    @SneakyThrows
    public BaseTokenModel getTokenModelByToken(String token) {
        if (token == null) {
            return null;
        }
        BaseTokenModel tokenModel = bizContextManager.getVal4ThdContext(TOKEN_INFO_CACHE, BaseTokenModel.class);
        if (ObjectUtils.isNotEmpty(tokenModel)) {
            return tokenModel;
        }
        tokenModel = JwtUtil.parseJwt(token, BaseTokenModel.class);
        bizContextManager.setVal2ThdContext(TOKEN_INFO_CACHE, tokenModel);
        return tokenModel;
    }

    /**
     * 获取token中的信息，并且将其转化为子类
     * @param token
     * @param type
     * @param <T>
     * @return
     */
    @SneakyThrows
    public <T extends BaseTokenModel> T getTokenModelByToken(String token, Class<T> type) {
        if (token == null) {
            return null;
        }
        T tokenModel = bizContextManager.getVal4ThdContext(TOKEN_INFO_CACHE, type);
        if (ObjectUtils.isNotEmpty(tokenModel)) {
            return tokenModel;
        }
        tokenModel = JwtUtil.parseJwt(token, type);
        bizContextManager.setVal2ThdContext(TOKEN_INFO_CACHE, tokenModel);
        return tokenModel;
    }

}
