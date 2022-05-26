package org.techtown.planner.GroupTest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.techtown.planner.service.ConditionCheckingCompliation;

public class GroupTest {

    ConditionCheckingCompliation ccc;

    @Before
    public void BeforeTest() {
        ccc = new ConditionCheckingCompliation();
    }

    @Test
    public void 조건확인() {
        // 조건이 필터링을 잘 하는가
        Assert.assertFalse(ccc.CheckCondition("그룹", 3, "1234"));
        Assert.assertFalse(ccc.CheckCondition("그룹", 10, "1234"));
        Assert.assertFalse(ccc.CheckCondition("그룹", 3, "123"));
        // 조건을 통과해야하는 데이터가 통과하지 못하지는 않는가.
        Assert.assertTrue(ccc.CheckCondition("세글자", 5, "123456aa"));
    }

}
