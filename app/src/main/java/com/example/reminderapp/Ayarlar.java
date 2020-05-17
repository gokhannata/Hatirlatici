package com.example.reminderapp;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Ayarlar extends Fragment{

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //addPreferencesFromResource(R.xml.preferences);


    }
    public static class MainSettingsFragment extends PreferenceFragment{
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
            bindSummaryValue(findPreference("key_full_name"));
            bindSummaryValue(findPreference("key_email"));
            bindSummaryValue(findPreference("key_sleep_timer"));
            bindSummaryValue(findPreference("key_music_quality"));
            bindSummaryValue(findPreference("key_notification_ringtone"));
        }
    }

    private static void bindSummaryValue(Preference preference){
        preference.setOnPreferenceChangeListener(listener);
        listener.onPreferenceChange(preference, PreferenceManager.getDefaultSharedPreferences
                (preference.getContext()).getString(preference.getKey(),""));
    }

    private static Preference.OnPreferenceChangeListener listener=new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String stringValue=newValue.toString();
            if(preference instanceof ListPreference){
                ListPreference listPreference=(ListPreference)preference;
                int index=listPreference.findIndexOfValue(stringValue);
                //Set the summary to reflect the new value
                preference.setSummary(index>0 ? listPreference.getEntries()[index]:null);
            }
            else if(preference instanceof EditTextPreference){
                preference.setSummary(stringValue);
            }
            else if(preference instanceof RingtonePreference){
                if(TextUtils.isEmpty(stringValue)){
                    //No ringtone
                    preference.setSummary("Silent");
                }
                else{
                    Ringtone ringtone= RingtoneManager.getRingtone(preference.getContext(), Uri.parse(stringValue));
                    if(ringtone==null){
                        //Clear to summary
                        preference.setSummary("Choose notification ringtone");
                    }
                    else{
                        String name=ringtone.getTitle(preference.getContext());
                        preference.setSummary(name);
                    }
                }
            }
            return true;
        }
    };
}
