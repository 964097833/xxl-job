package com.xxl.job.executorbiz;

import com.xxl.job.admin.core.model.XxlJobInfo;
import com.xxl.job.admin.core.util.I18nUtil;
import com.xxl.job.core.biz.ExecutorBiz;
import com.xxl.job.core.biz.client.ExecutorBizClient;
import com.xxl.job.core.biz.model.*;
import com.xxl.job.core.enums.ExecutorBlockStrategyEnum;
import com.xxl.job.core.glue.GlueTypeEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.text.MessageFormat;

/**
 * executor api test
 *
 * Created by xuxueli on 17/5/12.
 */
@EnableScheduling
public class ExecutorBizTest {

    // admin-client
    private static String addressUrl = "http://127.0.0.1:9999/";
    private static String accessToken = null;

    @Test
    public void beat() throws Exception {
        ExecutorBiz executorBiz = new ExecutorBizClient(addressUrl, accessToken);
        // Act
        final ReturnT<String> retval = executorBiz.beat();

        // Assert result
        Assertions.assertNotNull(retval);
        Assertions.assertNull(((ReturnT<String>) retval).getContent());
        Assertions.assertEquals(200, retval.getCode());
        Assertions.assertNull(retval.getMsg());
    }

    @Test
    public void idleBeat(){
        ExecutorBiz executorBiz = new ExecutorBizClient(addressUrl, accessToken);

        final int jobId = 0;

        // Act
        final ReturnT<String> retval = executorBiz.idleBeat(new IdleBeatParam(jobId));

        // Assert result
        Assertions.assertNotNull(retval);
        Assertions.assertNull(((ReturnT<String>) retval).getContent());
        Assertions.assertEquals(500, retval.getCode());
        Assertions.assertEquals("job thread is running or has trigger queue.", retval.getMsg());
    }

    @Test
    public void run(){
        ExecutorBiz executorBiz = new ExecutorBizClient(addressUrl, accessToken);

        // trigger data
        final TriggerParam triggerParam = new TriggerParam();
        triggerParam.setJobId(1);
        triggerParam.setExecutorHandler("demoJobHandler");
        triggerParam.setExecutorParams(null);
        triggerParam.setExecutorBlockStrategy(ExecutorBlockStrategyEnum.COVER_EARLY.name());
        triggerParam.setGlueType(GlueTypeEnum.BEAN.name());
        triggerParam.setGlueSource(null);
        triggerParam.setGlueUpdatetime(System.currentTimeMillis());
        triggerParam.setLogId(1);
        triggerParam.setLogDateTime(System.currentTimeMillis());

        // Act
        final ReturnT<String> retval = executorBiz.run(triggerParam);

        // Assert result
        Assertions.assertNotNull(retval);
    }

    @Test
    public void kill(){
        ExecutorBiz executorBiz = new ExecutorBizClient(addressUrl, accessToken);

        final int jobId = 0;

        // Act
        final ReturnT<String> retval = executorBiz.kill(new KillParam(jobId));

        // Assert result
        Assertions.assertNotNull(retval);
        Assertions.assertNull(((ReturnT<String>) retval).getContent());
        Assertions.assertEquals(200, retval.getCode());
        Assertions.assertNull(retval.getMsg());
    }

    @Test
    public void log(){
        ExecutorBiz executorBiz = new ExecutorBizClient(addressUrl, accessToken);

        final long logDateTim = 0L;
        final long logId = 0;
        final int fromLineNum = 0;

        // Act
        final ReturnT<LogResult> retval = executorBiz.log(new LogParam(logDateTim, logId, fromLineNum));

        // Assert result
        Assertions.assertNotNull(retval);
    }

    @Test
    public void trimTest() {

        String str1 = "  ab c  ";
        System.out.println(str1.length());
        str1 = str1.trim();
        System.out.println(str1.length());

    }

    @Test
    public void aVoid() {
        // 》ChildJobId valid

        XxlJobInfo jobInfo = new XxlJobInfo();
        jobInfo.setChildJobId("1111,,2222");

        if (jobInfo.getChildJobId()!=null && jobInfo.getChildJobId().trim().length()>0) {
            String[] childJobIds = jobInfo.getChildJobId().split(",");
            for (String childJobIdItem: childJobIds) {
                if (childJobIdItem!=null && childJobIdItem.trim().length()>0) {
                    System.out.println(childJobIdItem);
                } else {
                    System.out.println("进入else"+childJobIdItem);
                }
            }

//            // join , avoid "xxx,,"
//            String temp = "";
//            for (String item:childJobIds) {
//                if (!"".equals(item) && item.length() > 0) {
//                    temp += it802366qD66
//                em + ",";
//                }
//            }
//            temp = temp.substring(0, temp.length()-1);

//            System.out.println(temp);
//            jobInfo.setChildJobId(temp);
        }

    }

}
