package com.ldzxoj.ldzxoj.judge.coodsandbox;

import com.ldzxoj.ldzxoj.judge.coodsandbox.model.ExecuteCodeRequest;
import com.ldzxoj.ldzxoj.judge.coodsandbox.model.ExecuteCodeResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 需求：在日志中加入代码沙箱的输入和输出用例
 * 解决：频繁的使用@Slfj4过于繁琐，使用代理模式解决
 */
@Slf4j
@AllArgsConstructor
public class CodeSandboxPoxy implements CodeSandbox{

    private final CodeSandbox codeSandbox;

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        log.info("代码沙箱请求信息：" + executeCodeRequest.toString());
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
        log.info("代码沙箱响应信息" + executeCodeResponse.toString());
        return executeCodeResponse;
    }
}
