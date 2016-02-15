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
package org.chtijbug.drools.runtime;

import org.apache.commons.beanutils.BeanMap;
import org.chtijbug.drools.entity.DroolsFactObject;
import org.chtijbug.drools.entity.DroolsFactObjectAttribute;
import org.chtijbug.drools.runtime.util.ISO8601DateParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;


public class DroolsFactObjectFactory {

    private static Logger logger = LoggerFactory.getLogger(DroolsFactObjectFactory.class);

    protected DroolsFactObjectFactory() {

    }

    public static DroolsFactObject createFactObject(Object o) {
        return createFactObject(o, 0);
    }

    public static DroolsFactObject createFactObject(Object o, int version) {
        logger.debug(">>createFactObject", o, version);
        DroolsFactObject createFactObject = null;
        try {
            if (o != null) {
                createFactObject = new DroolsFactObject(o, version);
                createFactObject.setFullClassName(o.getClass().getCanonicalName());
                createFactObject.setHashCode(o.hashCode());

                BeanMap m = new BeanMap(o);
                StringBuilder jsonText = new StringBuilder();
                jsonText.append("{");
                boolean isFirstParam = true;
                for (Object para : m.keySet()) {
                    if (!para.toString().equals("class")) {
                        if (m.get(para) != null) {
                            DroolsFactObjectAttribute attribute = new DroolsFactObjectAttribute(para.toString(), m.get(para).toString(), m.get(para).getClass().getSimpleName());
                            createFactObject.getListfactObjectAttributes().add(attribute);
                            if (!m.get(para).getClass().getSimpleName().endsWith("ArrayList")) {
                                if (!isFirstParam) {
                                    jsonText.append(",");
                                } else {
                                    isFirstParam = false;
                                }
                                jsonText.append("\"").append(para.toString()).append("\":");
                                if (m.get(para).getClass().getSimpleName().equals("String")) {
                                    jsonText.append("\"").append(m.get(para).toString()).append("\"");
                                } else if (m.get(para).getClass().isEnum()) {
                                    jsonText.append("\"").append(m.get(para).toString()).append("\"");
                                } else if (m.get(para).getClass().getSimpleName().equals("Date")) {
                                    jsonText.append("\"").append(ISO8601DateParser.toString(((Date) m.get(para)))).append("\"");
                                } else {
                                    jsonText.append("").append(m.get(para).toString()).append("");
                                }

                            }
                        } else {
                            DroolsFactObjectAttribute attribute = new DroolsFactObjectAttribute(para.toString(), "null", "null");
                            createFactObject.getListfactObjectAttributes().add(attribute);
                        }

                    }

                }
                jsonText.append("}");
                createFactObject.setRealObject_JSON(jsonText.toString());
            }
        } catch (Exception e) {
            logger.error("Not possible to introspect {} for reason {}", o, e);
        } finally {
            logger.debug("<<createFactObject", createFactObject);
            return createFactObject;
        }
    }

}
