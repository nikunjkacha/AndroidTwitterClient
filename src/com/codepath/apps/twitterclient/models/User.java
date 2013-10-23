package com.codepath.apps.twitterclient.models;

import org.json.JSONException;
import org.json.JSONObject;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Users")
public class User extends Model {
	@Column(name = "Name")
	private String name;
	@Column(name = "ScreenName")
	private String screenName;
	@Column(name = "ProfileImageUrl")
	private String profileImageUrl;

	public User() {
		super();
	}

	public User(JSONObject jsonObject) throws JSONException {
		super();
		name = jsonObject.getString("name");
		screenName = jsonObject.getString("screen_name");
		profileImageUrl = jsonObject.getString("profile_image_url");
	}
	
	public String getName() {
		return name;
	}

	public String getScreenName() {
		return screenName;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
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
