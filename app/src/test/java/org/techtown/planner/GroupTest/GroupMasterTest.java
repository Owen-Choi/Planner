package org.techtown.planner.GroupTest;

import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.techtown.planner.domain.Group.GroupContent;
import org.techtown.planner.service.ConditionCheckingCompliation;

public class GroupMasterTest {

    ConditionCheckingCompliation ccc;
    GroupContent groupContent;

    @Before
    public void BeforeTest() {
        ccc = new ConditionCheckingCompliation();
        groupContent = new GroupContent("테스트용 그룹", "masterID", "123456789",
                null, 7);
    }

    @Test
    public void 그룹_생성_조건확인() {
        // 조건이 필터링을 잘 하는가
        Assert.assertFalse(ccc.CheckCondition("그룹", 3, "1234"));
        Assert.assertFalse(ccc.CheckCondition("그룹", 10, "1234"));
        Assert.assertFalse(ccc.CheckCondition("그룹", 3, "123"));
        // 조건을 통과해야하는 데이터가 통과하지 못하지는 않는가.
        Assert.assertTrue(ccc.CheckCondition("세글자", 5, "123456aa"));
        Assert.assertTrue(ccc.CheckCondition("다섯글자", 4, "2580as"));
    }

    @Test
    public void 일정픽스_권한확인() {
        Assert.assertFalse(ccc.permissionCheck(groupContent, "memberID"));
        Assert.assertTrue(ccc.permissionCheck(groupContent, "masterID"));
    }

}
