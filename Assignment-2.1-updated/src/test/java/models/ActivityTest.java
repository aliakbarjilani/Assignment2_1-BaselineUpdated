package models;

import static org.junit.Assert.*;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;
import static models.Fixtures.activities;

public class ActivityTest {
  Activity test = new Activity("walk", "fridge", 0.001);

  @Test
  public void testCreate() {
    assertEquals("walk", test.type);
    assertEquals("fridge", test.location);
    //assertEquals(0.001, test.distance);
  }

  @Test
  public void testIds() {
    Set<String> ids = new HashSet<>();
    for (Activity activity : activities) {
      ids.add(activity.id);
    }
    assertEquals(activities.size(), ids.size());
  }

  @Test
  public void testToString() {
    assertEquals("Activity{" + test.id + ", walk, fridge, 0.001, []}", test.toString());
  }

}
/*import static models.Fixtures.activities;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import models.Activity;
import models.User;*/