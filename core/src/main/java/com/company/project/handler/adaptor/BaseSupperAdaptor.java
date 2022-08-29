package com.company.project.handler.adaptor;

import cn.hutool.core.util.ReUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.company.project.bean.SysApiCfgDo;
import com.company.project.compoment.BizContextManager;
import com.company.project.compoment.SysApiCfgManager;
import com.company.project.compoment.TokenManager;
import com.company.project.constent.Code;
import com.company.project.constent.ProjectConstant;
import com.company.project.exception.BusinessException;
import com.company.project.model.BaseTokenModel;
import com.company.project.response.BaseRequest;
import com.company.project.response.BaseResponse;
import com.company.project.util.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


@Slf4j
public abstract class BaseSupperAdaptor<IN,OUT> {

    private static ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

    @Resource
    private TokenManager tokenManager;

    /**
     *  <p>解析请求参数</p>
     *  @param
     *  @return
     *  @author fuyongchao
     *  @since 2022/1/9 17:59
     */
    public static BaseRequest parseBaseReq(HttpServletRequest request, BizContextManager bizContextManager){
        BaseRequest baseReq=null;
        String req="";
        try {
            req= IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8);
        }catch (Exception e) {
            log.error("【Requestor->Api】请求参数解析失败",e);
            throw new BusinessException(Code.PARAM_REQUEST_PARSE_ERROR);
        }

        log.info("【Requestor->Api】请求参数：{}",req);
        if (StringUtils.isBlank(req)){
            throw new BusinessException(Code.PARAM_RSP_BODY_NOT_FIND);
        }

        baseReq= JSON.toJavaObject(JSON.parseObject(req),BaseRequest.class);
        if (baseReq == null){
            throw new BusinessException(Code.PARAM_RSP_BODY_NOT_FIND);
        }
        String apiVersion=baseReq.getApiVersion();
        if (StringUtils.isBlank(apiVersion)){
            throw new BusinessException(Code.PARAM_API_VERSION_NOT_FIND);
        }

        String accessToken=request.getHeader(ProjectConstant.TOKEN_HEADER_NAME);
        if (StringUtils.isNotBlank(accessToken)){
            baseReq.setAccessToken(accessToken);
        }

        bizContextManager.setVal2ThdContext("ACCESS_TOKEN",baseReq.getAccessToken());

