package chopstick;

/**
 * Represents a facade of an observable value.
 * <p>
 * This interface is intended to declare injectable facade fields from a non-anonymous class
 * or facade methods from a facade interface.
 * <h3>Facade fields</h3>
 * <p>
 * A injectable facade field is of type {@link Facade}, {@link IntFacade}, {@link FloatFacade},
 * {@link LongFacade}, or {@link  BooleanFacade}(facade types) with no access modifier(package accessible) and
 * is annotated with {@code @Name}. An example is:
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
 * <h3>Facade interface and facade methods</h3>
 * <p>
 * A facade interface is an interface which defines only facade methods. A facade method is a method
 * that takes no argument and returns a facade type({@link Facade}, {@link IntFacade},
 * {@link FloatFacade},{@link LongFacade}, or {@link  BooleanFacade}), and is annotated with {@code @Name}.
 * For example:
 * <p>
 * <pre>
 * &#064;Name("interface")
 * public interface InterfaceSettings {
 *     &#064;Name("language") Facade&lt;String> language();
 *     &#064;Name("font-size") IntFacade fontSize();
 * }
 *
 * </pre>
 * </p>
 * A facade interface can be annotated with {@code @Name}. If a facade interface is annotated with a {@code Name} annotation,
 * the value of the annotation should be considered as "scope" of that facade interface.
 * <p>
 * Created by San on 4/12/16.
 */
public interface Facade<T> {

    /**
     * Retrieves current value of the observable value
     *
     * @return current value, or null if not exists
     */
    T get();

    /**
     * Retrieves current value with a default value
     *
     * @param defaultValue value to return when the observable value doesn't exist
     * @return current value, or {@code defaultValue} if the observable value doesn't exist
     */
    T get(T defaultValue);

    /**
     * Updates the observable value. This method can be asynchronous(aka. calling {@code get()}
     * immediately after setting a new value through {@code set()} method may not get the just-set value)
     * depends on implementation.
     *
     * @param value the new value
     */
    void set(T value);

    /**
     * Retrieves a RxJava {@code Observable} to observe value updates.
     * If the observable value exists, the returned {@code Observable} will publish the latest value to the just subscribed observer when an observer subscribes to it.
     * The {@code Observable} will publish to the observer each followed update after subscription.
     */
    rx.Observable<T> rxObservable();

    /**
     * Retrieves a {@code Observable} to observe value updates.
     * When an observer is added to the returned {@code Observable}, it will be notified with the latest value if the observable value exists.
     * The {@code Observable} will notify the observer with each followed update afterwards.
     */
    java.util.Observable observable();
}
