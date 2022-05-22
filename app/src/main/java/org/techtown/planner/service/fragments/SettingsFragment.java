package org.techtown.planner.service.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import org.techtown.planner.R;
import org.techtown.planner.service.activities.MainActivity;
import org.techtown.planner.service.activities.MemberInfoActivity;
import org.techtown.planner.service.activities.Password_Init_Activity;

public class SettingsFragment extends PreferenceFragmentCompat {

    Intent our_git;
    Intent license_git;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings_preference, rootKey);

        our_git = new Intent(Intent.ACTION_VIEW);
        our_git.setData(Uri.parse("https://github.com/Owen-Choi"));
        license_git = new Intent(Intent.ACTION_VIEW);
        license_git.setData(Uri.parse("https://github.com/tlaabs/TimetableView"));

        Preference about_us = (Preference)findPreference("About_us");
        Preference License = (Preference)findPreference("License");
        Preference memberInfo = (Preference)findPreference("memberInfo");
        Preference change_pw = (Preference)findPreference("change_pw");
        Preference withdrawal = (Preference)findPreference("withdrawal");
        Preference sign_out = (Preference)findPreference("sign_out");

        set_clickListeners(about_us, License, memberInfo, change_pw, withdrawal, sign_out);
    }


    private void set_clickListeners(Preference about_us, Preference License, Preference memberInfo,
                                    Preference change_pw, Preference withdrawal, Preference sign_out) {
        about_us.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                startActivity(our_git);
                return true;
            }
        });

        License.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                startActivity(license_git);
                return true;
            }
        });

        memberInfo.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(@NonNull Preference preference) {
                StartActivity(MemberInfoActivity.class);
                return true;
            }
        });

        change_pw.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(@NonNull Preference preference) {
                StartActivity(Password_Init_Activity.class);
                return true;
            }
        });

        withdrawal.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(@NonNull Preference preference) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("정말 탈퇴하시겠습니까?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        WithDrawal();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        StartToast("탈퇴 취소");
                    }
                });
                builder.setCancelable(true);
                builder.show();
                return true;
            }
        });

        sign_out.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(@NonNull Preference preference) {
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.signOut();
                StartToast("로그아웃 되었습니다.");
                StartActivity(MainActivity.class);
                return false;
            }
        });
    }

    private void WithDrawal() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.delete();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // 유저 db와 유저가 속한 그룹에서 유저 정보를 지워줘야 한다.
        // 이 부분은 일단 그룹이 구현되면 마저 구현하자.
        StartToast("탈퇴");
    }

    private void StartActivity(Class c) {
        Intent intent = new Intent(getActivity(), c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void StartToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
}