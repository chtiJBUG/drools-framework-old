/**
 *
 */
package org.chtijbug.drools.runtime;

import org.apache.commons.beanutils.BeanMap;
import org.chtijbug.drools.entity.DroolsFactObject;
import org.chtijbug.drools.entity.DroolsFactObjectAttribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Bertrand Gressier
 * @date 27 déc. 2011
 */
public class DroolsFactObjectFactory {

    static final transient Logger LOGGER = LoggerFactory.getLogger(DroolsFactObjectFactory.class);

    protected DroolsFactObjectFactory() {

    }

    public static DroolsFactObject createFactObject(Object o) {
        return createFactObject(o, 0);
    }

    public static DroolsFactObject createFactObject(Object o, int version) {
        DroolsFactObject createFactObject = null;
        if (o != null) {
            createFactObject = new DroolsFactObject(o, version);
            createFactObject.setFullClassName(o.getClass().getCanonicalName());
            createFactObject.setHashCode(o.hashCode());

            try {
                BeanMap m = new BeanMap(o);
                for (Object para : m.keySet()) {
                    if (!para.toString().equals("class")) {
                        DroolsFactObjectAttribute attribute = new DroolsFactObjectAttribute(para.toString(), m.get(para).toString(), m.get(para).getClass().getSimpleName());
                        createFactObject.getListfactObjectAttributes().add(attribute);
                    }

                }
            } catch (Exception e) {
                LOGGER.error("Not possible to introspect {} for reason {}", o, e);
            }
        }
        return createFactObject;
    }

}
