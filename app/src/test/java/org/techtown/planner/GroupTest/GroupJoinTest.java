package org.techtown.planner.GroupTest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.techtown.planner.domain.Group.GroupContent;
import org.techtown.planner.service.ConditionCheckingCompliation;

public class GroupJoinTest {

    ConditionCheckingCompliation ccc;
    GroupContent groupContent;

    @Before
    public void BeforeTest() {
        ccc = new ConditionCheckingCompliation();
        groupContent = new GroupContent("테스트용 그룹", "masterID", "123456789",
                null, 7);
    }

    @Test
    public void 비밀번호_일치확인() {
        Assert.assertFalse(ccc.PasswordChecker(groupContent, "1234567"));
        Assert.assertFalse(ccc.PasswordChecker(groupContent, "12345678"));
        Assert.assertTrue(ccc.PasswordChecker(groupContent, "123456789"));
    }

    @Test
    public void 최대인원_초과여부() {
        // 그룹의 최대 인원은 7명이다. 이 수를 넘으면 false가 반환되어야 함.
        Assert.assertTrue(ccc.isAvailable(groupContent, 6));
        Assert.assertTrue(ccc.isAvailable(groupContent, 4));
        Assert.assertTrue(ccc.isAvailable(groupContent, 2));
        Assert.assertFalse(ccc.isAvailable(groupContent, 8));
        Assert.assertFalse(ccc.isAvailable(groupContent, 9));
    }
}
