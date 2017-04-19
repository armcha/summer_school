package com.luseen.yandexsummerschool.model.event_bus_events;

import com.luseen.yandexsummerschool.model.History;

/**
 * Created by Chatikyan on 15.04.2017.
 */

public class FromHistoryOrFavouriteEvent {

    private final History history;

    public FromHistoryOrFavouriteEvent(History history) {
        this.history = history;
    }

    public History getHistory() {
        return history;
    }
}
