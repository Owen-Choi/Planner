package org.techtown.planner.service.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.techtown.planner.R;
import org.techtown.planner.domain.Group.GroupContent;
import org.techtown.planner.service.activities.EachGroupActivity;
import org.techtown.planner.service.activities.GroupAddActivity;

import java.util.ArrayList;
import java.util.Objects;

public class GroupFragment extends Fragment {


    public int INDEX;
    String TAG = "GroupFragment";
    private ArrayList<GroupContent> groupList;

    private FirebaseUser curUser = FirebaseAuth.getInstance().getCurrentUser();
    final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private GroupAdapter adapter;

    // 그룹 추가 floating button, 철웅 추가
    FloatingActionButton add;

    ViewGroup rootView;

    public GroupFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView= (ViewGroup) inflater.inflate(R.layout.fragment_group , container, false);

        groupList = new ArrayList<>();

        ListView listView= (ListView) rootView.findViewById(R.id.list);

        adapter = new GroupAdapter();
        listView.setAdapter(adapter);

        setHasOptionsMenu(true);

        // 그룹 데이터베이스 변경됨. Group -> 방장 Uid -> My_Group(얘를 굳이 한번 더 넣은 이유는 collection은 삭제할 수 없지만
        // document는 삭제할 수 있기 때문에) -> GroupName 의 구조임.
        // 그래서 My_Group으로 CollectionGroup 쿼리 날려서 그룹 정보 다 가져오는 원리.
        db.collectionGroup("My_Group").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        GroupContent tempContent = document.toObject(GroupContent.class);
                        // 가입하지 않은 그룹만 보여준다.
                        if(!already_joined(tempContent.getUserList()))
                            adapter.addItem(tempContent);
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });


        //
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                INDEX = index;
                //커스텀 dialog 로 바꿈
                CustomDialog2 dlg_pw = new CustomDialog2(getContext());
                dlg_pw.show();

                //그룹 들어가기 전 pw 입력받기
//                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
//
//                alert.setTitle("Input Group pw");
//                alert.setMessage("패스워드를 입력해주세요.");
//
//                final EditText pw = new EditText(getContext());
//                alert.setView(pw);
//
//                alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        String g_pw = pw.getText().toString();
//                        find_and_update_db((GroupContent) adapter.getItem(index), g_pw, index);
////                        StartActivity(EachGroupActivity.class,index);
//                    }
//                });
//                alert.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        StartToast("취소되었습니다.");
//                    }
//                });
//                alert.show();
            }
        });
        // floating button 관련, 철웅 추가
        add = (FloatingActionButton)rootView.findViewById(R.id.add_group_floating_button);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogManager();
            }
        });

        return rootView;
    }

    class CustomDialog2 extends Dialog {

        private Context mContext;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.custom_dialog_group_pw);

            // 다이얼로그의 배경을 투명으로 만든다.
            Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            // 커스텀 다이얼로그의 각 위젯들을 정의한다.
            Button saveButton = findViewById(R.id.btnSave_pw);
            Button cancelButton = findViewById(R.id.btnCancel_pw);
            EditText pw = findViewById(R.id.put_text);

            // 버튼 리스너 설정
            saveButton.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // '확인' 버튼 클릭시
                    // ...코드..
                    String g_pw = pw.getText().toString();
                    find_and_update_db((GroupContent) adapter.getItem(INDEX), g_pw, INDEX);
