package aspectadvice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import shiro.WebUser;
import util.JsonUtil;
import util.WebUserUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Component
@Aspect
public class ServicesAspectAdvice {

    private static Logger logger = LoggerFactory.getLogger(ServicesAspectAdvice.class.getSimpleName());


//    @Resource
//    private HttpServletRequest request;
    @Resource
    private WebUserUtil webUserUtil;

    public ServicesAspectAdvice() {
        // TODO Auto-generated constructor stub
    }

    // @Before(value = "execution(* service.impl..*.*(..))")
    public void doBefore(JoinPoint jp) {
        System.out.println("===========进入before advice============ \n");

    }

    @Around(value = "execution(* service.impl..*.*(..))")
    public Object doAround(ProceedingJoinPoint jp) throws Throwable {

        Object o = null;
        System.out.println(jp.toString());

        MDC.put("Exception", "Exception");
        MDC.put("Action", jp.toString());
        MDC.put("Method", jp.toString());

//        if (this.request != null) {
//            MDC.put("ClientIp", this.request.getRemoteAddr());
//            MDC.put("RequestUrl", this.request.getRequestURL().toString());
//        }
        MDC.put("AppUid", "oaucenter");
        MDC.put("AppName", "oaucenter");

        if (jp.toString().equalsIgnoreCase("execution(User service.impl.UserServiceImpl.InitWebUserByName(String))") == false) {
            WebUser webUser = webUserUtil.CurrentUser();
            if (webUser != null) {
                MDC.put("UserUid", webUser.getUid());
                MDC.put("UserName", webUser.getUsername());
                MDC.put("EmpUid", webUser.getUid());
                MDC.put("EmpName", webUser.getEmployeeName());
            }
        }
        MDC.put("Params", JsonUtil.ToJson(jp.getArgs()));
        logger.info("message");
        MDC.clear();

        o = jp.proceed();

        return o;
    }

    /*
     * AfterThrowing 与 Around 存在冲突(或者说重叠)
     * Around当中就能catch异常，所以也不需要使用AfterThrowing进行栏截
     * 去除Around后，AfterThrowing就能工作了
     */
    // @AfterThrowing(value = "execution(* service.impl..*.*(..))", throwing =
    // "e")
    public void doThrow(JoinPoint jp, Throwable e) {
        System.out.println("throw exception!!");
    }

}
