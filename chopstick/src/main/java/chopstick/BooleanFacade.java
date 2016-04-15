package chopstick;

/**
 * Represents a facade of an observable {@code boolean} value.
 *
 * @see Facade
 * <p>
 * Created by San on 4/12/16.
 */
public interface BooleanFacade {

    /**
     * @see Facade#get()
     */
    boolean get();

    /**
     * @see Facade#get(Object)
     */
    boolean get(boolean defaultValue);

    /**
     * @see Facade#set(Object)
     */
    void set(boolean value);

    /**
     * @see Facade#rxObservable()
     */
    rx.Observable<Boolean> rxObservable();

    /**
     * @see Facade#observable()
     */
    java.util.Observable observable();
}
