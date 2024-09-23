package com.ldzxoj.ldzxoj.judge.strategy;

import com.ldzxoj.ldzxoj.model.dto.question.JudgeCase;
import com.ldzxoj.ldzxoj.model.dto.questionsubmit.JudgeInfo;
import com.ldzxoj.ldzxoj.model.entity.Question;
import com.ldzxoj.ldzxoj.model.entity.QuestionSubmit;
import lombok.Data;

import java.util.List;

/**
 * 上下文，用于定义在策略中传递的参数
 */
@Data
public class JudgeContext {

    private JudgeInfo judgeInfo;

    private List<String> inputList;

    private List<String> outputList;

    private Question question;

    private List<JudgeCase> judgeCaseList;

    private QuestionSubmit questionSubmit;

}
