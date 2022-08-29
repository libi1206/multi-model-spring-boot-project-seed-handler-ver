package com.company.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.company.project.bean.SysApiCfgDo;
import com.company.project.dao.SysApiCfgDao;
import com.company.project.service.SysApiCfgService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @author  generator
 * @description 接口配置表
 * @date 2022-04-08 16:08:15
 */
@Service("sysApiCfgService" )
public class SysApiCfgServiceImpl extends ServiceImpl<SysApiCfgDao, SysApiCfgDo> implements SysApiCfgService {

    @Override
    public SysApiCfgDo getSysApiCfg(String kindId, String apiVersion) {
        QueryWrapper<SysApiCfgDo> wrapper = new QueryWrapper<>();
        wrapper.eq("kind_id", kindId);
        wrapper.eq("api_version", apiVersion);
        return getOne(wrapper);
    }

    @Override
    public List<SysApiCfgDo> getApiList() {
        QueryWrapper<SysApiCfgDo> queryWrapper=new QueryWrapper<>();
        queryWrapper.orderByAsc("insert_time");
        return list(queryWrapper);
    }
}
