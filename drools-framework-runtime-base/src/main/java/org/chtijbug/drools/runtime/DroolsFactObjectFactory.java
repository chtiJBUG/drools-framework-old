/**
 *
 */
package org.chtijbug.drools.runtime;

import org.apache.commons.beanutils.BeanMap;
import org.chtijbug.drools.common.log.Logger;
import org.chtijbug.drools.common.log.LoggerFactory;
import org.chtijbug.drools.entity.DroolsFactObject;
import org.chtijbug.drools.entity.DroolsFactObjectAttribute;

/**
 * @author Bertrand Gressier
 * 27 déc. 2011
 */
public class DroolsFactObjectFactory {

    private static transient Logger logger = LoggerFactory.getLogger(DroolsFactObjectFactory.class);

    protected DroolsFactObjectFactory() {

    }

    public static DroolsFactObject createFactObject(Object o) {
        return createFactObject(o, 0);
    }

    public static DroolsFactObject createFactObject(Object o, int version) {
        logger.entry("createFactObject", o, version);
        DroolsFactObject createFactObject = null;
        try {
            if (o != null) {
                createFactObject = new DroolsFactObject(o, version);
                createFactObject.setFullClassName(o.getClass().getCanonicalName());
                createFactObject.setHashCode(o.hashCode());

                BeanMap m = new BeanMap(o);
                for (Object para : m.keySet()) {
                    if (!para.toString().equals("class")) {
                        if (m.get(para) != null) {
                            DroolsFactObjectAttribute attribute = new DroolsFactObjectAttribute(para.toString(), m.get(para).toString(), m.get(para).getClass().getSimpleName());
                            createFactObject.getListfactObjectAttributes().add(attribute);
                        } else {
                            DroolsFactObjectAttribute attribute = new DroolsFactObjectAttribute(para.toString(), "null", "null");
                            createFactObject.getListfactObjectAttributes().add(attribute);
                        }

                    }

                }
            }
        } catch (Exception e) {
            logger.error("Not possible to introspect {} for reason {}", o, e);
        } finally {
            logger.entry("createFactObject", createFactObject);
            return createFactObject;
        }
    }

}
