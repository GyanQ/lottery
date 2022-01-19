package com.vietnam.lottery.common.utils;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;

/**
 * @author Gyan
 * @since 2021/11/8
 */
public class ResultUtil {
    /**
     * 返回成功，不带数据
     *
     * @return ResultModel<T>
     */
    public static <T> ResultModel<T> success() {
        ResultModel<T> resultModel = new ResultModel<>();
        resultModel.setCode(Integer.parseInt(ResultEnum.SUCCESS.getKey()));
        resultModel.setMsg(ResultEnum.SUCCESS.getDesc());
        return resultModel;
    }

    /**
     * 返回成功，带数据
     *
     * @param obj 响应数据
     * @return ResultModel<T>
     */
    public static <T> ResultModel<T> success(T obj) {
        ResultModel<T> resultModel = new ResultModel<>();
        resultModel.setCode(Integer.parseInt(ResultEnum.SUCCESS.getKey()));
        resultModel.setMsg(ResultEnum.SUCCESS.getDesc());
        resultModel.setData(obj);
        return resultModel;
    }

    /**
     * 返回自定义错误信息
     *
     * @param msg 响应信息
     * @return ResultModel<T>
     */
    public static <T> ResultModel<T> failure(String msg) {
        ResultModel<T> resultModel = new ResultModel<>();
        resultModel.setCode(Integer.parseInt(ResultEnum.FAILURE.getKey()));
        resultModel.setMsg(msg);
        return resultModel;
    }

    /**
     * 返回自定义错误编码及信息
     *
     * @param code 响应码
     * @param msg  响应信息
     * @return ResultModel<T>
     */
    public static <T> ResultModel<T> failure(int code, String msg) {
        ResultModel<T> resultModel = new ResultModel<>();
        resultModel.setCode(code);
        resultModel.setMsg(msg);
        return resultModel;
    }

    /**
     * 统一校验接口入参
     *
     * @param bindingResult 实体类校验信息结果绑定
     * @return ResultModel<T>
     */
    public static <T> ResultModel<T> validFailure(BindingResult bindingResult) {
        ResultModel<T> resultModel = new ResultModel<>();
        resultModel.setCode(Integer.parseInt(ResultEnum.FAILURE.getKey()));
        StringBuilder stringBuilder = new StringBuilder();
        List<ObjectError> list = bindingResult.getAllErrors();
        for (ObjectError objectError : list) {
            stringBuilder.append(objectError.getDefaultMessage());
            stringBuilder.append("，");
        }
        String errorMsg = stringBuilder.toString();
        errorMsg = errorMsg.substring(0, errorMsg.length() - 1);
        resultModel.setMsg(errorMsg);
        return resultModel;
    }

}