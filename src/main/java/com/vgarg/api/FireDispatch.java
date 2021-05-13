package com.vgarg.api;

import java.util.List;

public interface FireDispatch {

  /**
   * Hires a number of com.vgarg.firefighters
   * @param numFirefighters
   */
  void setFirefighters(int numFirefighters);

  /**
   * Get the list of com.vgarg.firefighters
   * @return
   */
  List<Firefighter> getFirefighters();

  /**
   * The FireDispatch will be notified of burning buildings via this method. It will then dispatch the
   * com.vgarg.firefighters and extinguish the fires. We want to optimize for total distance traveled by all com.vgarg.firefighters
   * @param burningBuildings list of locations with burning buildings
   */
  void dispatchFirefighters(CityNode... burningBuildings);
}
