package com.ldzx.ldzxbackendjudgeservice.judge.strategy;
import com.ldzx.ldzxojbackendmodel.model.codesandbox.JudgeInfo;
import com.ldzx.ldzxojbackendmodel.model.dto.question.JudgeCase;
import com.ldzx.ldzxojbackendmodel.model.entity.Question;
import com.ldzx.ldzxojbackendmodel.model.entity.QuestionSubmit;
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
