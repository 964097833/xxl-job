package com.xxl.job.executor.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

import java.lang.annotation.*;

/**
 * 自定义定时任务注解
 *
 * @author yuqiaodi
 * @date 2021/6/23 10:49
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Scheduled
public @interface MyTask {

    /**
     * jobhandler name
     */
    String value();

    /**
     * init handler, invoked when JobThread init
     */
    String init() default "";

    /**
     * destroy handler, invoked when JobThread destroy
     */
    String destroy() default "";

    @AliasFor(annotation = Scheduled.class, attribute = "cron")
    String cron() default "";

    @AliasFor(annotation = Scheduled.class, attribute = "fixedRate")
    long fixedRate() default -1;

}
