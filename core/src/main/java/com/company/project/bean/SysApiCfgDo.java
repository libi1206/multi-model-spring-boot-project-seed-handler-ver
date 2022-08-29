package com.company.project.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 接口配置表
 * 
 * @author libi
 * @date 2022-04-08 16:08:15
 */
@TableName("sys_api_cfg")
public class SysApiCfgDo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	/**
	 * 接口定义：业务_接口动作；forensic_uploadFile2BlockChain:网页取证_上传文件到区块链
	 */
	private String kindId;
	/**
	 * 接口版本号
	 */
	private String apiVersion;
	/**
	 * 接口描述
	 */
	private String apiDesc;
	/**
	 * 接口业务处理类全路径
	 */
	private String apiAdaptorClass;
	/**
	 * 请求参数对象类型，如果无参数，填java.lang.Object
	 */
	private String reqModelClass;
	/**
	 * 应答参数类型，如果无参数，填java.lang.Object
	 */
	private String rspModelClass;
	/**
	 * 移除标识：1、在用；0、移除
	 */
	@TableLogic
	private String removeTag;
	/**
	 * 插入时间
	 */
	private Date insertTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 操作人姓名
	 */
	private String operatorName;
	/**
	 * 校验签名标识；1、校验；0、不校验
	 */
	private String signCheckTag;
	/**
	 * 校验accessToken标识；1、校验；0、不校验
	 */
	private String accessTokenCheckTag;
	/**
	 * 权限标识白名单；可以是permitAll、角色标识、也可以是权限标识（中间分号分割）
	 */
	private String authWhiteList;
	/**
	 * 权限标识黑名单；可以是denyAll、角色标识、也可以是权限标识（中间分号分割）、如果为空表示不校验黑名单
	 */
	private String authBlackList;
	/**
	 * IP白名单表达式；可以是permitAll、可以是满足ip的正则表达式（中间分号分割）
	 */
	private String ipWhiteExps;
	/**
	 * Ip黑名单表达式；可以是denyAll、可以是满足ip的正则表达式（中间分号分割）
	 */
	private String ipBlackExps;

	/**
	 * 设置：id
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：接口定义：业务_接口动作；forensic_uploadFile2BlockChain:网页取证_上传文件到区块链
	 */
	public void setKindId(String kindId) {
		this.kindId = kindId;
	}
	/**
	 * 获取：接口定义：业务_接口动作；forensic_uploadFile2BlockChain:网页取证_上传文件到区块链
	 */
	public String getKindId() {
		return kindId;
	}
	/**
	 * 设置：接口版本号
	 */
	public void setApiVersion(String apiVersion) {
		this.apiVersion = apiVersion;
	}
	/**
	 * 获取：接口版本号
	 */
	public String getApiVersion() {
		return apiVersion;
	}
	/**
	 * 设置：接口描述
	 */
	public void setApiDesc(String apiDesc) {
		this.apiDesc = apiDesc;
	}
	/**
	 * 获取：接口描述
	 */
	public String getApiDesc() {
		return apiDesc;
	}
	/**
	 * 设置：接口业务处理类全路径
	 */
	public void setApiAdaptorClass(String apiAdaptorClass) {
		this.apiAdaptorClass = apiAdaptorClass;
	}
	/**
	 * 获取：接口业务处理类全路径
	 */
	public String getApiAdaptorClass() {
		return apiAdaptorClass;
	}
	/**
	 * 设置：请求参数对象类型，如果无参数，填java.lang.Object
	 */
	public void setReqModelClass(String reqModelClass) {
		this.reqModelClass = reqModelClass;
	}
	/**
	 * 获取：请求参数对象类型，如果无参数，填java.lang.Object
	 */
	public String getReqModelClass() {
		return reqModelClass;
	}
	/**
	 * 设置：应答参数类型，如果无参数，填java.lang.Object
	 */
	public void setRspModelClass(String rspModelClass) {
		this.rspModelClass = rspModelClass;
	}
	/**
	 * 获取：应答参数类型，如果无参数，填java.lang.Object
	 */
	public String getRspModelClass() {
		return rspModelClass;
	}
	/**
	 * 设置：移除标识：1、在用；0、移除
	 */
	public void setRemoveTag(String removeTag) {
		this.removeTag = removeTag;
	}
	/**
	 * 获取：移除标识：1、在用；0、移除
	 */
	public String getRemoveTag() {
		return removeTag;
	}
	/**
	 * 设置：插入时间
	 */
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	/**
	 * 获取：插入时间
	 */
	public Date getInsertTime() {
		return insertTime;
	}
	/**
	 * 设置：更新时间
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取：更新时间
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
	/**
	 * 设置：操作人姓名
	 */
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	/**
	 * 获取：操作人姓名
	 */
	public String getOperatorName() {
		return operatorName;
	}
	/**
	 * 设置：校验签名标识；1、校验；0、不校验
	 */
	public void setSignCheckTag(String signCheckTag) {
		this.signCheckTag = signCheckTag;
	}
	/**
	 * 获取：校验签名标识；1、校验；0、不校验
	 */
	public String getSignCheckTag() {
		return signCheckTag;
	}
	/**
	 * 设置：校验accessToken标识；1、校验；0、不校验
	 */
	public void setAccessTokenCheckTag(String accessTokenCheckTag) {
		this.accessTokenCheckTag = accessTokenCheckTag;
	}
	/**
	 * 获取：校验accessToken标识；1、校验；0、不校验
	 */
	public String getAccessTokenCheckTag() {
		return accessTokenCheckTag;
	}
	/**
	 * 设置：权限标识白名单；可以是permitAll、角色标识、也可以是权限标识（中间分号分割）
	 */
	public void setAuthWhiteList(String authWhiteList) {
		this.authWhiteList = authWhiteList;
	}
	/**
	 * 获取：权限标识白名单；可以是permitAll、角色标识、也可以是权限标识（中间分号分割）
	 */
	public String getAuthWhiteList() {
		return authWhiteList;
	}
	/**
	 * 设置：权限标识黑名单；可以是denyAll、角色标识、也可以是权限标识（中间分号分割）、如果为空表示不校验黑名单
	 */
	public void setAuthBlackList(String authBlackList) {
		this.authBlackList = authBlackList;
	}
	/**
	 * 获取：权限标识黑名单；可以是denyAll、角色标识、也可以是权限标识（中间分号分割）、如果为空表示不校验黑名单
	 */
	public String getAuthBlackList() {
		return authBlackList;
	}
	/**
	 * 设置：IP白名单表达式；可以是permitAll、可以是满足ip的正则表达式（中间分号分割）
	 */
	public void setIpWhiteExps(String ipWhiteExps) {
		this.ipWhiteExps = ipWhiteExps;
	}
	/**
	 * 获取：IP白名单表达式；可以是permitAll、可以是满足ip的正则表达式（中间分号分割）
	 */
	public String getIpWhiteExps() {
		return ipWhiteExps;
	}
	/**
	 * 设置：Ip黑名单表达式；可以是denyAll、可以是满足ip的正则表达式（中间分号分割）
	 */
	public void setIpBlackExps(String ipBlackExps) {
		this.ipBlackExps = ipBlackExps;
	}
	/**
	 * 获取：Ip黑名单表达式；可以是denyAll、可以是满足ip的正则表达式（中间分号分割）
	 */
	public String getIpBlackExps() {
		return ipBlackExps;
	}
}
