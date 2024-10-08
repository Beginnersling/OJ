package com.ldzx.ldzxcodesodebox;

import com.ldzx.ldzxcodesodebox.model.ExecuteCodeRequest;
import com.ldzx.ldzxcodesodebox.model.ExecuteCodeResponse;

public interface CodeSandbox {
    /**
     * 执行代码
     * @param executeCodeRequest
     * @return
     */
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}
