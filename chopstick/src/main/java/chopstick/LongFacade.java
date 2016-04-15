package chopstick;

/**
 * Represents a facade of an observable {@code long} value.
 *
 * @see Facade
 * <p>
 * Created by San on 4/12/16.
 */
public interface LongFacade {
    /**
     * @see Facade#get()
     */
    long get();

    /**
     * @see Facade#get(Object)
     */
    long get(long defaultValue);

    /**
     * @see Facade#set(Object)
     */
    void set(long value);

    /**
     * @see Facade#rxObservable()
     */
    rx.Observable<Long> rxObservable();

    /**
     * @see Facade#observable()
     */
    java.util.Observable observable();
}
