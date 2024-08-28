package lab10.first;

import java.util.EventObject;

public interface Observable {
    void addSubscriber(Observer subscriber);

    default void removeSubscriber(Observer subscriber) {
        throw new UnsupportedOperationException("remove");
    }

    void notifySubscribers(EventObject object);
}
