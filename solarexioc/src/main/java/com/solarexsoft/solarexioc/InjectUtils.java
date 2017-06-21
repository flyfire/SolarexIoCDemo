package com.solarexsoft.solarexioc;

import android.content.Context;
import android.view.View;

import com.solarexsoft.solarexioc.annotation.ContentView;
import com.solarexsoft.solarexioc.annotation.EventBase;
import com.solarexsoft.solarexioc.annotation.ViewInject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 *    Author: houruhou
 *    Project: https://solarex.github.io/projects
 *    CreatAt: 21/06/2017
 *    Desc:
 * </pre>
 */

public class InjectUtils {
    public static void inject(Context context) {
        injectLayout(context);
        injectViews(context);
        injectListener(context);
    }

    private static void injectLayout(Context context) {
        int layoutId = 0;
        Class<?> clazz = context.getClass();
        ContentView contentView = clazz.getAnnotation(ContentView.class);
        if (contentView != null) {
            layoutId = contentView.value();
            try {
                Method setContentView = clazz.getMethod("setContentView", int.class);
                setContentView.invoke(context, layoutId);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private static void injectViews(Context context) {
        Class<?> clazz = context.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            ViewInject viewInject = field.getAnnotation(ViewInject.class);
            if (viewInject != null) {
                int valueId = viewInject.value();
                try {
                    Method method = clazz.getMethod("findViewById", int.class);
                    View view = (View) method.invoke(context, valueId);
                    field.setAccessible(true);
                    field.set(context, view);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } finally {
                    field.setAccessible(false);
                }
            }
        }

    }

    private static void injectListener(Context context) {
        Class<?> clazz = context.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            Annotation[] annotations = method.getAnnotations();
            for (Annotation annotation : annotations) {
                Class<?> annotationType = annotation.annotationType();
                EventBase eventBase = annotationType.getAnnotation(EventBase.class);
                if (eventBase == null) {
                    continue;
                }
                String listenerSetter = eventBase.listenerSetter();
                Class<?> listenerType = eventBase.listenerType();
                String listenerMethodName = eventBase.listenerMethodName();
                Map<String, Method> methodMap = new HashMap<>();
                methodMap.put(listenerMethodName, method);
                try {
                    Method valueMethod = annotationType.getMethod("value");
                    int[] viewIds = (int[]) valueMethod.invoke(annotation);
                    for (int viewId : viewIds) {
                        Method findViewById = clazz.getMethod("findViewById", int.class);
                        View view = (View) findViewById.invoke(context, viewId);
                        if (view == null) {
                            continue;
                        }
                        Method listenerSetterMethod = view.getClass().getMethod(listenerSetter,
                                listenerType);
                        Object proxy = Proxy.newProxyInstance(context.getClassLoader(),
                                new Class[]{listenerType}, new SolarexInvocationHandler(context,
                                        methodMap));
                        listenerSetterMethod.invoke(view, proxy);

                    }
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
