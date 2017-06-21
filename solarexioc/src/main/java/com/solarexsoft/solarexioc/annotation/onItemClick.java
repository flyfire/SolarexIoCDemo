package com.solarexsoft.solarexioc.annotation;

import android.widget.AdapterView;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 *    Author: houruhou
 *    Project: https://solarex.github.io/projects
 *    CreatAt: 21/06/2017
 *    Desc:
 * </pre>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@EventBase(listenerSetter = "setOnItemClickListener",
        listenerType = AdapterView.OnItemClickListener.class,
        listenerMethodName = "onItemClick")
public @interface onItemClick {
    int[] value() default -1;
}
