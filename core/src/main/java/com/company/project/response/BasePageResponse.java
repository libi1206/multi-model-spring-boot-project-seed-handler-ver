package com.company.project.response;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :Libi
 * @version :1.0
 * @date :4/11/22 3:58 PM
 * 基础的分页响应，用泛型进行控制
 */
@Data
public class BasePageResponse<T> {
    /**
     * 当前页面
     */
    private Long current;

    /**
     * 页面大小
     */
    private Long size;

    /**
     * 所有页面数量
     */
    private Long pages;

    /**
     * 所有记录数量
     */
    private Long total;

    /**
     * 页面内容
     */
    private List<T> records;


    /**
     * 将Mybatis Plus的Page对象转换成页面返回对象
     * 只会将成员变量相同的参数进行复制
     *
     * @param page
     * @param type
     * @param <SOURCE>
     * @param <TARGET>
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static <SOURCE, TARGET> BasePageResponse<TARGET> of(IPage<SOURCE> page, Class<TARGET> type) throws IllegalAccessException, InstantiationException {
        BasePageResponse<TARGET> pageResponse = new BasePageResponse<>();
        pageResponse.current = page.getCurrent();
        pageResponse.size = page.getSize();
        pageResponse.pages = page.getPages();
        pageResponse.total = page.getTotal();

        List<TARGET> targetList = new ArrayList<>();
        for (SOURCE record : page.getRecords()) {
            TARGET target = type.newInstance();
            BeanUtils.copyProperties(record, target);
            targetList.add(target);
        }
        pageResponse.records = targetList;
        return pageResponse;
    }

    /**
     * 仅仅填充页码数据
     * @param total
     * @param page
     * @param pageSize
     * @return
     */
    public static BasePageResponse of(Integer total, Integer page, Integer pageSize) {
        BasePageResponse basePageResponse = new BasePageResponse<>();
        basePageResponse.setCurrent(page.longValue());
        basePageResponse.setPages((long) (total / pageSize + (total % pageSize > 0 ? 1 : 0)));
        basePageResponse.setTotal(total.longValue());
        basePageResponse.setSize(pageSize.longValue());
        return basePageResponse;
    }

    /**
     * 自行在外面进行pojo类的转换
     * @param page
     * @param list
     * @return
     * @param <T>
     */
    public static <T> BasePageResponse<T> of(IPage<?> page, List<T> list) {
        BasePageResponse<T> pageResponse = new BasePageResponse<>();
        pageResponse.current = page.getCurrent();
        pageResponse.size = page.getSize();
        pageResponse.pages = page.getPages();
        pageResponse.total = page.getTotal();
        pageResponse.setRecords(list);
        return pageResponse;
    }
}
