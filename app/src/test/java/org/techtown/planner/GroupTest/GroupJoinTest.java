package org.techtown.planner.GroupTest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.techtown.planner.domain.Group.GroupContent;
import org.techtown.planner.service.ConditionCheckingCompliation;

import java.util.ArrayList;

public class GroupJoinTest {

    ConditionCheckingCompliation ccc;
    GroupContent groupContent;

    @Before
    public void BeforeTest() {
        ccc = new ConditionCheckingCompliation();
        ArrayList<String> userList = new ArrayList<>();
        for(int i=1; i<=7; i++) {
            userList.add(String.valueOf(i));
        }
        groupContent = new GroupContent("테스트용 그룹", "masterID", "123456789",
                userList, 7);
    }

    @Test
    public void Checking_password() {
        Assert.assertFalse(ccc.PasswordChecker(groupContent, "1234567"));
        Assert.assertFalse(ccc.PasswordChecker(groupContent, "12345678"));
        Assert.assertTrue(ccc.PasswordChecker(groupContent, "123456789"));
    }

    @Test
    public void Maximum_checking() {
        // 그룹의 최대 인원은 7명이다. 이 수를 넘으면 false가 반환되어야 함.
        Assert.assertTrue(ccc.isAvailable(groupContent, 6));
        Assert.assertTrue(ccc.isAvailable(groupContent, 4));
        Assert.assertTrue(ccc.isAvailable(groupContent, 2));
        Assert.assertFalse(ccc.isAvailable(groupContent, 8));
        Assert.assertFalse(ccc.isAvailable(groupContent, 9));
    }

    @Test
    public void duplicate_participation_checking() {
        // 방에는 userID가 1부터 7까지인 사람들이 참여하고 있다.
        // 이미 참여한 사랃믈의 id를 입력받으면 true가 반환되어야 한다.
        Assert.assertTrue(ccc.already_joined(groupContent, "1"));
        Assert.assertTrue(ccc.already_joined(groupContent, "2"));
        Assert.assertTrue(ccc.already_joined(groupContent, "3"));
        Assert.assertTrue(ccc.already_joined(groupContent, "4"));
        Assert.assertTrue(ccc.already_joined(groupContent, "5"));
        Assert.assertTrue(ccc.already_joined(groupContent, "6"));
        Assert.assertTrue(ccc.already_joined(groupContent, "7"));

        // 참여하지 않은 사람에 대해서는 false를 반환해야 한다.
        Assert.assertFalse(ccc.already_joined(groupContent, "8"));
    }
}
