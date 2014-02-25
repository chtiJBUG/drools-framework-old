package org.chtijbug.drools.common.file;

import com.google.common.base.Throwables;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by IntelliJ IDEA.
 * Date: 25/02/14
 * Time: 15:08
 * To change this template use File | Settings | File Templates.
 */
public class FileHelper {
    public static String getFileContent(InputStream inputStream) {
        String fileContent = null;
        try {
            fileContent = IOUtils.toString(inputStream);
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }
        return fileContent;
    }

}
