package chopstick.adapter;

import android.content.SharedPreferences;

import java.util.Set;

import chopstick.Chopstick;

/**
 * Created by San on 4/11/16.
 */
public class SharedPreferencesAdapter implements Chopstick.Adapter {
    private final SharedPreferences sharedPreferences;

    public SharedPreferencesAdapter(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override public <T> void set(String key, T value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Class<?> clazz = value.getClass();
        if (clazz == Boolean.class) {
            editor.putBoolean(key, (Boolean) value);
        } else if (clazz == Float.class) {
            editor.putFloat(key, (Float) value);
        } else if (clazz == Integer.class) {
            editor.putInt(key, (Integer) value);
        } else if (clazz == Long.class) {
            editor.putLong(key, (Long) value);
        } else if (clazz == String.class) {
            editor.putString(key, (String) value);
        } else if (clazz == Set.class) {
            editor.putStringSet(key, (Set<String>) value);
        }
        editor.commit();
    }

    @SuppressWarnings("unchecked")
    @Override public <T> T get(Class<T> type, String key) {
        if (type == boolean.class || type == Boolean.class) {
            return (T) Boolean.valueOf(sharedPreferences.getBoolean(key, false));
        } else if (type == float.class || type == Float.class) {
            return (T) Float.valueOf(sharedPreferences.getFloat(key, 0f));
        } else if (type == int.class || type == Integer.class) {
            return (T) Integer.valueOf(sharedPreferences.getInt(key, 0));
        } else if (type == long.class || type == Long.class) {
            return (T) Long.valueOf(sharedPreferences.getLong(key, 0L));
        } else if (type == String.class) {
            return (T) sharedPreferences.getString(key, null);
        } else if (type == Set.class) {
            return (T) sharedPreferences.getStringSet(key, null);
        } else {
            throw new IllegalArgumentException();
        }
    }
}
