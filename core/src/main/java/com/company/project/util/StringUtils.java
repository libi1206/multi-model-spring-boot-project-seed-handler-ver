package com.company.project.util;

import java.util.Map;

public class StringUtils {

    /**
     *  <p>替换EL表达式,${AAA},${BBB}...</p>
     *  @Param
     *  @return
     *  @Author: fuyongchao
     *  @Date: 2021/8/10 13:49
     */
    public static String replaceReg(String source, Map<String,String> ELReplaceMap){

        if (org.apache.commons.lang3.StringUtils.isBlank(source) || ELReplaceMap==null || ELReplaceMap.size() ==0){
            return source;
        }
        for (String key:ELReplaceMap.keySet()){
            source= org.apache.commons.lang3.StringUtils.replace(source,"${"+key+"}",ELReplaceMap.get(key));
        }
        return source;
    }
}
