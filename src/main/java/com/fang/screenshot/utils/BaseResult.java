package com.fang.screenshot.utils;

public class BaseResult<T> {

    public static final Integer SUCCESS_CODE=1;
    Integer code;
    String msg;
    T data;

    public BaseResult() {
    }

    public BaseResult(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static BaseResult success(){
        BaseResult baseResult = new BaseResult();
        baseResult.setMsg("成功");
        baseResult.setCode(1);
        return baseResult;
    }
    public static BaseResult success(String msg){
        BaseResult baseResult = new BaseResult();
        baseResult.setMsg(msg);
        baseResult.setCode(1);
        return baseResult;
    }
    public static BaseResult success(Object data){
        BaseResult baseResult = new BaseResult();
        baseResult.setMsg("成功");
        baseResult.setCode(1);
        baseResult.setData(data);
        return baseResult;
    }
    public static BaseResult success(String msg,Object data){
        BaseResult baseResult = new BaseResult();
        baseResult.setMsg(msg);
        baseResult.setCode(1);
        baseResult.setData(data);
        return baseResult;
    }
    public static BaseResult error(){
        BaseResult baseResult = new BaseResult();
        baseResult.setMsg("失败");
        baseResult.setCode(0);
        return baseResult;
    }
    public static BaseResult error(String errmsg){
        BaseResult baseResult = new BaseResult();
        baseResult.setMsg(errmsg);
        baseResult.setCode(0);
        return baseResult;
    }

    public static BaseResult error(String errmsg,Object data){
        BaseResult baseResult = new BaseResult();
        baseResult.setData(data);
        baseResult.setMsg(errmsg);
        baseResult.setCode(0);
        return baseResult;
    }
}
