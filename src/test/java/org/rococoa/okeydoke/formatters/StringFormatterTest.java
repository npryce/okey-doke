package org.rococoa.okeydoke.formatters;

import org.junit.Rule;
import org.junit.Test;
import org.rococoa.okeydoke.junit.ApprovalsRule;

import java.io.IOException;
import java.util.Arrays;

public class StringFormatterTest {

    // Test through the approvalsRule for now

    @Rule public final ApprovalsRule approver = ApprovalsRule.fileSystemRule("src/test/java");

    @Test public void a_string_is_itself() throws IOException {
        approver.assertApproved("A String");
    }

    @Test public void object_uses_toString() throws IOException {
        approver.assertApproved(new StringBuilder("A StringBuilder"));
    }

    @Test public void array_is_listed() throws IOException {
        approver.assertApproved(new String[] {"one", "two", "three"});
    }

    @Test public void iterable_is_listed() throws IOException {
        approver.assertApproved(Arrays.asList("one", "two", "three"));
    }


}
