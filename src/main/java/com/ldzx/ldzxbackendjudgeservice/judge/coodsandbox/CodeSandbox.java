package com.ldzx.ldzxbackendjudgeservice.judge.coodsandbox;


import com.ldzx.ldzxojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import com.ldzx.ldzxojbackendmodel.model.codesandbox.ExecuteCodeResponse;

/**
 * 代码沙箱接口定义
 */

public interface CodeSandbox {
    /**
     * 执行代码
     * @param executeCodeRequest
     * @return
     */
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}
