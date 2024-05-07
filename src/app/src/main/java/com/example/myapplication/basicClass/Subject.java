package com.example.myapplication.basicClass;

public interface Subject<T> {
    // Method to attach an observer to the subject
    public void attach(Observer observer);
    // Method to detach an observer from the subject
    public void detach(Observer observer);
    // Method to notify all observers about changes with a specific classifier
    public void notifyObservers(T classifier);
}