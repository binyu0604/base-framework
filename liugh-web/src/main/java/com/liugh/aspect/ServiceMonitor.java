package com.liugh.aspect;


import com.alibaba.fastjson.JSONObject;
import com.liugh.base.PublicResult;
import com.liugh.base.PublicResultConstant;
import com.liugh.util.ComUtil;
import com.liugh.util.StringUtils;
import com.liugh.validator.ParamValidator;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Map;


/**
 * 记录服务层方法参数,并对参数进行校验,同时监控方法执行时间
 *
 * @since: 15/11/21.
 * @author: yangjunming
 */
@Slf4j
@Aspect
@Component
public class ServiceMonitor {


    @Around("execution(* com.liugh..*Controller.*(..))")
    public Object logServiceAccess(ProceedingJoinPoint pjp) {
        long start = System.currentTimeMillis();

        String className = pjp.getTarget().getClass().getName();
        String fullMethodName = className + "." + pjp.getSignature().getName();

        boolean needLog = false;

        //记录参数,并对参数进行校验
        if (!className.contains("com.sun.proxy.$Proxy") && !className.contains("$$EnhancerBySpringCGLIB$$")) {
            needLog = true;
            if (pjp.getArgs() != null && pjp.getArgs().length > 0) {
                log.info(fullMethodName + "将被调用,下面是参数:");
                for (Object arg : pjp.getArgs()) {
                    JSONObject parse = (JSONObject)JSONObject.parse(arg.toString());
                    if(!ComUtil.isEmpty(parse.getString("passWord"))){
                        parse.put("passWord","*******");
                    }
                    if(!ComUtil.isEmpty(parse.getString("rePassWord"))){
                        parse.put("rePassWord","*******");
                    }
                    if(!ComUtil.isEmpty(parse.getString("oldPassWord"))){
                        parse.put("oldPassWord","*******");
                    }

                    log.info(parse.toJSONString());
                    Map<String, ArrayList<String>> validateResult = ParamValidator.validator(arg);
                    if (validateResult != null) {
                        for (Map.Entry<String, ArrayList<String>> entry : validateResult.entrySet()) {
                            log.error(fullMethodName + "参数:" + arg);
                            log.error(entry.getKey() + "校验失败,原因:" + entry.getValue());

                            long end = System.currentTimeMillis();
                            long elapsedMilliseconds = end - start;
                            log.info(fullMethodName + "执行耗时:" + elapsedMilliseconds + " 毫秒");
                            System.out.println(fullMethodName + "执行耗时:" + elapsedMilliseconds + " 毫秒");

                            return new PublicResult<>(PublicResultConstant.PARAM_ERROR, entry.getKey() + ":"
                                    + entry.getValue(), elapsedMilliseconds);
                        }
                    }
                }
            } else {
                log.info(fullMethodName + "将被调用");
            }
        }

        Object result = null;
        try {
            result = pjp.proceed();
            if (result instanceof PublicResult) {
                ((PublicResult<?>) result).setSuccess(true);
            }
        } catch (Throwable e) {

            if (result != null && result instanceof PublicResult) {
                PublicResult<?> errorResult = (PublicResult<?>) result;
                errorResult.setSuccess(false);
                if (StringUtils.isEmpty(errorResult.getResult())) {
                    errorResult.setResult(PublicResultConstant.UNKNOWN.result);
                }
                if (StringUtils.isEmpty(errorResult.getMsg())) {
                    errorResult.setMsg(e.getLocalizedMessage());
                }
            } else {
                result = new PublicResult<Object>(PublicResultConstant.UNKNOWN, e.getMessage());
            }
            log.error(fullMethodName + "执行出错,详情:", e);

            if (e instanceof DataAccessException) {
                //数据库层面的异常,继续向上抛,否则事务无法回滚
                ((PublicResult<Object>) result).setResult(PublicResultConstant.DATABASE_ERROR.result);
            }
        }


        long end = System.currentTimeMillis();
        long elapsedMilliseconds = end - start;
        if (needLog) {
            log.info(fullMethodName + "执行耗时:" + elapsedMilliseconds + " 毫秒");
        }

        if (result != null && result instanceof PublicResult) {
            ((PublicResult<?>) result).setElapsedMilliseconds(elapsedMilliseconds);
        }

        return result;
    }
}
