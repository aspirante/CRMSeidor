package com.crystalis.settings;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.crystalis.view.R;

public class SettingsPreference extends PreferenceActivity {
	
@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.settingspreferences);

    }
}
