package org.chtijbug.drools.runtime;

import org.junit.Test;

import static junit.framework.Assert.assertNotNull;

public class DroolsChtijbugExceptionTest {
    @Test
    public void should_keep_root_cause()  {
        IllegalArgumentException rootCause = new IllegalArgumentException();

        DroolsChtijbugException chtijbugException = new DroolsChtijbugException("key", "bla", rootCause);

        assertNotNull(chtijbugException.getCause());
    }
}
