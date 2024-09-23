package com.ldzxoj.ldzxoj.judge;

import cn.hutool.json.JSONUtil;
import com.ldzxoj.ldzxoj.common.ErrorCode;
import com.ldzxoj.ldzxoj.exception.BusinessException;
import com.ldzxoj.ldzxoj.judge.coodsandbox.CodeSandbox;
import com.ldzxoj.ldzxoj.judge.coodsandbox.CodeSandboxFactory;
import com.ldzxoj.ldzxoj.judge.coodsandbox.CodeSandboxPoxy;
import com.ldzxoj.ldzxoj.judge.coodsandbox.model.ExecuteCodeRequest;
import com.ldzxoj.ldzxoj.judge.coodsandbox.model.ExecuteCodeResponse;
import com.ldzxoj.ldzxoj.judge.strategy.DefaultJudgeStrategy;
import com.ldzxoj.ldzxoj.judge.strategy.JudgeContext;
import com.ldzxoj.ldzxoj.judge.strategy.JudgeStrategy;
import com.ldzxoj.ldzxoj.model.dto.question.JudgeCase;
import com.ldzxoj.ldzxoj.model.dto.questionsubmit.JudgeInfo;
import com.ldzxoj.ldzxoj.model.entity.Question;
import com.ldzxoj.ldzxoj.model.entity.QuestionSubmit;
import com.ldzxoj.ldzxoj.model.enums.QuestionSubmitStatusEnum;
import com.ldzxoj.ldzxoj.model.vo.QuestionSubmitVO;
import com.ldzxoj.ldzxoj.service.QuestionService;
import com.ldzxoj.ldzxoj.service.QuestionSubmitService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JudgeServiceImpl implements JudgeService {
    @Resource
    private QuestionService questionService;

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private JudgeManager judgeManager;


    @Value("${codesandbox.type:example}")
    private String type;
    @Override
    public QuestionSubmit doJudge(long questionSubmitId) {
        //1.传入题目id，获取对应的题目`提交信息
        QuestionSubmit questionSubmit = questionSubmitService.getById(questionSubmitId);

        if (questionSubmit == null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"提交信息不存在");
        }
        Long questionId = questionSubmit.getQuestionId();
        Question question = questionService.getById(questionId);
        if(question == null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"题目不存在");
        }
        //2.判断题目的状态，防止多次判题节省系统资源
        //是否不为等待状态
        if(!questionSubmit.getStatus().equals(QuestionSubmitStatusEnum.WATTING.getValue())){
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"题目正在判题中");
        }
        //修改状态
        QuestionSubmit questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        boolean update = questionSubmitService.updateById(questionSubmitUpdate);
        if(!update){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"题目状态更新错误");
        }
        //3.调用沙箱，获取执行结果
        CodeSandbox codeSandbox = CodeSandboxFactory.newInstance(type);
        codeSandbox = new CodeSandboxPoxy(codeSandbox);
        //获取输入用例
        String language = questionSubmit.getLanguage();
        String code = questionSubmit.getCode();
        String judgeCaseStr = question.getJudgeCase();
        List<JudgeCase> judgeCaseList = JSONUtil.toList(judgeCaseStr, JudgeCase.class);
        List<String> inputList = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build(); 
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
        List<String> outputList = executeCodeResponse.getOutputList();
        //4.根据沙箱的执行结果，设置题目的判题状态和信息
        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setJudgeInfo(executeCodeResponse.getJudgeInfo());
        judgeContext.setInputList(inputList);
        judgeContext.setOutputList(outputList);
        judgeContext.setQuestion(question);
        judgeContext.setJudgeCaseList(judgeCaseList);
        judgeContext.setQuestionSubmit(questionSubmit);

        JudgeInfo judgeInfo = judgeManager.doJudge(judgeContext);
        //5.修改数据库中的判题结果
        questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        questionSubmitUpdate.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        update = questionSubmitService.updateById(questionSubmitUpdate);
        if(!update){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"题目状态更新错误");
        }
        QuestionSubmit questionSubmitResult = questionSubmitService.getById(questionId);

        return questionSubmitResult;
    }
}
