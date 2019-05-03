package com.nlapin.youthsongs.ui.settingsscreen;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.nlapin.youthsongs.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    private SettingsViewModel mViewModel;

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.pref_screen, rootKey);
    }

}
