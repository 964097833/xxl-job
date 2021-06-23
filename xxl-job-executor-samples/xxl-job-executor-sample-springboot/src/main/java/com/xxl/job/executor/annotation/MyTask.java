package com.xxl.job.executor.annotation;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

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
     *
     */
    String destroy() default "";


    /**
     * A special cron expression value that indicates a disabled trigger: {@value}.
     * <p>This is primarily meant for use with <code>${...}</code> placeholders,
     * allowing for external disabling of corresponding scheduled methods.
     * @since 5.1
     * @see ScheduledTaskRegistrar#CRON_DISABLED
     */
    String CRON_DISABLED = ScheduledTaskRegistrar.CRON_DISABLED;


    /**
     * A cron-like expression, extending the usual UN*X definition to include triggers
     * on the second, minute, hour, day of month, month, and day of week.
     * <p>For example, {@code "0 * * * * MON-FRI"} means once per minute on weekdays
     * (at the top of the minute - the 0th second).
     * <p>The fields read from left to right are interpreted as follows.
     * <ul>
     * <li>second</li>
     * <li>minute</li>
     * <li>hour</li>
     * <li>day of month</li>
     * <li>month</li>
     * <li>day of week</li>
     * </ul>
     * <p>The special value {@link #CRON_DISABLED "-"} indicates a disabled cron
     * trigger, primarily meant for externally specified values resolved by a
     * <code>${...}</code> placeholder.
     * @return an expression that can be parsed to a cron schedule
     * @see org.springframework.scheduling.support.CronSequenceGenerator
     */
    String cron() default "";

    /**
     * A time zone for which the cron expression will be resolved. By default, this
     * attribute is the empty String (i.e. the server's local time zone will be used).
     * @return a zone id accepted by {@link java.util.TimeZone#getTimeZone(String)},
     * or an empty String to indicate the server's default time zone
     * @since 4.0
     * @see org.springframework.scheduling.support.CronTrigger#CronTrigger(String, java.util.TimeZone)
     * @see java.util.TimeZone
     */
    String zone() default "";

    /**
     * Execute the annotated method with a fixed period in milliseconds between the
     * end of the last invocation and the start of the next.
     * @return the delay in milliseconds
     */
    long fixedDelay() default -1;

    /**
     * Execute the annotated method with a fixed period in milliseconds between the
     * end of the last invocation and the start of the next.
     * @return the delay in milliseconds as a String value, e.g. a placeholder
     * or a {@link java.time.Duration#parse java.time.Duration} compliant value
     * @since 3.2.2
     */
    String fixedDelayString() default "";

    /**
     * Execute the annotated method with a fixed period in milliseconds between
     * invocations.
     * @return the period in milliseconds
     */
    long fixedRate() default -1;

    /**
     * Execute the annotated method with a fixed period in milliseconds between
     * invocations.
     * @return the period in milliseconds as a String value, e.g. a placeholder
     * or a {@link java.time.Duration#parse java.time.Duration} compliant value
     * @since 3.2.2
     */
    String fixedRateString() default "";

    /**
     * Number of milliseconds to delay before the first execution of a
     * {@link #fixedRate} or {@link #fixedDelay} task.
     * @return the initial delay in milliseconds
     * @since 3.2
     */
    long initialDelay() default -1;

    /**
     * Number of milliseconds to delay before the first execution of a
     * {@link #fixedRate} or {@link #fixedDelay} task.
     * @return the initial delay in milliseconds as a String value, e.g. a placeholder
     * or a {@link java.time.Duration#parse java.time.Duration} compliant value
     * @since 3.2.2
     */
    String initialDelayString() default "";

}
