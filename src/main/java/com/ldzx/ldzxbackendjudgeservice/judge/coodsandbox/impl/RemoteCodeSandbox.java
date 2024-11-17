package com.ldzx.ldzxbackendjudgeservice.judge.coodsandbox.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.excel.util.StringUtils;
import com.ldzx.ldzxbackendjudgeservice.judge.coodsandbox.CodeSandbox;
import com.ldzx.ldzxojbackendcommon.common.ErrorCode;
import com.ldzx.ldzxojbackendcommon.exception.BusinessException;
import com.ldzx.ldzxojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import com.ldzx.ldzxojbackendmodel.model.codesandbox.ExecuteCodeResponse;

/**
 * 远程代码沙箱（实际调用沙箱的接口）
 */

public class RemoteCodeSandbox implements CodeSandbox {
    // 定义鉴权请求头和密钥
    private static final String AUTH_REQUEST_HEADER = "auth";

    private static final String AUTH_REQUEST_SECRET = "secretKey";



    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("远程代码沙箱");

        String url = "http://localhost:8090/executeCode";
        String json = JSONUtil.toJsonStr(executeCodeRequest);
        String responseStr = HttpUtil.createPost(url)
                .header(AUTH_REQUEST_HEADER, AUTH_REQUEST_SECRET)
                .body(json)
                .execute()
                .body();
        //判断的条件有问题，即使没有成功调用代码沙箱也不会报错
        if(StringUtils.isBlank(responseStr)){
            throw new BusinessException(ErrorCode.API_REQUEST_ERROR, "executeCode remoteSandbox error, message = " + responseStr);
        }
        return JSONUtil.toBean(responseStr,ExecuteCodeResponse.class);
    }
}