        return baseReq;
    }


    /**
     *  <p>生成一个返回对象</p>
     *  @param
     *  @return
     *  @author fuyongchao
     *  @since 2022/1/9 17:33
     */
    public static BaseResponse productBaseRsp(String bizId, String kindId, BaseRequest baseReq){
        BaseResponse baseRsp = new BaseResponse(Code.SUCCESS);
        baseRsp.setTransIDH(bizId);
        if (baseReq != null){
            baseRsp.setDomain(baseReq.getDomain());
            baseRsp.setTransIDO(baseReq.getTransIDO());
        }
        return baseRsp;
    }

    /**
     *  <p>获取处理业务类</p>
     *  @param
     *  @return
     *  @author fuyongchao
     *  @since 2022/1/9 18:04
     */
    public static BaseSupperAdaptor getAdaptor(String bizId, String kindId, BaseRequest baseReq, BaseResponse baseRsp, SysApiCfgManager sysApiCfgManager, ApplicationContext applicationContext){
        String apiVersion = baseReq.getApiVersion();
        //1）获取处理类
        SysApiCfgDo sysApiCfg = sysApiCfgManager.getSysApiCfg(kindId, apiVersion);
        if (sysApiCfg == null){
            Map<String,String> elMap=new HashMap<>();
            elMap.put("kindId",kindId);
            elMap.put("apiVersion",apiVersion);
            log.warn("根据kindId={kindId},apiVersion={apiVersion}未获取到有效的接口配置，请联系系统管理员",kindId,apiVersion);
            throw new BusinessException(Code.SYSTEM_API_NOT_FIND,elMap);
        }
        String apiAdaptroClass=sysApiCfg.getApiAdaptorClass();
        log.debug("处理类路径：{}",apiAdaptroClass);
        BaseSupperAdaptor adaptor = SpringUtils.getBeanByClassFullName(apiAdaptroClass, applicationContext);
        if (adaptor == null){
            Map<String,String> elMap=new HashMap<>();
            elMap.put("apiAdaptorClass",apiAdaptroClass);
            log.warn("根据类路径[${apiAdaptorClass}]未获取到有效的接口处理对象，请联系系统管理员",apiAdaptroClass);
            throw new BusinessException(Code.SYSTEM_ADAPTOR_NOT_FIND,elMap);
        }
        return adaptor;
    }

    /**
     *  <p>解析请求IP</p>
     *  @param
     *  @return
     *  @author fuyongchao
     *  @since 2022/1/14 14:52
     */
    public static String paresReqAccessIp(HttpServletRequest request){
        String accessIp=request.getHeader("SFCZ_ACCESS_IP");
        if (StringUtils.isBlank(accessIp)){
            accessIp=request.getHeader("X-Real-IP");
            if (StringUtils.isBlank(accessIp)){
                accessIp=request.getRemoteAddr();
            }
        }
        log.info("请求IP:{}",accessIp);
        return accessIp;
    }

    /**
     *  <p>解析入参对象</p>
     *  @param
     *  @return
     *  @author fuyongchao
     *  @since 2022/1/9 18:18
     */
    public IN parseINObject(String bizId, SysApiCfgDo sysApiCfg, BaseRequest baseReq, BaseResponse baseRspz, HttpServletRequest request, HttpServletResponse response){
        JSONObject data = baseReq.getData();
        if (data == null){
            return null;
        }
        String reqModelClass=sysApiCfg.getReqModelClass();
        try {
            Object o = JSON.toJavaObject(data, Class.forName(reqModelClass));
            return (IN) o;
        }catch (ClassCastException e){
            log.warn("类型强制转换错误：{}",reqModelClass);
            throw new BusinessException("10003");
        }catch (ClassNotFoundException e) {
            Map<String,String> elMap=new HashMap<>();
            elMap.put("reqModelClass",reqModelClass);
            log.warn("根据类路径[${reqModelClass}]未获取到有效的Class信息，请联系系统管理员",reqModelClass);
            throw new BusinessException("90003",elMap);
        }
    }


    public abstract OUT handlerBiz(String bizId, SysApiCfgDo sysApiCf, IN reqData, BaseRequest baseReq, BaseResponse baseRsp, HttpServletRequest request, HttpServletResponse response);

    public void validateRspData(String bizId, SysApiCfgDo sysApiCf, IN reqData, OUT rspData, BaseRequest baseReq, BaseResponse baseRsp, HttpServletRequest request, HttpServletResponse response){
        return;
    }

    /**
     *  <p>入参校验</p>
     *  @param
     *  @return
     *  @author fuyongchao
     *  @since 2022/1/12 19:56
     */
    public void validateReqData(String bizId, SysApiCfgDo sysApiCfg, IN reqData, BaseRequest baseReq, BaseResponse baseRsp, HttpServletRequest request, HttpServletResponse response){
        Set<ConstraintViolation<IN>> validate =
                validatorFactory
                        .getValidator()
                        .validate(reqData);
        if (ObjectUtils.isNotEmpty(validate)){
            log.error("入参校验失败：{}",validate.iterator().next().getMessage());
            String msg = validate.iterator().next().getMessage();
            Map<String,String> elMap=new HashMap<>();
            elMap.put("msg",msg);
            throw new BusinessException(Code.PARAM_VERIFY_ERROR,elMap);
        }
    }

    public BaseResponse putRspData2BaseRsp(String domain, String bizId, SysApiCfgDo sysApiCf, BaseRequest baseReq, BaseResponse baseRsp, IN reqData, OUT rspData, JSONObject extData, HttpServletRequest request, HttpServletResponse response){
        baseRsp.setDomain(domain);
        baseRsp.setTransIDH(bizId);
        baseRsp.setTransIDO(baseReq.getTransIDO());
        if (rspData != null){
            baseRsp.setData(rspData);
        }
        if (extData !=null){
            baseRsp.setRspExt(extData);
        }
        return baseRsp;
    }

    public JSONObject handlerBizExt(String bizId, SysApiCfgDo sysApiCfg, BaseRequest baseReq, IN reqData, BaseResponse baseRsp, OUT rspData, HttpServletRequest request, HttpServletResponse response){
        return null;
    }

    /**
     *  <p>获取接口的配置信息</p>
     *  @param
     *  @return
     *  @author fuyongchao
     *  @since 2022/1/10 14:09
     */
    public SysApiCfgDo getApiCfg(String bizId, String kindId, BaseRequest baseReq, BaseResponse baseRsp, SysApiCfgManager sysApiCfgManager){
        SysApiCfgDo sysApiCfg = sysApiCfgManager.getSysApiCfg(kindId, baseReq.getApiVersion());
        if (sysApiCfg == null){
            //根据kindId=${kindId},apiVersion=${apiVersion}未获取到有效的接口配置，请联系系统管理员
            Map<String,String> elMap=new HashMap<>();
            elMap.put("kindId",kindId);
            elMap.put("apiVersion",baseReq.getApiVersion());
            throw new BusinessException(Code.SYSTEM_API_NOT_FIND);
        }
        return sysApiCfg;
    }

    /**
     *  <p>黑白名单校验</p>
     *  @param
     *  @return
     *  @author fuyongchao
     *  @since 2022/1/14 14:09
     */
    public void validateBlackAndWhiteIp(String bizId, SysApiCfgDo sysApiCfg, BaseRequest baseReq, BaseResponse baseRsp, HttpServletRequest request, HttpServletResponse response, String accessIp){
        //黑名单校验
        String ipBlackExps = sysApiCfg.getIpBlackExps();
        if (StringUtils.startsWithIgnoreCase(ipBlackExps,"denyAll")){
            log.debug("禁止接入系统，请使用被允许的系统访问");
            throw new BusinessException("11001");
        }
        String[] blackIps = StringUtils.split(ipBlackExps, ";");
        if (blackIps!=null && blackIps.length >0){
            for (String blackIpExp:blackIps){
                if (ReUtil.isMatch(blackIpExp, accessIp)){
                    throw new BusinessException("11001");
                }
            }
        }

        //白名单校验
        String ipWhiteExps = sysApiCfg.getIpWhiteExps();
        if (!StringUtils.startsWithIgnoreCase(ipWhiteExps,"permitAll")){
            //不是permitAll就需要对ip校验，如果所有校验完都不在内，抛出异常
            String[] whiteIps = StringUtils.split(ipWhiteExps, ";");
            if (whiteIps!=null && whiteIps.length >0){
                for (String whiteExp:whiteIps){
                    if (ReUtil.isMatch(whiteExp, accessIp)){
                        return;
                    }
                }
                throw new BusinessException("11001");
            }
        }
    }

    /**
     *  <p>请求签名校验</p>
     *  @param
     *  @return
     *  @author fuyongchao
     *  @since 2022/1/14 14:09
     */
    public void validateReqSign(String bizId, SysApiCfgDo sysApiCfg, BaseRequest baseReq, BaseResponse baseRsp, HttpServletRequest request, HttpServletResponse response){

    }

    /**
     *  <p>接入token校验</p>
     *  @param
     *  @return
     *  @author fuyongchao
     *  @since 2022/1/14 14:10
     */
    public void validateAccessToken(String bizId, SysApiCfgDo sysApiCfg, BaseRequest baseReq, BaseResponse baseRsp, HttpServletRequest request, HttpServletResponse response){
        //1:需要校验 0：不需要校验
        String checkTag = sysApiCfg.getAccessTokenCheckTag();
        if (StringUtils.equals("1",checkTag)){
            String accessToken = baseReq.getAccessToken();
            if (StringUtils.isBlank(accessToken)){
                throw new BusinessException(Code.AUTH_TOKEN_VERIFY_ERROR);
            }
            BaseTokenModel tokenInfo = tokenManager.getTokenModelByToken(accessToken);
            if (tokenInfo == null){
                throw new BusinessException(Code.AUTH_TOKEN_VERIFY_ERROR);
            }
        }
    }

    /**
     *  <p>资源权限校验</p>
     *  @param
     *  @return
     *  @author fuyongchao
     *  @since 2022/1/14 14:11
     */
    public void validateResourceAuth(String bizId, SysApiCfgDo sysApiCfg, BaseRequest baseReq, BaseResponse baseRsp, HttpServletRequest request, HttpServletResponse response){
        //只有有用户登录才会有权限的说法，所以必须先确保有用户概念
        String tokenCheckTag = sysApiCfg.getAccessTokenCheckTag();
        if (StringUtils.equals("1",tokenCheckTag)){
            BaseTokenModel tokenMO = tokenManager.getTokenModelByToken(baseReq.getAccessToken());
            if (tokenMO == null){
                throw new BusinessException(Code.AUTH_TOKEN_VERIFY_ERROR);
            }
            String userAuthList = tokenMO.getAuthList();

            //权限黑名单
            String authBlackList = sysApiCfg.getAuthBlackList();
            if (StringUtils.startsWithIgnoreCase(authBlackList,"denyAll")){
                throw new BusinessException(Code.AUTH_ACCESS_NOT_ALLOWED);
            }
            //用户没有指定权限这里应该也是放过的
            String[] blackAuths = StringUtils.split(authBlackList, ";");
            if (blackAuths !=null && blackAuths.length >0){
                for (String blackAuth:blackAuths){
                    if (StringUtils.contains(userAuthList,blackAuth)){
                        throw new BusinessException(Code.AUTH_ACCESS_NOT_ALLOWED);
                    }
                }
            }

            //权限白名单
            String authWhiteList = sysApiCfg.getAuthWhiteList();
            if (!StringUtils.startsWithIgnoreCase(authWhiteList,"permitAll")){
                //不是permitAll就需要对ip校验，如果所有校验完都不在内，抛出异常
                String[] whiteAuths = StringUtils.split(authWhiteList, ";");
                if (whiteAuths!=null && whiteAuths.length >0){
                    for (String whiteAuth:whiteAuths){
                        if (StringUtils.contains(userAuthList,whiteAuth)){
                            return;
                        }
                    }
                    throw new BusinessException(Code.AUTH_ACCESS_NOT_ALLOWED);
                }

            }

        }



    }



}
