package com.xxl.job.executor.spring;

import com.xxl.job.core.handler.annotation.XxlJob;
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
@Configuration
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
            Map<Method, XxlJob> xxlJobAnnotatedMethods = MethodIntrospector.selectMethods(targetClass,
                    new MethodIntrospector.MetadataLookup<XxlJob>() {
                        @Override
                        public XxlJob inspect(Method method) {
                            return AnnotatedElementUtils.findMergedAnnotation(method, XxlJob.class);
                        }
                    });
            if (!xxlJobAnnotatedMethods.isEmpty()) {
                // 再对其筛选出是否同时携带@Scheduled或@Schedules
                Map<Method, Set<Scheduled>> annotatedMethods = MethodIntrospector.selectMethods(targetClass,
                        (MethodIntrospector.MetadataLookup<Set<Scheduled>>) method -> {
                            Set<Scheduled> scheduledMethods = AnnotatedElementUtils.getMergedRepeatableAnnotations(
                                    method, Scheduled.class, Schedules.class);;
                            return (!scheduledMethods.isEmpty() ? scheduledMethods : null);
                        });
                // 如果该bean同时有同时携带两种注解的方法，则将其替换为null
                if (!annotatedMethods.isEmpty()) {
                    /**
                     * spring在遍历后处理器的过程中，会判断每次处理器返回的bean是否为null
                     * 如果为null则不执行后序的处理器，直接返回上一个处理器返回的bean
                     */
                    return null;
                }
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
