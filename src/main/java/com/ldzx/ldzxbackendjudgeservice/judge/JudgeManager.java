package com.ldzx.ldzxbackendjudgeservice.judge;

import com.ldzx.ldzxbackendjudgeservice.judge.strategy.DefaultJudgeStrategy;
import com.ldzx.ldzxbackendjudgeservice.judge.strategy.JavaLanguageJudgeStrategy;
import com.ldzx.ldzxbackendjudgeservice.judge.strategy.JudgeContext;
import com.ldzx.ldzxbackendjudgeservice.judge.strategy.JudgeStrategy;
import com.ldzx.ldzxojbackendmodel.model.codesandbox.JudgeInfo;
import com.ldzx.ldzxojbackendmodel.model.entity.QuestionSubmit;
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
