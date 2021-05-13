package com.vgarg.firefighters;

import java.util.*;

import com.vgarg.api.City;
import com.vgarg.api.CityNode;
import com.vgarg.api.FireDispatch;
import com.vgarg.api.Firefighter;
import com.vgarg.api.exceptions.NoFireFoundException;

public class FireDispatchImpl implements FireDispatch {

  private List<Firefighter> fireFighters;
  private City city;

  public FireDispatchImpl(City city) {
    this.city = city;
    this.fireFighters = new ArrayList<>();
  }

  @Override
  public void setFirefighters(int numFirefighters) {
    for (int i = 0; i < numFirefighters; i++) {
      this.fireFighters.add(new FirefighterImpl(this.city.getFireStation().getLocation())); // Initialize each fire fighter with fire station location.
    }
  }

  @Override
  public List<Firefighter> getFirefighters() {
    return this.fireFighters;
  }

  @Override
  public void dispatchFirefighters(CityNode... burningBuildings) {
    int i = 0;
    while (i < burningBuildings.length) {
      FirefighterNextCityNode firefighterNextCityNode = this.getClosestBuildingForFirefighters(burningBuildings);
      this.handleExtinguishingFire(firefighterNextCityNode.getFirefighter(), firefighterNextCityNode.getNextCityNode());
      i++;
    }
  }

  private FirefighterNextCityNode getClosestBuildingForFirefighters(CityNode... burningBuildings) {
    int closestDistance = 0;
    Firefighter nextFirefighter = null;
    Map<Firefighter, CityNodeWithDistance> closestNodePerFirefighter = new HashMap<>();
    for (Firefighter firefighter : this.fireFighters) {
      CityNodeWithDistance closestNode = this.getClosestBuildingForFirefighter(firefighter, burningBuildings);
      closestNodePerFirefighter.put(firefighter, closestNode);
    }

    for (Map.Entry<Firefighter, CityNodeWithDistance> entry : closestNodePerFirefighter.entrySet()) {
      CityNodeWithDistance cityNodeWithDistance = entry.getValue();
      if (closestDistance == 0 || cityNodeWithDistance.getDistance() < closestDistance) {
        closestDistance = cityNodeWithDistance.getDistance();
        nextFirefighter = entry.getKey();
      }
    }

    return new FirefighterNextCityNode(nextFirefighter, closestNodePerFirefighter.get(nextFirefighter));
  }

  private CityNodeWithDistance getClosestBuildingForFirefighter(Firefighter firefighter, CityNode... burningBuildings) {
    int closestDistance = 0;
    int closestIndex = -1;
    for (int i = 0; i < burningBuildings.length; i++) {
      if (this.city.getBuilding(burningBuildings[i]).isBurning()) {
        int distance = this.calculateDistance(firefighter.getLocation(), burningBuildings[i]);
        if (closestDistance == 0 || distance < closestDistance) {
          closestDistance = distance;
          closestIndex = i;
        }
      }
    }
    return new CityNodeWithDistance(burningBuildings[closestIndex], closestDistance);
  }

  private void handleExtinguishingFire(Firefighter firefighter, CityNodeWithDistance nextClosest) {
    try {
      this.city.getBuilding(nextClosest.getCityNode()).extinguishFire();
      ((FirefighterImpl) firefighter).updateDistance(nextClosest.getDistance());
      ((FirefighterImpl) firefighter).setCurrentLocation(nextClosest.getCityNode());
    } catch (NoFireFoundException ex) {
      System.out.println("No fire found...");
    }
  }

  // Taxicab distance formulae: d = |x2 - x1| + |y2 - y1|
  private int calculateDistance(CityNode source, CityNode destination) {
    return Math.abs(destination.getX() - source.getX()) + Math.abs(destination.getY() - source.getY());
  }

  /**
   * Inner class to provide an object to store given firefighter and next city node to be visited.
   */
  private class FirefighterNextCityNode {
    private Firefighter firefighter;
    private CityNodeWithDistance nextCityNode;

    FirefighterNextCityNode(Firefighter firefighter, CityNodeWithDistance nextCityNode) {
      this.firefighter = firefighter;
      this.nextCityNode = nextCityNode;
    }

    Firefighter getFirefighter() {
      return firefighter;
    }

    CityNodeWithDistance getNextCityNode() {
      return nextCityNode;
    }
  }

  /**
   * Inner class to provide an object to store given city node and distance of it from previous visited node.
   */
  private class CityNodeWithDistance {
    private CityNode cityNode;
    private int distance;

    CityNodeWithDistance(CityNode cityNode, int distance) {
      this.cityNode = cityNode;
      this.distance = distance;
    }

    CityNode getCityNode() {
      return this.cityNode;
    }

    int getDistance() {
      return this.distance;
    }
  }
}
