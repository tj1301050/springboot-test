package com.hdedu.enums;

public enum ResponseEnum {


    SUCCEED("200","成功"),
    FAIL("500","成功");
    private String code;

    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    ResponseEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
