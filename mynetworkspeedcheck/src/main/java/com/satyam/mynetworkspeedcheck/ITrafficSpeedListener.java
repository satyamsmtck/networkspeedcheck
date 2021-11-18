package com.satyam.mynetworkspeedcheck;

public interface ITrafficSpeedListener {
    void onTrafficSpeedMeasured(double upStream, double downStream);
}
