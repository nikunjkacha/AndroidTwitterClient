package com.codepath.apps.twitterclient.fragments;

import org.json.JSONArray;

import android.os.Bundle;

import com.codepath.apps.twitterclient.TwitterClientApp;
import com.codepath.apps.twitterclient.models.Tweet;
import com.codepath.apps.twitterclient.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

public class UserTimelineFragment extends TweetsListFragment {
	User user;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle args = getArguments();
		this.user = (User) args.getSerializable("user");

		TwitterClientApp.getRestClient().getUserTimeline(
				user.getScreenName(),
				new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray jsonTweets) {
				displayTweets(Tweet.fromJson(jsonTweets));
			}
		});
	}

}
