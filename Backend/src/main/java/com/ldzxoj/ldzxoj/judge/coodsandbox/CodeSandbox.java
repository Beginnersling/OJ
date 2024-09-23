package com.ldzxoj.ldzxoj.judge.coodsandbox;

import com.ldzxoj.ldzxoj.judge.coodsandbox.model.ExecuteCodeRequest;
import com.ldzxoj.ldzxoj.judge.coodsandbox.model.ExecuteCodeResponse;

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
