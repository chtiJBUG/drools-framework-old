package org.chtijbug.drools.runtime.test;

import org.chtijbug.drools.runtime.impl.RuleBaseSingleton;
import org.chtijbug.drools.runtime.mbeans.StatefulSessionSupervision;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest(RuleBaseSingleton.class)
@PowerMockIgnore("javax.management.*")
public class RuleBaseSingletonTest {

    @Test
    public void should_not_fail_on_mbean_error() throws Exception {
        whenNew(StatefulSessionSupervision.class).withNoArguments().thenThrow(IllegalArgumentException.class);
        new RuleBaseSingleton();
    }
}
