package org.taosha.playground.chopstick;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import chopstick.Chopstick;


public class MainActivity extends BaseActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private TextView poemView;

    private SharedPreferences sharedPreferences;
    private InterfaceSettings interfaceSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        poemView = (TextView) findViewById(R.id.poem);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        interfaceSettings = Chopstick.from(sharedPreferences).get(InterfaceSettings.class);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        int fontSize = interfaceSettings.fontSize();
        if (fontSize == 0) {
            fontSize = 14;
        }
        poemView.setTextSize(getResources().getDisplayMetrics().density * fontSize);
    }
}
