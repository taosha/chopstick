package org.taosha.playground.chopstick;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v14.preference.PreferenceFragment;
import android.support.v7.preference.Preference;

import chopstick.Chopstick;

/**
 * Created by San on 4/11/16.
 */
public class SettingsActivity extends BaseActivity {

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }

    public static class SettingsFragment extends PreferenceFragment {
        @Override public void onCreatePreferences(Bundle bundle, String s) {
            addPreferencesFromResource(R.xml.interface_settings);

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
            final InterfaceSettings interfaceSettings = Chopstick.from(sharedPreferences).get(InterfaceSettings.class);
            findPreference("interface.font-size").setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override public boolean onPreferenceChange(Preference preference, Object value) {
                    interfaceSettings.fontSize(Integer.parseInt(value.toString()));
                    return true;
                }
            });
        }
    }
}
