package chopstick;

import android.content.SharedPreferences;

import org.taosha.chopstick.Injector;

import java.lang.reflect.Constructor;
import java.lang.reflect.Proxy;

import chopstick.adapter.SharedPreferencesAdapter;

/**
 * Created by San on 4/9/16.
 */
public class Chopstick {

    private final Handler handler;

    /**
     * Creates a {@code Chopstick} instance from a {@code SharedPreferences}.
     *
     * @param sharedPreferences
     * @return a {@code Chopstick} instance
     */
    public static Chopstick from(SharedPreferences sharedPreferences) {
        return Chopstick.from(new SharedPreferencesAdapter(sharedPreferences));
    }

    /**
     * Creates a {@code Chopstick} instance from an adapter.
     *
     * @param adapter
     * @return a {@code Chopstick} instance
     */
    public static Chopstick from(Chopstick.Adapter adapter) {
        return new Chopstick(adapter);
    }

    private Chopstick(Adapter adapter) {
        this.handler = new Handler(adapter);
    }

    /**
     * Retrieves an instance of a facade interface. The facade interface's type is specified by {@code type}.
     *
     * @param type the facade interface type
     * @param <T>  represents a facade interface type
     * @return an instance of the facade interface.
     * @throws IllegalArgumentException if {@code type} is not a facade interface type
     * @see Facade
     */
    @SuppressWarnings("unchecked")
    public <T> T get(Class<T> type) {
        // TODO: Validation
        return (T) Proxy.newProxyInstance(type.getClassLoader(), new Class<?>[]{type}, handler);
    }

    /**
     * Injects facade fields. An example for facade fields injection is:
     * <pre>
     * public class MainActivity extends Activity {
     *
     *     &#064;Name("lang") Facade&lt;String> language;
     *
     *     &#064;Override
     *     protected void onCreate(@Nullable Bundle savedInstanceState) {
     *         super.onCreate(savedInstanceState);
     *         Chopstick.from(getPreference(MOD_PRIVATE)).inject(this);
     *         // Now the field language has a value
     *         assert language != null;
     *     }
     * }
     * </pre>
     *
     * @param target the object to inject
     * @throws IllegalArgumentException if {@code target} class has no facade field declared
     * @see Facade
     * @see IntFacade
     * @see FloatFacade
     * @see LongFacade
     * @see BooleanFacade
     */
    @SuppressWarnings("unchecked")
    public <T> void inject(T target) {
        if (target == null)
            throw new NullPointerException("target cannot be null");

        findInjector((Class<T>) target.getClass()).inject(target);
    }

    @SuppressWarnings("unchecked")
    private <T> Injector<T> findInjector(Class<T> targetClass) {
        final String injectorClassName = targetClass.getCanonicalName() + "ChopstickInjector";
        try {
            Class<? extends Injector<T>> injectorClass = (Class<? extends Injector<T>>) Class.forName(injectorClassName);
            Constructor<? extends Injector<T>> constructor = injectorClass.getDeclaredConstructor(Chopstick.class);
            return constructor.newInstance(this);
        } catch (Throwable e) {
            throw new RuntimeException("Cannot found injector for " + targetClass, e);
        }
    }

    /**
     * Adapter to get or set value
     */
    public interface Adapter {
        <T> T get(Class<T> type, String name);

        /**
         * Sets value
         *
         * @param value
         * @param <T>
         */
        <T> void set(T value);

        // TODO: Observable design
    }
}
