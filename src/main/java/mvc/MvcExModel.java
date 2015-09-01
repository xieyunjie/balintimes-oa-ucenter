package mvc;

import java.lang.annotation.*;

/**
 * Created by AlexXie on 2015/8/24.
 */
@Documented
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface MvcExModel {
}
