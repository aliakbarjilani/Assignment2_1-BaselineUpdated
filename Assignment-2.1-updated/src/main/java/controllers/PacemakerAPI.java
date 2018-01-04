package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Optional;

import models.Activity;
import models.Location;
import models.User;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import utils.Serializer;

public class PacemakerAPI {
  
  //PacemakerInterface pacemakerInterface;
  private PacemakerAPI paceApi = new PacemakerAPI();
  private Map<String, String> messageIndex = new HashMap<>();
  private Map<String, User> friendIndex = new HashMap<>();
  private Map<String, User> emailIndex = new HashMap<>();
  private Map<String, User> userIndex = new HashMap<>();
  private Map<String, Activity> activitiesIndex = new HashMap<>();

  public Serializer serializer;

  public PacemakerAPI() {}

  public PacemakerAPI(Serializer serializer) {
    this.serializer = serializer;
  }
  public Collection<User> getUsers() {
    return userIndex.values();
  }

  public void deleteUsers() {
    userIndex.clear();
    emailIndex.clear();
  }

  public User createUser(String firstname, String lastname, String email, String password) {
    User user = new User(firstname, lastname, email, password);
    emailIndex.put(email, user);
    userIndex.put(user.id, user);
    return user;
  }
  
  public Activity createActivity(String id, String type, String location, double distance){
    Activity activity = null;
    Optional<User> user = Optional.fromNullable(userIndex.get(id));
    if (user.isPresent()) {
      activity = new Activity(type, location, distance);
      user.get().activities.put(activity.id, activity);
      activitiesIndex.put(activity.id, activity);
    }
    return activity;
  }

  public Activity getActivity(String id) {
    return activitiesIndex.get(id);
  }
  
  public String getActivityLocations(String id) {
    return activitiesIndex.get(id).getRoute();
  }

  public Collection<Activity> getActivities(String id) {
    Collection<Activity> activities = null;
    Optional<User> user = Optional.fromNullable(userIndex.get(id));
    if (user.isPresent()) {
      activities = user.get().activities.values();
    }
    return activities;
  }

  public List<Activity> listActivities(String userId, String sortBy) {
    List<Activity> activities = new ArrayList<>();
    activities.addAll(userIndex.get(userId).activities.values());
    switch (sortBy) {
      case "type":
        activities.sort((a1, a2) -> a1.type.compareTo(a2.type));
        break;
      case "location":
        activities.sort((a1, a2) -> a1.location.compareTo(a2.location));
        break;
      case "distance":
        activities.sort((a1, a2) -> Double.compare(a1.distance, a2.distance));
        break;
    }
    return activities;
  }

  public Activity getActivity(String userId, String activityId) {
    Activity activity = null;
     try {
       //Call<Activity> call = pacemakerInterface.getActivity(userId, activityId);
       //Response<Activity> response = call.execute();
       activity = paceApi.getActivity(userId, activityId);
       //activity = response.body();
     } catch (Exception e) {
       System.out.println(e.getMessage());
     }
     return activity;
   }
  
  public void addLocation(String id, double latitude, double longitude) 
  {
    Optional<Activity> activity = Optional.fromNullable(activitiesIndex.get(id));
    if (activity.isPresent()) 
    {
      activity.get().route.add(new Location(latitude, longitude));
    }
  }

  public User getUserByEmail(String email) 
  {
    return emailIndex.get(email);
  }

  public User getUser(String id) 
  {
    return userIndex.get(id);
  }

  public User deleteUser(String id) 
  {
    User user = userIndex.remove(id);
    return emailIndex.remove(user.email);
  }
  
  public void deleteActivities(String id) {
    Optional<User> user = Optional.fromNullable(userIndex.get(id));
    if (user.isPresent()) {
      user.get().activities.values().forEach(activity -> activitiesIndex.remove(activity.getId()));
      user.get().activities.clear();
    }
  }
  
  public List<Location> listActivityLocations(String id) {
    return activitiesIndex.get(id).route;
  }

  public User Follow(String email)
  {
    User friend = emailIndex.get(email);
    friendIndex.put(email, friend);
    return friend;
  }
  
  public Collection<User> listFriends()
  {
    return friendIndex.values();
  }
  
  public User getFriendByEmail(String email) 
  {
    return friendIndex.get(email);
  }

  public User unFollow(String email)
  {
    User friend = emailIndex.get(email);
    return friendIndex.remove(friend.email);
  }
  
  public String messageFriend(String email, String message)
  {
    messageIndex.put(email, message);
    
    return messageIndex.get(email);
  }

  // lm 
  public Collection<String> listMessages()
  {
    return messageIndex.values();
  }
}