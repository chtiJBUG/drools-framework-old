/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/

package org.chtijbug.kieserver.remote.rest.drools;

import org.chtijbug.kieserver.services.drools.DroolsFrameworkKieServerExtension;
import org.junit.Test;
import org.kie.server.services.api.KieServerRegistry;
import org.kie.server.services.api.SupportedTransports;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class DroolsApplicationComponentsServiceTest {

    @Test
    public void createResources() {

        DroolsFrameworkKieServerExtension extension = new DroolsFrameworkKieServerExtension();
        extension.init(null, mock(KieServerRegistry.class));
        List<Object> appComponentsList = extension.getAppComponents(SupportedTransports.REST);

        assertFalse("No application component retrieved!", appComponentsList.isEmpty());
        Object appComponent = appComponentsList.get(0);
        assertTrue("Expected a " + CommandResource.class.getSimpleName() + " instance",
                appComponent instanceof CommandResource);
    }

}
