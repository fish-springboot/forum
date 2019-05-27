package com.github.fish56.forum.validate;

import com.github.fish56.forum.service.ServerResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import javax.validation.Validation;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * aop的方式可以拿到Controller方法的参数
 * 这是filter和interceptor所不具备的能力
 *
 * 这里我们拦截了前往各个Controller的请求，手动的进行的参数的校验。
 *
 * 而直接在Controller层进行validate有两大缺陷
 *   - 侵入式强，需要Controller层多一个BindingResult作为参数
 *   - 大量的样板代码
 *
 * 这里我们通过AOP技术拦截Controller，统一的进行参数校验以及异常处理
 * 大大的节省了代码量并提高的扩展性
 */
@Slf4j
@Aspect
@Component
public class ValidatorAop {
    /**
     * 这个切面是校验Controller层参数的合法性
     *   - 匹配com.github.fish56.forum包所有的子包的中以Controller结尾的类的所有方法
     *   - 只校验被@ShouldValidate标注的参数
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("execution(* com.github.fish56.forum.*..*Controller.*(..))")
    public Object validator(ProceedingJoinPoint joinPoint) throws Throwable{
        log.info("正在通过AOP验证字段的合法性（只检验被@ShouldValidate标注的）");

        // 同来存储校验结果
        StringBuilder errorMessage = new StringBuilder();

        // 获得方法的参数
        Object[] args = joinPoint.getArgs();

        // 获得当前切点的方法对象
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        // 获得所有参数的注解
        final Annotation[][] paramAnnotations = method.getParameterAnnotations();

        // 做个遍历，看看哪个参数被@Valid标注了
        for (int i = 0; i < paramAnnotations.length; i++) {
            for (Annotation a: paramAnnotations[i]) {

                // 判断当前参数是不是被ShouldValidate标注了
                if (a instanceof ShouldValidate) {
                    log.info("参数" + i + "被 @ShouldValidate注解了");

                    // 检查它的合法性，如果有错误信息，就将它输送给errorMessage
                    String error = ValidatorUtil.validate(args[i]);
                    if (error != null) {
                        errorMessage.append(error);
                    }
                }
            }
        }

        // 有错误的话直接返回
        if (errorMessage.length() > 0) {
            log.warn("字段不符合规则，已经打回去重写了！");

            // 删除最后面的 ", "
            errorMessage.delete(errorMessage.length() - 2, errorMessage.length() - 1);

            return ServerResponseMessage.get(400, errorMessage.toString());

        } else {
            Object object = joinPoint.proceed();

            log.info("字段符合规则！！");

            return object;
        }
    }
}
