package com.solarexsoft.solarexioc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * <pre>
 *    Author: houruhou
 *    Project: https://solarex.github.io/projects
 *    CreatAt: 21/06/2017
 *    Desc:
 * </pre>
 */

public class SolarexInvocationHandler implements InvocationHandler {
    private Map<String, Method> mMethodMap;
    private Object mTarget;

    public SolarexInvocationHandler(Object target, Map<String, Method> methodMap) {
        mTarget = target;
        mMethodMap = methodMap;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();
        Method callMethod = mMethodMap.get(methodName);
        if (callMethod != null) {
            Object obj = callMethod.invoke(mTarget, args);
            return obj;
        }
        return null;
    }
}
