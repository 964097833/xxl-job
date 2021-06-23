package com.xxl.job.executor.service.jobhandler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 测试glue功能类
 *
 * @author yuqiaodi
 * @date 2021/6/8 10:52
 */
@Component
public class SampleGlueXxlJob {

    private static int count = 0;

    public String method() {
        return "success";
    }

//    @Scheduled(fixedRate = 1000)
//    public void demoJobHandler111() throws Exception {
//        System.out.println("===============定时任务111执行第" + ++count + "次==============");
//    }

}
