package org.techtown.planner.MemberTest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.techtown.planner.service.ConditionCheckingCompliation;

public class MemberSignInTest {

    ConditionCheckingCompliation ccc;

    @Before
    public void prepare() {
        ccc = new ConditionCheckingCompliation();
    }

    @Test
    public void sign_in_condition() {
        Assert.assertTrue(ccc.SignInConditionChecker("Email", "password"));
    }

    @Test
    public void sign_in_filtering() {
        Assert.assertFalse(ccc.SignInConditionChecker("", "password"));
        Assert.assertFalse(ccc.SignInConditionChecker("Email", ""));
    }
}
