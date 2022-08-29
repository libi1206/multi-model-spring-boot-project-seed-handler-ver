package com.company.project.response;

import lombok.Data;

import java.util.List;

/**
 * @author libi@hyperchain.cn
 * @date 2022/5/20 11:11 AM
 * 流式翻页布局下的翻页响应
 * 为了避免翻页时列表变化导致的列表上重复而
 */
@Data
public class BaseStreamPageResponse<T> {
    private String lastId;

    private Integer size;

    private List<T> records;


    public static <T> BaseStreamPageResponse<T> of(Long lastId, List<T> records) {
        BaseStreamPageResponse<T> result = new BaseStreamPageResponse<>();
        result.setLastId(lastId == null ? null : lastId.toString());
        result.setRecords(records);
        result.setSize(records.size());
        return result;
    }

    public static <T> BaseStreamPageResponse<T> of(String lastId, List<T> records) {
        BaseStreamPageResponse<T> result = new BaseStreamPageResponse<>();
        result.setLastId(lastId);
        result.setRecords(records);
        result.setSize(records.size());
        return result;
    }
}
