package models;

import static org.junit.Assert.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controllers.PacemakerAPI;
import models.User;
import static models.Fixtures.users;

public class UserTest {

  User homer = new User("homer", "simpson", "homer@simpson.com", "secret");

  @Test
  public void testCreate() {
    assertEquals("homer", homer.firstname);
    assertEquals("simpson", homer.lastname);
    assertEquals("homer@simpson.com", homer.email);
    assertEquals("secret", homer.password);
  }

  @Test
  public void testIds() {
    Set<String> ids = new HashSet<>();
    for (User user : users) {
      ids.add(user.id);
    }
    assertEquals(users.size(), ids.size());
  }

  @Test
  public void testToString() {
    assertEquals("User{" + homer.id + ", homer, simpson, secret, homer@simpson.com, {}}",
        homer.toString());
  }


  @Test
  public void tesAddActivity() {
    Activity activity = new Activity("walk", "fridge", 0.001);
    homer.activities.put(activity.id,activity);
    assertEquals("User{" + homer.id + ", homer, simpson, secret, homer@simpson.com, {" + activity.id + "=Activity{" + activity.id + ", walk, fridge, 0.001, []}}}",
        homer.toString());
  }

/*  PacemakerAPI pacemaker = new PacemakerAPI();

  User homer = new User("homer", "simpson", "homer@simpson.com", "secret");

  @Before
  public void setup() {
    pacemaker.deleteUsers();
  }

  @After
  public void tearDown() {
  }
  
  

  @Test
  public void testCreateUser() {
    User user = pacemaker.createUser(homer.firstname, homer.lastname, homer.email, homer.password);
    assertEquals(user, homer);
    User user2 = pacemaker.getUserByEmail(homer.email);
    assertEquals(user2, homer);
  }

  @Test
  public void testCreateUsers() {
    users.forEach(
        user -> pacemaker.createUser(user.firstname, user.lastname, user.email, user.password));
    Collection<User> returnedUsers = pacemaker.getUsers();
    assertEquals(users.size(), returnedUsers.size());
  }*/
}