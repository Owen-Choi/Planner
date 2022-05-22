package org.techtown.planner.service.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import org.techtown.planner.R;

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


    private void StartActivity(Class c) {
        Intent intent = new Intent(getActivity(), c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
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
                return false;
            }
        });

        change_pw.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(@NonNull Preference preference) {
                return false;
            }
        });

        withdrawal.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(@NonNull Preference preference) {
                return false;
            }
        });

        sign_out.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(@NonNull Preference preference) {
                return false;
            }
        });
    }
}