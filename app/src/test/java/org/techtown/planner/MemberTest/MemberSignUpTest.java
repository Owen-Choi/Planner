package org.techtown.planner.MemberTest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.techtown.planner.service.ConditionCheckingCompliation;

public class MemberSignUpTest {

    ConditionCheckingCompliation ccc;

    @Before
    public void prepare() {
        ccc = new ConditionCheckingCompliation();
    }

    @Test
    public void sign_up_condition() {
        Assert.assertTrue(ccc.SignUpConditionChecker("Email", "123456qq",
                "Nickname", "Name", "19990423", "male"));
    }

    @Test
    public void sign_up_filtering() {
        Assert.assertFalse(ccc.SignUpConditionChecker("", "123456qq",
                "Nickname", "Name", "19990423", "male"));
        Assert.assertFalse(ccc.SignUpConditionChecker("Email", "",
                "Nickname", "Name", "19990423", "male"));
        Assert.assertFalse(ccc.SignUpConditionChecker("Email", "123456qq",
                "", "Name", "19990423", "male"));
        Assert.assertFalse(ccc.SignUpConditionChecker("Email", "123456qq",
                "Nickname", "", "19990423", "male"));
        Assert.assertFalse(ccc.SignUpConditionChecker("Email", "123456qq",
                "Nickname", "Name", "", "male"));
        Assert.assertFalse(ccc.SignUpConditionChecker("Email", "123456qq",
                "Nickname", "Name", "19990423", ""));
    }
}
