package com.ldzxoj.ldzxoj.judge;

import com.ldzxoj.ldzxoj.judge.strategy.DefaultJudgeStrategy;
import com.ldzxoj.ldzxoj.judge.strategy.JavaLanguageJudgeStrategy;
import com.ldzxoj.ldzxoj.judge.strategy.JudgeContext;
import com.ldzxoj.ldzxoj.judge.strategy.JudgeStrategy;
import com.ldzxoj.ldzxoj.model.dto.questionsubmit.JudgeInfo;
import com.ldzxoj.ldzxoj.model.entity.QuestionSubmit;
import org.springframework.stereotype.Service;

/**
 * 判题管理（执行什么策略的判题逻辑）
 */
@Service
public class JudgeManager {
    /**
     * 执行判题
     * @param judgeContext
     * @return
     */
    JudgeInfo doJudge(JudgeContext judgeContext){
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        String language = questionSubmit.getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if("java".equals(language) ){
            judgeStrategy = new JavaLanguageJudgeStrategy();
        }
        return judgeStrategy.doJudge(judgeContext);
    }
}
