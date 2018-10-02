package pl.qalabs.workshops.javaselenium1.support;

import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UserFile {
    String resource() default "";
}
