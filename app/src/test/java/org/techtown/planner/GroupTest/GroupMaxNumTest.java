package org.techtown.planner.GroupTest;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.techtown.planner.domain.Group.GroupContent;

public class GroupMaxNumTest {

    static final String testUid = "0TQ0bGUPFRWivskx2Ffs0iujUCb2";
    static int maxNum = 1;
    static int currentSize = 1;

//    @Before
//    public void Before() {
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        db.collectionGroup("Junit Test Group").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if(task.isSuccessful()) {
//                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
//                        GroupContent groupContent = documentSnapshot.toObject(GroupContent.class);
//                        maxNum = groupContent.getMaxNum();
//                        currentSize = groupContent.getUserList().size();
//                        // 테스트용 그룹은 이미 그룹원이 꽉 차있는 상태이다.
//                    }
//                }
//            }
//        });
//    }

    @Test
    public void MaxNumTester() {
        // 최대 인원이 이미 꽉 찬 상태에서는 그룹에 가입할 수 없다.
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collectionGroup("Junit Test Group").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        GroupContent groupContent = documentSnapshot.toObject(GroupContent.class);
                        maxNum = groupContent.getMaxNum();
                        currentSize = groupContent.getUserList().size();
                        // 테스트용 그룹은 이미 그룹원이 꽉 차있는 상태이다.
                    }
                    Assert.assertEquals(maxNum, currentSize);
                }
            }
        });
    }

}
