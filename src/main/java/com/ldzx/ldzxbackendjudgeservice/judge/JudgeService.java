package com.ldzx.ldzxbackendjudgeservice.judge;


import com.ldzx.ldzxojbackendmodel.model.entity.QuestionSubmit;

/**
 * 判题服务
 */
public interface JudgeService {
    QuestionSubmit doJudge(long questionSubmitId);
}
