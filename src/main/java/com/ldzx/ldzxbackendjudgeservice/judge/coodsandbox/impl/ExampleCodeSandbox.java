package com.ldzx.ldzxbackendjudgeservice.judge.coodsandbox.impl;


import com.ldzx.ldzxbackendjudgeservice.judge.coodsandbox.CodeSandbox;
import com.ldzx.ldzxojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import com.ldzx.ldzxojbackendmodel.model.codesandbox.ExecuteCodeResponse;
import com.ldzx.ldzxojbackendmodel.model.codesandbox.JudgeInfo;
import com.ldzx.ldzxojbackendmodel.model.enums.JudgeInfoMessageEnum;
import com.ldzx.ldzxojbackendmodel.model.enums.QuestionSubmitStatusEnum;

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
