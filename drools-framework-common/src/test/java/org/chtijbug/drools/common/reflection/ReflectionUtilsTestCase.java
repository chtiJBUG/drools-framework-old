package org.chtijbug.drools.common.reflection;

import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created with IntelliJ IDEA.
 * User: samuel
 * Date: 01/10/12
 * Time: 09:42
 */
public class ReflectionUtilsTestCase {


    @Test
    public void runIsGetter() {

        try {
            Method methodToEval = TestClass.class.getMethod("getProperty");
            assertTrue(ReflectionUtils.IsGetter(methodToEval));

            methodToEval = TestClass.class.getMethod("execute");
            assertFalse(ReflectionUtils.IsGetter(methodToEval));


        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            fail();
        }

    }


    private class TestClass {
        private String property;

        public String getProperty() {
            return property;
        }

        public void execute()  {

        }
    }

}
