package com.example.rob.try3;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.SwitchPreference;
import android.support.annotation.Nullable;

public class PrefActivity extends PreferenceActivity {

    SwitchPreference switchPreference;
    ListPreference listPreference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref);

        switchPreference = (SwitchPreference)findPreference("switch_preference_notif");
        listPreference = (ListPreference)findPreference("list_preference_language");

    }
}
