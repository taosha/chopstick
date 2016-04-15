package org.taosha.playground.chopstick;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.Locale;

import chopstick.Chopstick;
import chopstick.Facade;
import chopstick.Name;
import rx.functions.Action1;

/**
 * Created by San on 4/11/16.
 */
public class BaseActivity extends AppCompatActivity {
    private static final String TAG = "Base";

    @Name("language") Facade<String> language;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Chopstick.from(sharedPreferences).inject(this);

        language.rxObservable().subscribe(new Action1<String>() {
            @Override public void call(String value) {
                if (value != null) {
                    getBaseContext().getResources().getConfiguration().locale = new Locale(value);
                }
            }
        });
    }

}
