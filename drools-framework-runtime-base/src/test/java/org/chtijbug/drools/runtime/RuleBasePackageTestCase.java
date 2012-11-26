package org.chtijbug.drools.runtime;

import com.thoughtworks.xstream.XStream;
import org.junit.Assert;
import org.junit.Test;


import java.io.*;

import static org.junit.Assert.assertNotNull;

/**
 * Created with IntelliJ IDEA.
 * User: Samuel
 * Date: 26/11/12
 * Time: 12:21
 * To change this template use File | Settings | File Templates.
 */
public class RuleBasePackageTestCase {

    @Test
    public void testXStreamSerialization() {

        try {
            XStream xstream = new XStream();
            RuleBasePackage ruleBasePackage = RuleBaseBuilder.createPackageBasePackage("fibonacci.drl") ;
            String pkgXML = xstream.toXML(ruleBasePackage);
            FileWriter fstream = new FileWriter("/tmp/chtijbug-rule-cache");
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(pkgXML);
            out.close();
        } catch (IOException ex) {
            Assert.fail(ex.getMessage() );
        } catch (DroolsChtijbugException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void testXStreamDeserialization() {
        String ruleBaseXML = null;
        try {
            ruleBaseXML = readFileAsString("/tmp/chtijbug-rule-cache");
            XStream xStream = new XStream();
            RuleBasePackage ruleBasePackage = (RuleBasePackage) xStream.fromXML(ruleBaseXML);
            assertNotNull(ruleBasePackage);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }


    private static String readFileAsString(String filePath)
            throws java.io.IOException {
        StringBuffer fileData = new StringBuffer(1000);
        BufferedReader reader = new BufferedReader(
                new FileReader(filePath));
        char[] buf = new char[1024];
        int numRead = 0;
        while ((numRead = reader.read(buf)) != -1) {
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
            buf = new char[1024];
        }
        reader.close();
        return fileData.toString();
    }

}