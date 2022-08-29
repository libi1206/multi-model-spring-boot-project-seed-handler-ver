-- nft_store.sys_api_cfg definition

CREATE TABLE `sys_api_cfg` (
  `id` int(8) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `kind_id` varchar(128) NOT NULL COMMENT '接口定义：业务_接口动作；forensic_uploadFile2BlockChain:网页取证_上传文件到区块链',
  `api_version` varchar(8) NOT NULL COMMENT '接口版本号',
  `api_desc` varchar(256) NOT NULL COMMENT '接口描述',
  `api_adaptor_class` varchar(256) NOT NULL COMMENT '接口业务处理类全路径',
  `req_model_class` varchar(256) NOT NULL COMMENT '请求参数对象类型，如果无参数，填java.lang.Object',
  `rsp_model_class` varchar(256) NOT NULL COMMENT '应答参数类型，如果无参数，填java.lang.Object',
  `remove_tag` char(1) DEFAULT '1' COMMENT '移除标识：1、在用；0、移除',
  `insert_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '插入时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `operator_name` varchar(32) NOT NULL COMMENT '操作人姓名',
  `sign_check_tag` char(1) NOT NULL DEFAULT '1' COMMENT '校验签名标识；1、校验；0、不校验',
  `access_token_check_tag` char(1) NOT NULL DEFAULT '1' COMMENT '校验accessToken标识；1、校验；0、不校验',
  `auth_white_list` varchar(512) NOT NULL DEFAULT 'permitAll' COMMENT '权限标识白名单；可以是permitAll、角色标识、也可以是权限标识（中间分号分割）',
  `auth_black_list` varchar(512) DEFAULT NULL COMMENT '权限标识黑名单；可以是denyAll、角色标识、也可以是权限标识（中间分号分割）、如果为空表示不校验黑名单',
  `ip_white_exps` varchar(512) NOT NULL DEFAULT 'permitAll' COMMENT 'IP白名单表达式；可以是permitAll、可以是满足ip的正则表达式（中间分号分割）',
  `ip_black_exps` varchar(512) DEFAULT NULL COMMENT 'Ip黑名单表达式；可以是denyAll、可以是满足ip的正则表达式（中间分号分割）',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sys_api_cfg_UN` (`kind_id`,`api_version`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='接口配置表';