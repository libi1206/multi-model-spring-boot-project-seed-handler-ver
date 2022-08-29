package com.company.project.handler;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.company.project.bean.SysApiCfgDo;
import com.company.project.compoment.BizContextManager;
import com.company.project.compoment.SysApiCfgManager;
import com.company.project.handler.adaptor.BaseSupperAdaptor;
import com.company.project.response.BaseRequest;
import com.company.project.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;


/**
 *  <p>业务统一入口</p>
 *  @author fuyongchao
 *  @since 2022/1/9 16:29
 */
@RestController
@Slf4j
public class BaseApiHandler {


    @Resource
    private SysApiCfgManager sysApiCfgManager;
    @Resource
    private ApplicationContext applicationContext;
    @Resource
    private BizContextManager bizContextManager;

    @PostMapping(value = "/api/{kindId}")
    public BaseResponse dealApi(@PathVariable(value = "kindId",required = true)String kindId,
                                HttpServletRequest request,
                                HttpServletResponse response
                           ){
        Date startTime=new Date();
        log.info("【Requestor->Api】开始时间：{},接口：{}", DateUtil.format(startTime,"yyyyMMddHHmmss"),kindId);
        String bizId = (String) request.getAttribute("SFCZ_BIZ_ID");
        String accessIp="";
        BaseRequest baseReq=null;
        BaseResponse baseRsp=null;
        try{
            accessIp= BaseSupperAdaptor.paresReqAccessIp(request);
            baseReq= BaseSupperAdaptor.parseBaseReq(request,bizContextManager);
            log.debug("请求参数：{}", JSON.toJSONString(baseReq));
            baseRsp= BaseSupperAdaptor.productBaseRsp(bizId,kindId,baseReq);
            BaseSupperAdaptor adaptor = BaseSupperAdaptor.getAdaptor(bizId,kindId, baseReq, baseRsp, sysApiCfgManager, applicationContext);
            log.info("【Requestor->Api】处理类：{}",adaptor);
            //获取接口配置信息
            SysApiCfgDo sysApiCfg=adaptor.getApiCfg(bizId,kindId, baseReq, baseRsp, sysApiCfgManager);
            //黑白名单校验
            adaptor.validateBlackAndWhiteIp(bizId,sysApiCfg,baseReq,baseRsp,request,response,accessIp);
            //签名校验
            adaptor.validateReqSign(bizId,sysApiCfg,baseReq,baseRsp,request,response);
            //token校验
            adaptor.validateAccessToken(bizId,sysApiCfg,baseReq,baseRsp,request,response);
            //资源权限校验
            adaptor.validateResourceAuth(bizId,sysApiCfg,baseReq,baseRsp,request,response);
            //入参转换
            Object reqData = adaptor.parseINObject(bizId,sysApiCfg, baseReq,baseRsp,request,response);
            //参数校验
            adaptor.validateReqData(bizId,sysApiCfg,reqData,baseReq,baseRsp,request,response);
            //处理业务
            Object rspData=adaptor.handlerBiz(bizId,sysApiCfg, reqData,baseReq,baseRsp,request,response);
            //处理Ext
            JSONObject extData=adaptor.handlerBizExt(bizId,sysApiCfg,baseReq,reqData,baseRsp,rspData,request,response);
            //返回参数校验
            adaptor.validateRspData(bizId,sysApiCfg,reqData,rspData,baseReq,baseRsp,request,response);

            //返回参数包装
            baseRsp=adaptor.putRspData2BaseRsp(baseReq.getDomain(),bizId,sysApiCfg,baseReq,baseRsp,reqData,rspData,extData,request,response);

            //结果返回
            Date endTime=new Date();
            log.info("【Api->Requestor】结束时间：{}}", DateUtil.format(endTime,"yyyyMMddHHmmss"));

        }finally {
            bizContextManager.removeThdContext();
        }
        baseRsp.setTransIDHTime(DateUtil.format(new Date(),"yyyyMMddHHmmss"));
        return baseRsp;
    }
}
