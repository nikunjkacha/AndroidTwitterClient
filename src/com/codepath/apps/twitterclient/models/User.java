package com.codepath.apps.twitterclient.models;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Users")
public class User extends Model implements Serializable {
	private static final long serialVersionUID = 5177222050535318633L;

	@Column(name = "Name")
	private String name;
	@Column(name = "ScreenName")
	private String screenName;
	@Column(name = "Description")
	private String description;
	@Column(name = "ProfileImageUrl")
	private String profileImageUrl;
	@Column(name = "FollowersCount")
	private int followersCount;
	@Column(name = "FriendsCount")
	private int friendsCount;

	public User() {
		super();
	}

	public User(JSONObject jsonObject) throws JSONException {
		super();
		name = jsonObject.getString("name");
		screenName = jsonObject.getString("screen_name");
		description = jsonObject.getString("description");
		profileImageUrl = jsonObject.getString("profile_image_url");
		followersCount = jsonObject.getInt("followers_count");
		friendsCount = jsonObject.getInt("friends_count");
	}
	
	public String getName() {
		return name;
	}

	public String getScreenName() {
		return screenName;
	}

	public String getDescription() {
		return description;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public int getFollowersCount() {
		return followersCount;
	}

	public int getFriendsCount() {
		return friendsCount;
	}

	public static User fromJson(JSONObject jsonObject) {
		try {
			User user = new User(jsonObject);
			user.save();
			return user;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
}
