package com.ldzxoj.ldzxoj.model.dto.question;

import lombok.Data;

/**
 * 题目配置
 */
@Data
public class JudgeConfig {
    /**
     * 时间限制
     */
    private long timeLimit;
    /**
     * 内存限制
     */
    private long memoryLimit;
    /**
     * 堆栈查找
     */
    private long stackLimit;







}
