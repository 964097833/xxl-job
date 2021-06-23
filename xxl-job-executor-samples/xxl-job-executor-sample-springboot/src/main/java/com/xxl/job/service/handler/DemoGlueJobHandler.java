package com.xxl.job.service.handler;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.executor.service.jobhandler.SampleGlueXxlJob;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;

/**
 * TODO
 *
 * @author yuqiaodi
 * @date 2021/6/8 11:02
 */
public class DemoGlueJobHandler extends IJobHandler {

    @Resource
    private SampleGlueXxlJob sampleGlueXxlJob;


    @Resource
    private Environment environment;

    private String result = "success";

    @Override
    public void execute() throws Exception {
        String property = environment.getProperty("xxl.job.executor.appname");
        System.out.println(property);
    }

    public static void main(String[] args) throws Exception {
        DemoGlueJobHandler demoGlueJobHandler = new DemoGlueJobHandler();
        demoGlueJobHandler.execute();
    }


    public static void Thread() {
        new Thread() {
            int i = 0;

            public void run() {
                boolean flg = false;
                while (!flg) {
                    try {
                        i++;
                        System.out.println("我是" + i);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

}
