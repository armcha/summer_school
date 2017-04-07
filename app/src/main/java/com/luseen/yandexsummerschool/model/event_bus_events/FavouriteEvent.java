package com.luseen.yandexsummerschool.model.event_bus_events;

/**
 * Created by Chatikyan on 07.04.2017.
 */

public class FavouriteEvent {

    private boolean isFavourite;
    private String identifier;

    public FavouriteEvent(boolean isFavourite, String identifier) {
        this.isFavourite = isFavourite;
        this.identifier = identifier;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
