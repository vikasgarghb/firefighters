package com.vgarg.firefighters;

import com.vgarg.api.CityNode;
import com.vgarg.api.Firefighter;

public class FirefighterImpl implements Firefighter {

  private CityNode currentLocation;
  private int distanceCovered;

  FirefighterImpl(CityNode fireStation) {
    this.currentLocation = fireStation;
    this.distanceCovered = 0;
  }

  @Override
  public CityNode getLocation() {
    return this.currentLocation;
  }

  @Override
  public int distanceTraveled() {
    return this.distanceCovered;
  }

  void setCurrentLocation(CityNode location) {
    this.currentLocation = location;
  }

  void updateDistance(int distance) {
    this.distanceCovered += distance;
  }
}
