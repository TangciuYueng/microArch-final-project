package cn.edu.tongji.Emotions.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceLoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(ServiceLoggingAspect.class);

    // 在执行CheckinEmotionServiceImpl中的任何方法前调用
    @Before("execution(* cn.edu.tongji.Emotions.service.impl.CheckinEmotionServiceImpl.*(..))")
    public void logBeforeServiceMethod(JoinPoint joinPoint) {
        logger.info("开始执行方法: {}，参数：{}", joinPoint.getSignature().getName(), joinPoint.getArgs());
    }

    // 在执行CheckinEmotionServiceImpl中的任何方法后调用
    @After("execution(* cn.edu.tongji.Emotions.service.impl.CheckinEmotionServiceImpl.*(..))")
    public void logAfterServiceMethod(JoinPoint joinPoint) {
        logger.info("方法执行完成: {}", joinPoint.getSignature().getName());
    }
}
