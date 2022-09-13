package com.company.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.company.project.bean.SysApiCfg;

import java.util.List;

/**
 *
 * @author  generator
 * @description 接口配置表
 * @date 2022-04-08 16:08:15
 */
public interface SysApiCfgService extends IService<SysApiCfg> {
    SysApiCfg getSysApiCfg(String kindId, String apiVersion);

    List<SysApiCfg> getApiList();
}

