package com.github.dynamic.threadpool.config.notify;

import com.github.dynamic.threadpool.config.notify.listener.Subscriber;
import com.github.dynamic.threadpool.config.event.Event;

/**
 * Event publisher.
 *
 * @author chen.ma
 * @date 2021/6/23 18:58
 */
public interface EventPublisher {

    /**
     * Init.
     *
     * @param type
     * @param bufferSize
     */
    void init(Class<? extends Event> type, int bufferSize);

    /**
     * Add subscriber.
     *
     * @param subscriber
     */
    void addSubscriber(Subscriber subscriber);

    /**
     * Publish.
     *
     * @param event
     * @return
     */
    boolean publish(Event event);

    /**
     * Notify subscriber.
     *
     * @param subscriber
     * @param event
     */
    void notifySubscriber(Subscriber subscriber, Event event);

}