//                    StartActivity(EachGroupActivity.class, INDEX);                    // Custom Dialog 종료
                    dismiss();
                }
            });
            cancelButton.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // '취소' 버튼 클릭시
                    // ...코드..
                    Toast.makeText(getActivity(), "취소하셨습니다.", Toast.LENGTH_SHORT).show();
                    // Custom Dialog 종료
                    dismiss();
                }
            });

        }

        public CustomDialog2(Context mContext) {
            super(mContext);
            this.mContext = mContext;
        }

    }


    @Override
    public void onResume() {
        adapter.notifyDataSetChanged();
        Log.e(TAG, " : " + adapter.getCount());
        super.onResume();
    }

    private void StartActivity(Class<EachGroupActivity> eachGroupFragmentClass, int i, GroupContent groupInfo) {
        Intent intent= new Intent(getContext(),eachGroupFragmentClass);
        intent.putExtra("i",i+1);
        intent.putExtra("groupContent", groupInfo);
        startActivity(intent);
    }

    private void StartActivity(Class c) {
        Intent intent = new Intent(getContext(), c);
        // CLEAR_TOP이 없으면 기존 액티비티 스택이 제거가 되지 않아서, DB에서 일정을 가져오서 시간표를 표시하는
        // OnCreateView가 다시 호출되지 않는다.그렇다고 onResume()을 쓰면 시간표를 누를때마다 색이 변함.
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    // 그룹 생성 관련 dialog, 철웅 추가
    private void DialogManager() {
        CustomDialog dlg_ng = new CustomDialog(getContext());
        dlg_ng.show();
//        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity())
//                .setTitle("Timetable Clear")
//                .setMessage("새 그룹을 만드시겠습니까?")
//                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        StartActivity(GroupAddActivity.class);
//                    }
//                })
//                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        Toast.makeText(getActivity(), "취소하셨습니다.", Toast.LENGTH_SHORT).show();
//                    }
//                });
//        android.app.AlertDialog dialog = builder.create();
//        dialog.setCanceledOnTouchOutside(true);
//        dialog.show();
    }

    class CustomDialog extends Dialog {

        private Context mContext;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.custom_dialog_new_group);

            // 다이얼로그의 배경을 투명으로 만든다.
            Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            // 커스텀 다이얼로그의 각 위젯들을 정의한다.
            Button saveButton = findViewById(R.id.btnSave_ng);
            Button cancelButton = findViewById(R.id.btnCancel_ng);

            // 버튼 리스너 설정
            saveButton.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // '확인' 버튼 클릭시
                    // ...코드..
                    StartActivity(GroupAddActivity.class);
                    // Custom Dialog 종료
                    dismiss();
                }
            });
            cancelButton.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // '취소' 버튼 클릭시
                    // ...코드..
                    Toast.makeText(getActivity(), "취소하셨습니다.", Toast.LENGTH_SHORT).show();
                    // Custom Dialog 종료
                    dismiss();
                }
            });

        }

        public CustomDialog(Context mContext) {
            super(mContext);
            this.mContext = mContext;
        }

    }

    private void StartToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    // 패스워드 체킹, 데이터베이스 검색 및 업데이트, 그룹 참가  기능을 모두 수행하는 함수, 철웅 추가
    private void find_and_update_db(GroupContent GroupContentFromListView, String Password, int index) {
        // 좀 비효율적이지만 달리 다른 방법이 떠오르지 않음.
        // 컬렉션 그룹 하위의 모든 데이터들을 일일이 찾는 원리.
        db.collectionGroup("My_Group").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for (QueryDocumentSnapshot  document : task.getResult()) {
                        GroupContent tempContent = document.toObject(GroupContent.class);
                        // 방 이름이랑 방장 ID가 같다면 그룹을 찾은 것으로 간주.
                        // 따라서 방장은 같은 이름의 방을 2개 이상 만들어서는 안된다.
                        if(tempContent.getGname().equals(GroupContentFromListView.getGname())
                        && tempContent.getMasterID().equals(GroupContentFromListView.getMasterID())) {
                            // 찾았으면 비밀번호 체킹 후 본인 추가된 데이터로 업데이트, 그 후에 화면 전환.
                            if(PasswordChecker(tempContent, Password)) {
                                // 비밀번호 일치, DB 업데이트 후 화면 전환
                                ArrayList<String> ForChecking = tempContent.getUserList();
                                // 이미 가입한 그룹인지 확인한다.
                                if(already_joined(ForChecking)) {
                                    // 이미 가입한 그룹이다.
                                    // 이 경우 별도의 데이터베이스 업데이트 없이 바로 그룹 액티비티 호출하면 되겠다.
                                    Log.e(TAG, "이미 가입한 그룹, 데이터베이스 업데이트 생략");
                                    StartActivity(EachGroupActivity.class,index, tempContent);
                                    return;
                                }
                                // 최대 인원수를 넘지 않았는지 확인
                                if(!isAvailable(tempContent))
                                    return;
                                ArrayList<String> UpdatedList = ForChecking;
                                GroupContent newGroupContent =
                                        new GroupContent(
                                                GroupContentFromListView.getGname(),
                                                GroupContentFromListView.getMasterID(),
                                                GroupContentFromListView.getGroupPassword(),
                                                UpdatedList,
                                                GroupContentFromListView.getMaxNum());
                                UpdatedList.add(curUser.getUid());
                                DocumentReference reference = document.getReference();
                                reference.set(newGroupContent);
                                StartActivity(EachGroupActivity.class,index, tempContent);
                            } else {
                                // 비밀번호 불일치
                                StartToast("비밀번호가 틀렸습니다.");
                            }
                        }
                    }
                }
            }
        });
    }

    private boolean already_joined(ArrayList<String> userList) {
        String myUid = curUser.getUid();
        for (String memberUid : userList) {
            if(myUid.equals(memberUid))
                // 이미 가입한 그룹.
                return true;
        }
        return false;
    }

    private boolean isAvailable(GroupContent temp) {
        Log.e(TAG, "isAvailable: " + temp.getUserList().size());
        if(temp.getUserList().size() <
                temp.getMaxNum()) {
            return true;
        }
        StartToast("그룹이 꽉 찼습니다");
        return false;
    }

    private boolean PasswordChecker(GroupContent fromDB, String password) {
        if(!fromDB.getGroupPassword().equals(password))
            return false;
        return true;
    }















