/*
 * Copyright 2014 Pymma Software
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
