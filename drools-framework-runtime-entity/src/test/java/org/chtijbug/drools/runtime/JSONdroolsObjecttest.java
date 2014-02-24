package org.chtijbug.drools.runtime;

import junit.framework.Assert;
import org.apache.commons.io.IOUtils;
import org.chtijbug.drools.common.date.DateHelper;
import org.chtijbug.drools.entity.DroolsFactObject;
import org.chtijbug.drools.runtime.pojotest.User;
import org.chtijbug.drools.runtime.pojotest.UserName;
import org.junit.Test;

import java.io.InputStream;

/**
 * Created by IntelliJ IDEA.
 * Date: 24/02/14
 * Time: 16:25
 * To change this template use File | Settings | File Templates.
 */
public class JSONdroolsObjecttest {
    @Test
    public void testJson() throws Exception {

        UserName userName = new UserName();
        userName.setFirstname("Katamreddy");
        userName.setMiddlename("Siva");
        userName.setLastname("PrasadReddy");

        User user = new User();
        user.setUserId("1");
        user.setUserName(userName);
        user.setDob(DateHelper.getDate("2013-12-31"));
        DroolsFactObject droolsFactObject = new DroolsFactObject(user, 1);
        InputStream stream = JSONdroolsObjecttest.class.getResourceAsStream("/user.json");
        String toto = IOUtils.toString(stream,"utf-8");
        Assert.assertTrue(toto.equals(droolsFactObject.getRealObject_JSON()));
    }
}
