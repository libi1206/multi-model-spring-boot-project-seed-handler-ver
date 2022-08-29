package com.company.project.response;

import lombok.Data;
import org.springframework.util.ObjectUtils;

/**
 * @author libi@hyperchain.cn
 * @date 2022/5/20 11:09 AM
 * 流式翻页布局下的翻页请求
 */
@Data
public class BaseStreamPageRequest {
    /**
     * 上一页最后的id，没有就从头开始
     */
    private String lastId;
    /**
     * 页面大小
     */
    private Integer size;

    public Long getLastId() {
        if (ObjectUtils.isEmpty(lastId)) {
            return null;
        }
        return Long.parseLong(lastId);
    }

    public String getLastIdStr() {
        return lastId;
    }
}
