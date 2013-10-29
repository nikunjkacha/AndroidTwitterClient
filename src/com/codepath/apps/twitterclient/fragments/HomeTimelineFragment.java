package com.codepath.apps.twitterclient.fragments;

import java.util.List;

import org.json.JSONArray;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;

import com.activeandroid.query.Select;
import com.codepath.apps.twitterclient.EndlessScroll;
import com.codepath.apps.twitterclient.TwitterClientApp;
import com.codepath.apps.twitterclient.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class HomeTimelineFragment extends TweetsListFragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		lvTweets.setOnScrollListener(new EndlessScroll() {
			@Override
			public void onLoadMore(int page, 
					int totalItemsCount) {
				updateHomeTimeline(getMinId()-1);
			}
		});
		updateHomeTimeline(-1);
	};

	private boolean isNetworkConnected() {
		ConnectivityManager cm =
				(ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return (ni != null && ni.isAvailable() && ni.isConnected());
	}

	public void onLoadMoreClick(View view) {
		if (isNetworkConnected()) {
			updateHomeTimeline(getMinId()-1);
		}
	}
	
	public void updateHomeTimeline(long maxId) {
		if (isNetworkConnected()) {
			TwitterClientApp.getRestClient().getHomeTimeline(maxId,
					new JsonHttpResponseHandler() {
				@Override
				public void onSuccess(JSONArray jsonTweets) {
					displayTweets(Tweet.fromJson(jsonTweets));
				}
			});
		} else {
			List<Tweet> tweets = new Select()
				.from(Tweet.class)
				.orderBy("createdAt")
				.limit("25")
				.execute();
			displayTweets(tweets);
		}
	}
}
