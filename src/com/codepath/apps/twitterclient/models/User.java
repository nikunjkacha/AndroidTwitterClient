package com.codepath.apps.twitterclient.models;

import org.json.JSONObject;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Users")
public class User extends BaseModel {
	@Column(name = "Name")
	private String name;
	@Column(name = "ScreenName")
	private String screenName;
	@Column(name = "ProfileImageUrl")
	private String profileImageUrl;

	public User(JSONObject jsonObject) {
		super(jsonObject);
		name = getString("name");
		screenName = getString("screen_name");
		profileImageUrl = getString("profile_image_url");
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
		return new User(jsonObject);
	}
}
