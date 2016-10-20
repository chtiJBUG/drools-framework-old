package org.chtijbug.drools.common.jaxb;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;

public final class JAXBContextUtils {

     public static <T> String marshallObjectAsString( Class<T> clazz, T object) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

        // output pretty printed
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        StringWriter writer = new StringWriter();
        jaxbMarshaller.marshal(object, writer);
        return writer.toString();
    }

    public static <T> T unmarshallObject( Class<T> clazz, InputStream objectAsInputStream) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

        InputStreamReader reader = new InputStreamReader(objectAsInputStream);
        return (T) jaxbUnmarshaller.unmarshal(reader);
    }

}
