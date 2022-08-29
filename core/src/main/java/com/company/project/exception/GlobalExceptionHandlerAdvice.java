package com.company.project.exception;


import com.company.project.constent.Code;
import com.company.project.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 *
 * @author superlee
 * @date 2017/11/6
 * 全局异常处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandlerAdvice {

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public BaseResponse handleBusinessException(BusinessException be) {
        log.error(be.getMessage());
        be.printStackTrace();
        return new BaseResponse(be.getCode(), be.getMsg());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public BaseResponse handleDefaultException(Exception be) {
        log.error(be.getMessage());
        be.printStackTrace();
        return new BaseResponse(Code.UNKNOWN_ABNORMAL);
    }


}
