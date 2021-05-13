package com.vgarg.firefighters;

import com.vgarg.api.CityNode;
import org.junit.Assert;
import org.junit.Test;

public class FirefighterImplTest {
  @Test
  public void shouldUpdateTheDistanceCovered() {
    FirefighterImpl firefighter = new FirefighterImpl(new CityNode(0, 0));
    Assert.assertEquals(0, firefighter.distanceTraveled());
    firefighter.updateDistance(5);
    Assert.assertEquals(5, firefighter.distanceTraveled());
  }
}
