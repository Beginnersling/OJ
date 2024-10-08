package com.ldzx.ldzxcodesodebox;
import cn.hutool.core.date.StopWatch;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.StrUtil;
import com.ldzx.ldzxcodesodebox.model.ExecuteCodeRequest;
import com.ldzx.ldzxcodesodebox.model.ExecuteCodeResponse;
import com.ldzx.ldzxcodesodebox.model.ExecuteMessage;
import com.ldzx.ldzxcodesodebox.model.JudgeInfo;
import com.ldzx.ldzxcodesodebox.utils.ProcessUtils;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class JavaNativeCodeSandbox implements CodeSandbox {
    private  static final String GLOBAL_CODE_DIR_NAME = "temCode";
    private  static final String GLOBAL_JAVA_CLASS_NAME = "Main.java";


    public static void main(String[] args) {
        JavaNativeCodeSandbox javaNativeCodeSandbox = new JavaNativeCodeSandbox();
        ExecuteCodeRequest executeCodeRequest = new ExecuteCodeRequest();
        executeCodeRequest.setInputList(Arrays.asList("1 2", "1 3"));
//        String code = ResourceUtil.readStr("testCode/simpleComputeArgs/Main.java", StandardCharsets.UTF_8);
        String code = ResourceUtil.readStr("testCode/unsafeCode/RunFileError.java", StandardCharsets.UTF_8);
//        String code = ResourceUtil.readStr("testCode/simpleCompute/Main.java", StandardCharsets.UTF_8);
        executeCodeRequest.setCode(code);
        executeCodeRequest.setLanguage("java");
        ExecuteCodeResponse executeCodeResponse = javaNativeCodeSandbox.executeCode(executeCodeRequest);
        System.out.println(executeCodeResponse);
    }

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        //用户输入信息
        List<String> inputList = executeCodeRequest.getInputList();
        String code = executeCodeRequest.getCode();
        String language = executeCodeRequest.getLanguage();
        //项目根目录
        String userDir = System.getProperty("user.dir");
        //存放文件目录
        String globalCodePathName = userDir + File.separator + GLOBAL_CODE_DIR_NAME;
        //判断文件是否存在，如果没有则新建
        if(!FileUtil.exist(globalCodePathName)){
            FileUtil.mkdir(globalCodePathName);
        }
        //1. 把用户的代码隔离存放为文件
        String userCodeParentPath = globalCodePathName + File.separator + UUID.randomUUID();
        //实际存放代码的文件目录
        String userCodePath = userCodeParentPath + File.separator + GLOBAL_JAVA_CLASS_NAME;
        File userCodeFile = FileUtil.writeString(code, userCodePath, StandardCharsets.UTF_8);
        //2. 编译代码得到class文件
        String compileCmd = String.format("javac -encoding utf-8 %s",userCodeFile.getAbsoluteFile());
        try {
            Process compileProcess = Runtime.getRuntime().exec(compileCmd);
            ExecuteMessage executeMessage = ProcessUtils.runProcessAndGetMessage(compileProcess,"编译");

        } catch (Exception e) {
            return getResponse(e);
        }
        //3. 执行代码，得出输出结果
        List<ExecuteMessage> executeMessageList = new ArrayList<>();
        for(String inputArgs : inputList ){
            String runCmd = String.format("java -Dfile.encoding=UTF-8 -cp %s Main %s", userCodeParentPath, inputArgs);
            try {
                Process runProcess = Runtime.getRuntime().exec(runCmd);
                ExecuteMessage executeMessage = ProcessUtils.runProcessAndGetMessage(runProcess, "运行");
                executeMessageList.add(executeMessage);
            } catch (Exception e) {
                return getResponse(e);            }
        }
        //4. 收集整理输出结果
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        List<String> outputList = executeMessageList.stream().map(ExecuteMessage::getMessage).collect(Collectors.toList());
        //取用最大值，用于判断是否超时
        long maxTime = 0;
        for(ExecuteMessage executeMessage : executeMessageList){
            String errorMessage = executeMessage.getErrorMessage();
            if(StrUtil.isNotBlank(executeMessage.getErrorMessage())){
                executeCodeResponse.setMessage(errorMessage);
                //执行中存在错误
                executeCodeResponse.setStatus(3);
                break;
            }
            outputList.add(executeMessage.getMessage());
            Long time = executeMessage.getTime();
            if(time != null){
                maxTime = Math.max(maxTime,time);
            }
        }
        // 正常运行完成
        if(outputList.size() == executeMessageList.size()){
            executeCodeResponse.setStatus(1);
        }
        executeCodeResponse.setOutputList(outputList);
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setTime(maxTime);
        //5. 文件清理
        if(userCodeFile.getParentFile() != null){
            boolean del = FileUtil.del(userCodeParentPath);
            System.out.println("删除" + (del ? "成功" : "失败"));
        }
        return executeCodeResponse;
    }

    /**
     * 获取错误响应
     *
     * @param e
     * @return
     */
    private ExecuteCodeResponse getResponse(Throwable e){
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        executeCodeResponse.setOutputList(new ArrayList<>());
        executeCodeResponse.setMessage(e.getMessage());
        //表示代码沙箱错误
        executeCodeResponse.setStatus(2);
        executeCodeResponse.setJudgeInfo(new JudgeInfo());
        return executeCodeResponse;

    }

}
