package chopstick;

/**
 * Represents a facade of an observable {@code float} value.
 *
 * @see Facade
 * <p>
 * Created by San on 4/12/16.
 */
public interface FloatFacade {
    /**
     * @see Facade#get()
     */
    float get();

    /**
     * @see Facade#get(Object)
     */
    float get(float defaultValue);

    /**
     * @see Facade#set(Object)
     */
    void set(float value);

    /**
     * @see Facade#rxObservable()
     */
    rx.Observable<Float> rxObservable();

    /**
     * @see Facade#observable()
     */
    java.util.Observable observable();
}
