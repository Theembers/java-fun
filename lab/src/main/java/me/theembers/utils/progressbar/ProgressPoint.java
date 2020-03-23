package me.theembers.utils.progressbar;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author TheEmbers Guo
 * @version 1.0
 * createTime 2020-03-05 14:27
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ProgressPoint {
}
