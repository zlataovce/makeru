package dev.cephx.makeru.expr.annotations;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.SOURCE)
@Documented
public @interface LimitedFeatureSupport {
    String[] value();
}
