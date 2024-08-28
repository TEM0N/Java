package lab10.first;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

public class KeyManager implements Observable {
    private final List<Observer> subscribers;

    public static KeyManager newInstance() {
        return new KeyManager();
    }

    private KeyManager() {
        subscribers = new ArrayList<>();
    }

    @Override
    public void addSubscriber(Observer subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public void notifySubscribers(EventObject object) {
        for (Observer subscriber : subscribers) {
            subscriber.update(object);
        }
    }
}
