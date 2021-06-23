package com.xxl.job.executor.spring;

import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.executor.annotation.MyTask;
import org.springframework.aop.framework.AopInfrastructureBean;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.MergedBeanDefinitionPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;

/**
 * TODO
 *
 * @author yuqiaodi
 * @date 2021/6/21 9:11
 */
//@Configuration
public class MyBeanPostProcessor implements MergedBeanDefinitionPostProcessor, Ordered {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        // schedule源码中会跳过这三种实例，所以此处也直接跳过
        if (bean instanceof AopInfrastructureBean || bean instanceof TaskScheduler ||
                bean instanceof ScheduledExecutorService) {
            // Ignore AOP infrastructure such as scoped proxies.
            return bean;
        }
        // 获取目标类
        Class<?> targetClass = AopProxyUtils.ultimateTargetClass(bean);
        // 判断给定的类是否适合携带指定的注解(只要类名不为java.开头和Order类，即为true)
        if (AnnotationUtils.isCandidateClass(targetClass, Arrays.asList(Scheduled.class, Schedules.class))) {
            // 将bean中带有@XxlJob筛选出来
            Map<Method, MyTask> xxlJobAnnotatedMethods = MethodIntrospector.selectMethods(targetClass,
                    new MethodIntrospector.MetadataLookup<MyTask>() {
                        @Override
                        public MyTask inspect(Method method) {
                            return AnnotatedElementUtils.findMergedAnnotation(method, MyTask.class);
                        }
                    });
            if (!xxlJobAnnotatedMethods.isEmpty()) {
                /**
                 * spring在遍历后处理器的过程中，会判断每次处理器返回的bean是否为null
                 * 如果为null则不执行后序的处理器，直接返回上一个处理器返回的bean
                 */
                return null;
            }
        }

        return bean;
    }

    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE - 1;
    }

    @Override
    public void postProcessMergedBeanDefinition(RootBeanDefinition beanDefinition, Class<?> beanType, String beanName) {

    }
}
