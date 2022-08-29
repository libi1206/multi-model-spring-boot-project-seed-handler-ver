package com.company.project.compoment;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 *  <p>业务线程上下文，每一个请求线程，都会有一个上下文，用于存放请求的上下文数据</p>
 *  用途是插入ThreadLocal参数
 *  @author fuyongchao
 *  @since 2022/1/13 10:59
 */
@Component
public final class BizContextManager {

    private final static ThreadLocal<Map<String,Object>> threadLocal=new ThreadLocal<>();

    /**
     *  <p>给上下文设置值</p>
     *  <b>特别注意：线程结束后必须remove,不然会造成内存泄露</b>
     *  @param
     *  @return
     *  @author fuyongchao
     *  @since 2022/1/13 11:17
     */
    public Object setVal2ThdContext(String key,Object val){
        Map<String, Object> map = threadLocal.get();
        if (map == null){
            map=new HashMap<>();
            threadLocal.set(map);
        }
        map.put(key,val);
        return val;
    }

    /**
     *  <p>从上下文中获取值</p>
     *  @param
     *  @return
     *  @author fuyongchao
     *  @since 2022/1/13 11:18
     */
    public Object getVal4ThdContext(String key){
        Map<String, Object> map = threadLocal.get();
        if (map==null){
            return null;
        }
        return map.get(key);
    }

    /**
     *  <p>从上下文中获取值</p>
     *  @param
     *  @return
     *  @author fuyongchao
     *  @since 2022/1/13 11:18
     */
    public <T> T getVal4ThdContext(String key, Class<T> type) {
        Object val4ThdContext = getVal4ThdContext(key);
        if (type.isInstance(val4ThdContext)) {
            return (T) val4ThdContext;
        }
        return null;
    }

    /**
     *  <p>移除方法</p>
     *  @param
     *  @return
     *  @author fuyongchao
     *  @since 2022/1/13 11:18
     */
    public void removeThdContext(){
        threadLocal.remove();
    }
}
