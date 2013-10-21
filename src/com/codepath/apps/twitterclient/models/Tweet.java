package com.codepath.apps.twitterclient.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Tweets")
public class Tweet extends BaseModel {
	@Column(name = "User")
	private User user;
	@Column(name = "Body")
	private String body;
	@Column(name = "Id")
	private long id;
	@Column(name = "CreatedAt")
	private String createdAt;

	public Tweet(JSONObject jsonObject) throws JSONException {
		super(jsonObject);
		user = User.fromJson(jsonObject.getJSONObject("user"));
		body = getString("text");
		id = getLong("id");
		createdAt = getString("created_at");
	}

	public Date getDate() {
		Date d;
		try {
			d = new SimpleDateFormat(
					"EE MMM DD HH:mm:ss Z yyyy", Locale.ENGLISH
					).parse(createdAt);
//			Sat Oct 19 17:03:38 +0000 2013
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		return d;
	}
	
	public User getUser() {
		return user;
	}

	public String getBody() {
		return body;
	}

	public long getTweetId() {
		return id;
	}

	public static Tweet fromJson(JSONObject jsonObject) {
		Tweet tweet;
		try {
			tweet = new Tweet(jsonObject);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return tweet;
	}

	public static ArrayList<Tweet> fromJson(JSONArray jsonArray) {
		ArrayList<Tweet> tweets = new ArrayList<Tweet>(jsonArray.length());
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject tweetJson = null;
			try {
				tweetJson = jsonArray.getJSONObject(i);
			} catch (JSONException e) {
				e.printStackTrace();
				continue;
			}
			
			Tweet tweet = Tweet.fromJson(tweetJson);
			if (tweet != null) {
				tweets.add(tweet);
			}
		}

		return tweets;
	}
}