//    class CustomDialog extends Dialog {
//
//        private Context mContext;
//
//        @Override
//        protected void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//
//            if(INDEX == -1){
//                Log.d("NG", "새 그룹 만들기");
//                setContentView(R.layout.custom_dialog_group_pw);
//
//                // 다이얼로그의 배경을 투명으로 만든다.
//                Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//                Button saveButton_ng = rootView.findViewById(R.id.btnSave_ng);
//                Button cancelButton_ng = rootView.findViewById(R.id.btnCancel_ng);
//
//                // new group 버튼 리스너
//                // 버튼 리스너 설정
//                saveButton_ng.setOnClickListener(new Button.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        // '확인' 버튼 클릭시
//                        // ...코드..
//                        StartActivity(GroupAddActivity.class);
//                        // Custom Dialog 종료
//                        dismiss();
//                    }
//                });
//                cancelButton_ng.setOnClickListener(new Button.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        // '취소' 버튼 클릭시
//                        // ...코드..
//                        Toast.makeText(getActivity(), "취소하셨습니다.", Toast.LENGTH_SHORT).show();
//                        // Custom Dialog 종료
//                        dismiss();
//
//                    }
//                });
//            }
//            else{
//                Log.d("PW", "그룹 비밀번호 입력");
//                setContentView(R.layout.custom_dialog_new_group);
//
//                EditText pw = rootView.findViewById(R.id.put_text);
//
//                // 다이얼로그의 배경을 투명으로 만든다.
//                Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//                // 커스텀 다이얼로그의 각 위젯들을 정의한다.
//                Button saveButton_pw = rootView.findViewById(R.id.btnSave_pw);
//                Button cancelButton_pw = rootView.findViewById(R.id.btnCancel_pw);
//
//                //pw 버튼 리스너
//                //버튼 리스너
//                saveButton_pw.setOnClickListener(new Button.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        // '확인' 버튼 클릭시
//                        // ...코드..
//                        String g_pw = pw.getText().toString();
//                        find_and_update_db((GroupContent) adapter.getItem(INDEX), g_pw, INDEX);
//                        // Custom Dialog 종료
//                        dismiss();
//                    }
//                });
//                cancelButton_pw.setOnClickListener(new Button.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        // '취소' 버튼 클릭시
//                        // ...코드..
//                        Toast.makeText(getActivity(), "취소되었습니다.", Toast.LENGTH_SHORT).show();
//                        // Custom Dialog 종료
//                        dismiss();
//
//                    }
//                });
//
//            }
//        }
//
//        public CustomDialog(Context mContext) {
//
//            super(mContext);
//            Log.d("CustomDialog", "들어옴");
//            this.mContext = mContext;
//        }
//    }

}

