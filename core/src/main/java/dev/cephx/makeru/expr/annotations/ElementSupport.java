package dev.cephx.makeru.expr.annotations;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.CONSTRUCTOR, ElementType.TYPE_USE})
@Retention(RetentionPolicy.SOURCE)
@Documented
public @interface ElementSupport {
    LimitedFeatureSupport[] value();
}
