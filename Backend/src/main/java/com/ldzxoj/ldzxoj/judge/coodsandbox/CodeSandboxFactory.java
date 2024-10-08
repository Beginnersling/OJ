package com.ldzxoj.ldzxoj.judge.coodsandbox;

import com.ldzxoj.ldzxoj.judge.coodsandbox.impl.ExampleCodeSandbox;
import com.ldzxoj.ldzxoj.judge.coodsandbox.impl.RemoteSandCodebox;
import com.ldzxoj.ldzxoj.judge.coodsandbox.impl.ThirdPartyCodeSandbox;

/**
 * 代码沙箱静态工厂（根据字符串参数创建指定的代码沙箱示例）
 */

public class CodeSandboxFactory {
    /**
     * 创建代码沙箱示例
     * @param type 沙箱类型
     * @return
     */
    public static CodeSandbox newInstance(String type){
        switch (type){
            case "example":
                return new ExampleCodeSandbox();
            case "remote":
                return new RemoteSandCodebox();
            case "thirdParty":
                return new ThirdPartyCodeSandbox();
            default:
                return new ExampleCodeSandbox();
        }

    }
}
