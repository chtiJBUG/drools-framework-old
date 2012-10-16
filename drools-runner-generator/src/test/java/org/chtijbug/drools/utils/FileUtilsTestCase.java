package org.chtijbug.drools.utils;

import org.junit.Test;

import java.io.File;
import java.net.URL;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: samuel
 * Date: 03/10/12
 * Time: 23:03
 */
public class FileUtilsTestCase {

    @Test
    public void runGetPomContent() {
        try {

            byte[] content = FileUtils.getByteArrayContent("/templates/log4j-template");
            String toEval = new String(content);
            assertNotNull(toEval);
            assertTrue(toEval.contains("log4j"));
        } catch (Throwable e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testReplaceTokenInFile() {
        try {
            URL url = FileUtilsTestCase.class.getResource("/replace-token-file");
            File file = new File(url.getFile());
            String tokenKey = "#JUNIT#";
            String tokenValue = "value";
            StringBuffer content = FileUtils.replaceTokenFromFile(file, tokenKey, tokenValue);
            assertNotNull(content);
            assertFalse(content.toString().contains("#JUNIT#"));
            System.out.println(content);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

}
