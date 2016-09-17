/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facebook.trash;

import facebook4j.Checkin;
import facebook4j.Event;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.GeoLocation;
import facebook4j.Group;
import facebook4j.Location;
import facebook4j.Place;

import facebook4j.Post;
import facebook4j.ResponseList;
import facebook4j.User;
import facebook4j.auth.AccessToken;
import facebook4j.internal.org.json.JSONArray;
import facebook4j.internal.org.json.JSONException;
import facebook4j.internal.org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LABORATORIO 5
 */
public class facebooksample1 {
    // http://facebook4j.org/en/code-examples.html

    public static void main(String[] args) {
        try {
            Facebook facebook = new FacebookFactory().getInstance();

            String appId = "";
            String appSecret = "";
            String commaSeparetedPermissions = "";
            String accessToken = null;    //  facebook.getOAuthAppAccessToken();

            facebook.setOAuthAppId(appId, appSecret);
            facebook.setOAuthPermissions(commaSeparetedPermissions);

            facebook.setOAuthAccessToken(new AccessToken(accessToken, null));

            ResponseList<Post> results1 = facebook.searchPosts("watermelon");
            ResponseList<User> results2 = facebook.searchUsers("mark");
            ResponseList<Event> results3 = facebook.searchEvents("conference");
            ResponseList<Group> results4 = facebook.searchGroups("programming");

            // Search by name
            ResponseList<Place> results = facebook.searchPlaces("coffee");

// You can narrow your search to a specific location and distance
            GeoLocation center = new GeoLocation(37.76, -122.427);
            int distance = 1000;
            ResponseList<Place> results5 = facebook.searchPlaces("coffee", center, distance);

            // you or your friend's latest checkins, or checkins where you or your friends have been tagged
            ResponseList<Checkin> results6 = facebook.searchCheckins();

            // To search for objects near a geographical location
            center = new GeoLocation(37.76, -122.427);
            distance = 1000;
            ResponseList<Location> results7 = facebook.searchLocations(center, distance
            );

//            // To search for objects at a particular place
//            String placeId = "166793820034304";
//            ResponseList<Location> locations = facebookBestFriend1.searchLocations(placeId);
            // Single FQL
            String query = "SELECT uid2 FROM friend WHERE uid1=me()";
            JSONArray jsonArray = facebook.executeFQL(query);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                System.out.println(jsonObject.get("uid2"));
            }

// Multiple FQL
            Map<String, String> queries = new HashMap<String, String>();
            queries.put("all friends", "SELECT uid2 FROM friend WHERE uid1=me()");
            queries.put("my name", "SELECT name FROM user WHERE uid=me()");
            Map<String, JSONArray> result = facebook.executeMultiFQL(queries);
            JSONArray allFriendsJSONArray = result.get("all friends");
            for (int i = 0; i < allFriendsJSONArray.length(); i++) {
                JSONObject jsonObject = allFriendsJSONArray.getJSONObject(i);
                System.out.println(jsonObject.get("uid2"));
            }
            JSONArray myNameJSONArray = result.get("my name");
            System.out.println(myNameJSONArray.getJSONObject(0).get("name"));

        } catch (FacebookException ex) {
            Logger.getLogger(facebooksample1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(facebooksample1.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
