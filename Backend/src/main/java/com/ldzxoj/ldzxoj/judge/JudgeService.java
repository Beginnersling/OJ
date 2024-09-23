package com.ldzxoj.ldzxoj.judge;

import com.ldzxoj.ldzxoj.model.entity.QuestionSubmit;

/**
 * 判题服务
 */
public interface JudgeService {
    QuestionSubmit doJudge(long questionSubmitId);
}
