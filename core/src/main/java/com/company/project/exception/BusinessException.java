package com.company.project.exception;


import com.company.project.constent.Code;
import lombok.Getter;

import java.util.Map;

/**
 * 业务异常
 */
@Getter
public final class BusinessException extends RuntimeException {
    private String code;
    private String msg;

    public BusinessException() {
        super();
    }

    public BusinessException(Code code) {
        super(code.getMsg());
        this.code = code.getCode();
        this.msg = code.getMsg();
    }

    public BusinessException(String code) {
        super(code);
        Code codeObj = Code.getCodeByCodeStr(code);
        this.code = codeObj.getCode();
        this.msg = codeObj.getMsg();
    }

    public BusinessException(String code, String msg) {
        super(code);
        this.code = code;
        this.msg = msg;
    }

    public BusinessException(String code, Map<String, String> elMap) {
        super(code);
        Code codeEnum = Code.getCodeByCodeStr(code);
        this.code = code;
        this.msg = codeEnum.getMsg(elMap);
    }

    public BusinessException(Code code, Map<String, String> elMap) {
        super(code.getMsg());
        this.code = code.getCode();
        this.msg = code.getMsg(elMap);
    }
}
