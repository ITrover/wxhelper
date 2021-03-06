package cn.edu.cqupt.nmid.wxhelper.wxhelper.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Component("logAspect")
@Aspect
public class LogAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 定义切面,controller下的所有方法
     */
    @Pointcut("execution(* cn.edu.cqupt.nmid.wxhelper.wxhelper.controller.*.*(..))")
    public void log() {
    }
    @Pointcut("execution(* cn.edu.cqupt.nmid.wxhelper.wxhelper.controller.admin.*.*(..)))")
    public void logAdmin(){}

    @Before("log() || logAdmin()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        RequestLog requestLog = new RequestLog(url, ip, classMethod, args);
        logger.info("request: {}", requestLog);
    }

    @AfterReturning(pointcut = "log() || logAdmin()", returning = "result")
    public void doAfterReturn(Object result) {
        logger.info("result : {}", result);
    }

    @After("log() || logAdmin()")
    public void doAfter() {

    }
    //消息格式
    private class RequestLog {

        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

        public RequestLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "RequestLog{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }

    }
}