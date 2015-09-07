package mvc;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.annotation.Annotation;

/**
 * Created by AlexXie on 2015/8/24.
 */
public class MvcHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    public boolean supportsParameter(MethodParameter methodParameter) {

        Annotation[] as = methodParameter.getParameterAnnotations();
        for (Annotation a : as) {
            if (a.annotationType() == MvcExModel.class) {
                Class<?> clazz = methodParameter.getParameterType();
                if (clazz.isAssignableFrom(MvcSessionID.class) || clazz.isAssignableFrom(MvcWebUser.class)) {
                    return true;
                }
            }
        }
        return false;
    }

    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {

        Class<?> clazz = methodParameter.getParameterType();
        if (clazz.isAssignableFrom(MvcSessionID.class)) {
            MvcSessionID sessionID = new MvcSessionID();
            System.out.println(nativeWebRequest.getHeader("redissessionid"));
            sessionID.setSessionID(nativeWebRequest.getHeader("redissessionid"));
            return sessionID;
        }
        return null;
    }
}
