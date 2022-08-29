package com.company.project.compoment;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.company.project.model.BaseTokenModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author :Libi
 * @version :1.0
 * @date :4/13/22 3:07 PM
 * 获取登陆过的用户信息
 */
@Component
public final class UserManager {
    @Autowired
    private BizContextManager bizContextManager;
    @Autowired
    private TokenManager tokenManager;


    /**
     * 获取用户唯一id
     * @return
     */
    public Integer getLoggedUserId() {
        BaseTokenModel loggedUserInfo = getLoggedUserInfo();
        if (ObjectUtils.isNotEmpty(loggedUserInfo)) {
            return loggedUserInfo.getUserId();
        }
        return null;
    }

    /**
     * 获取基础的用户信息
     * @return
     */
    public BaseTokenModel getLoggedUserInfo() {
        String token = bizContextManager.getVal4ThdContext("ACCESS_TOKEN", String.class);
        return tokenManager.getTokenModelByToken(token);
    }

    /**
     * 获取每个项目独特的用户信息
     * @param type
     * @param <T>
     * @return
     */
    public <T extends BaseTokenModel> T getLoggedUserInfo(Class<T> type) {
        String token = bizContextManager.getVal4ThdContext("ACCESS_TOKEN", String.class);
        return tokenManager.getTokenModelByToken(token, type);
    }
}
