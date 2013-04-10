package org.chtijbug.drools.common.jaxb;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class JAXBContextUtilsTest {

    @Test
    public void testMarshallObjectAsString() throws Exception {
        ToTestClass toTestClass = new ToTestClass();
        toTestClass.setAttr1("attr1");
        toTestClass.setAttr2("attr2");

        String toEvaluate = JAXBContextUtils.marshallObjectAsString(ToTestClass.class, toTestClass);

        assertThat(toEvaluate).isNotNull();
        assertThat(toEvaluate).isEqualTo("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<toTestClass>\n" +
                "    <attr1>attr1</attr1>\n" +
                "    <attr2>attr2</attr2>\n" +
                "</toTestClass>\n");
    }

}
