package com.hdedu.base;

import com.hdedu.enums.ResponseEnum;
import lombok.Data;

import javax.xml.transform.Result;

@Data
public class ResultVO<T> {

    private String code;

    private String message;

    private T data;

    public ResultVO() {
    }

    public ResultVO(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static <T> ResultVO<T> success(){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(ResponseEnum.SUCCEED.getCode());
        return resultVO;
    }

    public static <T> ResultVO<T> success(String message){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(ResponseEnum.SUCCEED.getCode());
        resultVO.setMessage(message);
        return resultVO;
    }

    public static <T> ResultVO<T> success(T data){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(ResponseEnum.SUCCEED.getCode());
        resultVO.setMessage(ResponseEnum.SUCCEED.getMessage());
        resultVO.setData(data);
        return resultVO;
    }

    public static <T> ResultVO<T> fail(String message){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(ResponseEnum.FAIL.getCode());
        resultVO.setMessage(message);
        return resultVO;
    }
    public static <T> ResultVO<T> fail(String code,String message,T data){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMessage(message);
        resultVO.setData(data);
        return resultVO;
    }

    public static <T> ResultVO<T>fail(String code,String message){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMessage(message);
        return resultVO;
    }

}
