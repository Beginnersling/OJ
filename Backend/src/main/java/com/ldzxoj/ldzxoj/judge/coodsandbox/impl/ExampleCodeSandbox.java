package com.ldzxoj.ldzxoj.judge.coodsandbox.impl;

import com.ldzxoj.ldzxoj.judge.coodsandbox.CodeSandbox;
import com.ldzxoj.ldzxoj.judge.coodsandbox.model.ExecuteCodeRequest;
import com.ldzxoj.ldzxoj.judge.coodsandbox.model.ExecuteCodeResponse;
import com.ldzxoj.ldzxoj.model.dto.questionsubmit.JudgeInfo;
import com.ldzxoj.ldzxoj.model.enums.JudgeInfoMessageEnum;
import com.ldzxoj.ldzxoj.model.enums.QuestionSubmitStatusEnum;

import java.util.List;

/**
 * 示例代码沙箱
 */

public class ExampleCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        List<String> inputList = executeCodeRequest.getInputList();
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        executeCodeResponse.setOutputList(inputList);
        executeCodeResponse.setMessage("测试执行成功");
        executeCodeResponse.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setMessage(JudgeInfoMessageEnum.ACCEPTED.getText());
        judgeInfo.setMemory(100L);
        judgeInfo.setTime(100L);
        executeCodeResponse.setJudgeInfo(judgeInfo);
        return executeCodeResponse;

    }
}
