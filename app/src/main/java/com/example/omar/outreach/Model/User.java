package com.example.omar.outreach.Model;

import java.util.ArrayList;
import java.util.Objects;

class User {

    private String firstName;
    private ArrayList<Entery> enteries;

    public User(String firstName, ArrayList<Entery> enteries) {
        this.firstName = firstName;
        this.enteries = enteries;
    }

    public String getFirstName() {
        return firstName;
    }

    public ArrayList<Entery> getEnteries() {
        return enteries;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setEnteries(ArrayList<Entery> enteries) {
        this.enteries = enteries;
    }

    public void addEntery(Entery entery){
        enteries.add(entery);
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ",enteries=" + enteries +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(firstName, user.firstName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, enteries);
    }
}
