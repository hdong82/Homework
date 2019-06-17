package com.leo.test.bookshelf.utils;

import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import timber.log.Timber;

/**
 * Created by leo on 2016. 6. 16..
 * @author leo
 */
public final class EventProvider {
    public static class DuplicateRegisterException extends RuntimeException {
        public DuplicateRegisterException(String messgae) {
            super(messgae);
        }
    }

    public interface OnEventListener<T> {
        void onEvent(String key, T event);
    }

    private final Map<String, List<OnEventListener>> subscriber;
    private final Map<String, Map<String, OnEventListener>> callerMap;
    private final Handler mainHandler;

    private EventProvider() {
        subscriber = new ConcurrentHashMap<>();
        callerMap = new ConcurrentHashMap<>();
        mainHandler = new Handler(Looper.getMainLooper());
    }

    private static class EventBusHolder {
        static final EventProvider instance = new EventProvider();
    }

    public static EventProvider getInstance() {
        return EventBusHolder.instance;
    }

    public void register(Class<?> cls, String[] keys, OnEventListener listener) {
        for (String key : keys) {
            register(cls, key, listener);
        }
    }

    public void register(Class<?> cls, String key, OnEventListener listener) {
        List<OnEventListener> listeners = subscriber.get(key);
        if (listeners == null) {
            listeners = new ArrayList<>();
        }

        Map<String, OnEventListener> map = callerMap.get(cls.getSimpleName());
        if (map == null) {
            map = new ConcurrentHashMap<>();
        }

        listeners.add(listener);
        map.put(key, listener);

        subscriber.put(key, listeners);
        callerMap.put(cls.getSimpleName(), map);
    }

    public void unRegister(Class<?> cls) {
        Map<String, OnEventListener> map = callerMap.get(cls.getSimpleName());
        if (map == null) {
            return;
        }

        for (String key : map.keySet()) {
            List<OnEventListener> listeners = subscriber.get(key);
            if (listeners == null) {
                // unregister를 여러번 호출할 경우 발생가능.
                continue;
            }

            listeners.remove(map.get(key));
            if (listeners.size() <= 0) {
                subscriber.remove(key);
            }
        }
        callerMap.remove(cls.getSimpleName());
    }

    public synchronized void notify(final String key) {
        notify(key, null);
    }

    public synchronized void notify(final String key, final long delay) {
        notify(key, null, delay);
    }

    public synchronized <T> void notify(final String key, final T event) {
        notify(key, event, 0);
    }

    public synchronized <T> void notify(final String key, final T event, final long delay) {
        mainHandler.postDelayed(() -> {
            if (subscriber != null) {
                List<OnEventListener> listeners = subscriber.get(key);
                if (listeners != null) {
                    List<OnEventListener> copy = new ArrayList<>();
                    copy.addAll(listeners);
                    for (OnEventListener listener : copy) {
                        Timber.d("++ notify : %s", key);
                        listener.onEvent(key, event);
                    }
                }
            }
        }, delay);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("KEY : [");
        for (String key : subscriber.keySet()) {
            builder.append(key).append(" ");
        }
        builder.append("]");
        return builder.toString();
    }
}
