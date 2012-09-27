package org.chtijbug.drools.common.reflection;

import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: samuel
 * Date: 26/09/12
 * Time: 15:45
 */
public class ReflectionUtils {

    /**
     * Returns <code>true</code> if the method is a getter meaning :
     * <ul>
     *     <li>method name start with 'get' </li>
     *     <li>no parameters expected from the method</li>
     *     <li>the return type of the method is not void</li>
     * </ul>
     * Otherwise, it will return <code>false</code>
     * @param method Method
     * @return true if the method is a getter. false otherwise.
     */
    public static boolean IsGetter(Method method){
        if(!method.getName().startsWith("get"))      return false;
        if(method.getParameterTypes().length != 0)   return false;
        if(void.class.equals(method.getReturnType())) return false;
        return true;
    }
}
