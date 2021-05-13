package com.vgarg.api;

import com.vgarg.api.exceptions.FireproofBuildingException;
import com.vgarg.api.exceptions.NoFireFoundException;

public interface Building {
  /**
   * Get the location of this building
   *
   * @return {@link CityNode} representing the location
   */
  CityNode getLocation();

  /**
   * Find out if the building is currently on fire
   *
   * @return true if the building is burning, otherwise false
   */
  boolean isBurning();

  /**
   * Find out if the building is fireproof
   *
   * @return true if the building is fireproof, otherwise false
   */
  boolean isFireproof();

  /**
   * Extinguish the fire in the building
   *
   * @throws NoFireFoundException if the building is not on fire
   */
  void extinguishFire() throws NoFireFoundException;

  /**
   * Sets the building on fire. This method should only be used to set up the scenario.
   *
   * @throws FireproofBuildingException if the building is fireproof
   */
  void setFire() throws FireproofBuildingException;
}
