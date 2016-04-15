package chopstick;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by San on 4/9/16.
 */
class Handler implements InvocationHandler {

    private final Chopstick.Adapter adapter;

    Handler(Chopstick.Adapter adapter) {
        this.adapter = adapter;
    }

    @Override public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // TODO: Implement this
        return null;
    }

}
