package com.xxl.job.service.handler;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.executor.service.jobhandler.SampleGlueXxlJob;

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

    @Override
    public void execute() throws Exception {
        String method = sampleGlueXxlJob.method();
        System.out.println(method);
        XxlJobHelper.log(method);
    }
}
