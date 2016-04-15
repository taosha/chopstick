package org.taosha.playground.chopstick;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import chopstick.Chopstick;
import chopstick.Facade;
import chopstick.Name;

/**
 * Created by San on 4/12/16.
 */
public class x {

    public class MainActivity extends Activity {

        @Name("lang") Facade<String> language;

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Chopstick.from(getPreferences(MODE_PRIVATE)).inject(this);
            // Now the field language has a value
            assert language != null;
        }
    }
}
