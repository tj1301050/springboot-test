package com.hdedu.exception;

import lombok.Data;

/**
 * @author EDZ
 * @className BizException
 * @description TODO
 * @date 2021/10/12 9:53
 */
@Data
public class BizException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    protected String errorCode;
    protected String errorMessage;

    public BizException(){
        super();
    }

    public BizException(BaseErrorInfoInterface errorInfoInterface) {
        super(errorInfoInterface.getResultCode());
        this.errorCode = errorInfoInterface.getResultCode();
        this.errorMessage = errorInfoInterface.getResultMessage();
    }

    public BizException(BaseErrorInfoInterface errorInfoInterface, Throwable cause) {
        super(errorInfoInterface.getResultCode(), cause);
        this.errorCode = errorInfoInterface.getResultCode();
        this.errorMessage = errorInfoInterface.getResultMessage();
    }

    public BizException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public BizException(String errorCode, String errorMessage) {
        super(errorCode);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public BizException(String errorCode, String errorMessage, Throwable cause) {
        super(errorCode, cause);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}