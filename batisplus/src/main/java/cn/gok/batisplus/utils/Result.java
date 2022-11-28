package cn.gok.batisplus.utils;

import lombok.Data;

@Data
public class Result {
//    成功的标识
    private static final int SUCCESS_CODE=1;
//    失败的标识
    private static final int FAILED_CODE=0;
//    状态码
    private int code;
//    信息
    private String msg;
//    数据
    private Object data;

/*
* 成功的方法:成功的标识，成功的信息，数据 返回
* 返回值：result对象
* */
    public static Result success(String msg, Object data){
        Result result= new Result();
        result.setCode(Result.SUCCESS_CODE);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

/*
     * 失败的方法:失败的标识，失败的信息
     * 返回值：result对象
     * */
    public static Result fail(String msg){
        Result result= new Result();
        result.setCode(Result.FAILED_CODE);
        result.setMsg(msg);
        return result;
    }

}
