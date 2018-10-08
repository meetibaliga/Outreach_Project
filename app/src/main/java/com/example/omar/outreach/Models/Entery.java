package com.example.omar.outreach.Models;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Entery Class : This class is responsible for deling with user status entries. Every time the user creates a new enter  a new object of type entery is instantiated and stored in the user database.
 */

public class Entery {

    private String userId;
    private Timestamp timestamp;
    private ArrayList<Emotion> emotions;
    private Activity activity;
    private Places places;
    private AcceptanceScale noise;
    private AcceptanceScale odor;
    private AcceptanceScale active;
    private AcceptanceScale transportation;
    private Boolean _asthmaAttack;
    private Boolean _asthmaMedication;
    private Boolean _cough;
    private Boolean _limitedActivities;


    public Entery(String userId, Timestamp timestamp, ArrayList<Emotion> emotions, Activity activity, Places places, AcceptanceScale noise, AcceptanceScale odor, AcceptanceScale active, AcceptanceScale transportation) {
        this.userId = userId;
        this.timestamp = timestamp;
        this.emotions = emotions;
        this.activity = activity;
        this.places = places;
        this.noise = noise;
        this.odor = odor;
        this.active = active;
        this.transportation = transportation;
    }

    // Getters

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public ArrayList<Emotion> getEmotions() {
        return emotions;
    }

    public Activity getActivity() {
        return activity;
    }

    public Places getLocation() {
        return places;
    }

    public String getUserId() {
        return userId;
    }

    public AcceptanceScale getNoise() {
        return noise;
    }

    public AcceptanceScale getOdor() {
        return odor;
    }

    public AcceptanceScale getActive() {
        return active;
    }

    public AcceptanceScale getTransportation() {
        return transportation;
    }

    // Setters

    public void setEmotions(ArrayList<Emotion> emotions) {
        this.emotions = emotions;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setLocation(Places location) {
        this.places = location;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public void setNoise(AcceptanceScale noise) {
        this.noise = noise;
    }

    public void setOdor(AcceptanceScale odor) {
        this.odor = odor;
    }

    public void setActive(AcceptanceScale active) {
        this.active = active;
    }

    public void setTransportation(AcceptanceScale transportation) {
        this.transportation = transportation;
    }

    // Methods

    @Override
    public String toString() {
        return "Entery{" +
                "userId='" + userId + '\'' +
                ", timestamp=" + timestamp +
                ", emotions=" + emotions +
                ", activity=" + activity +
                ", location=" + places +
                ", noise=" + noise +
                ", odor=" + odor +
                ", active=" + active +
                ", transportation=" + transportation +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entery entery = (Entery) o;
        return Objects.equals(userId, entery.userId) &&
                Objects.equals(timestamp, entery.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(emotions, activity, places);
    }
}
