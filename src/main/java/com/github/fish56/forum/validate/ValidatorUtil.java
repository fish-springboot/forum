package com.github.fish56.forum.validate;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import javax.validation.Validation;

/**
 * 手动检验字段的合法性
 */
@Slf4j
@Aspect
public class ValidatorUtil {
    private static javax.validation.Validator javaxValidator = Validation.buildDefaultValidatorFactory().getValidator();
    private static SpringValidatorAdapter validator =  new SpringValidatorAdapter(javaxValidator);

    public static String validateDefault(Object entity){
        return validate(entity);
    }

    public static String validate(Object entity, Object... validationHints){
        log.info("正在检验字段的合法性");
        Errors errors = new BeanPropertyBindingResult(entity, entity.getClass().getName());
        validator.validate(entity, errors, validationHints);

        // 如果没有错误信息，直接返回null
        if (!errors.hasErrors()){
            return null;

        } else {
            // 同来存储校验结果
            StringBuilder errorMessage = new StringBuilder();

            errors.getAllErrors().forEach((objectError) -> {
                // 将错误信息输出到errorMessage中
                errorMessage.append(objectError.getDefaultMessage() + ", ");

            });
            if (errorMessage.length() > 2) {
                errorMessage.delete(errorMessage.length() - 2, errorMessage.length());
            }
            return errorMessage.toString();
        }
    }
}
