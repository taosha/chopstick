package chopstick;

/**
 * Represents a facade of an observable {@code int} value.
 *
 * @see Facade
 * <p>
 * Created by San on 4/12/16.
 */
public interface IntFacade {
    /**
     * @see Facade#get()
     */
    int get();

    /**
     * @see Facade#get(Object)
     */
    int get(int defaultValue);

    /**
     * @see Facade#set(Object)
     */
    void set(int value);

    /**
     * @see Facade#rxObservable()
     */
    rx.Observable<Integer> rxObservable();

    /**
     * @see Facade#observable()
     */
    java.util.Observable observable();
}
