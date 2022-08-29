package com.company.project.compoment;

import com.company.project.bean.SysApiCfgDo;
import com.company.project.service.SysApiCfgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component(value = "sysApiCfgManager")
public class SysApiCfgManager {

    /**
     * key kindId|apiVersion
     * value sysApiCfg
     * */
    private Map<String, SysApiCfgDo> cache=new HashMap<>();
    private Map<String,SysApiCfgDo> cacheTemp=new HashMap<>();
    /**
     * 是否正在初始化
     * true :正在初始化   false:未在初始化
     * */
    private volatile boolean init=false;

    @Resource
    private SysApiCfgService sysApiCfgService;

    /**
     *  <p>初始化（重载）返回码信息</p>
     *  @return 重载返回码的结果
     *  @author fuyongchao
     *  @since 2022/1/9 13:55
     */
    @PostConstruct
    public synchronized boolean reloadMap(){
        List<SysApiCfgDo> list = sysApiCfgService.getApiList();
        if (list !=null && list.size() >0){
            Map<String, SysApiCfgDo> temp=new HashMap<>();
            for (SysApiCfgDo api:list){
                temp.put(api.getKindId()+"|"+api.getApiVersion(),api);
            }
            this.cacheTemp.clear();
            this.cacheTemp.putAll(this.cache);
            this.init=true;
            this.cache.clear();
            this.cache.putAll(temp);
            this.init=false;
            log.info("合计加载表【sys_api_cfg】数据：{}条",temp.size());
            return true;
        }
        log.info("合计加载表【sys_api_cfg】数据：{}条",0);
        return true;
    }
    /**
     *  <p>根据kindId+version获取Api配置信息</p>
     *  @param
     *  @return
     *  @author fuyongchao
     *  @since 2022/1/9 13:57
     */
    public SysApiCfgDo getSysApiCfg(String kindId,String apiVersion){
        if(init){
            return this.cacheTemp.get(kindId+"|"+apiVersion);
        }
        return this.cache.get(kindId+"|"+apiVersion);
    }
}
