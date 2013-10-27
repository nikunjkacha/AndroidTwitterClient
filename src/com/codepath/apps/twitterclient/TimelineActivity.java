package com.codepath.apps.twitterclient;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.activeandroid.query.Select;
import com.codepath.apps.twitterclient.fragments.TweetsListFragment;
import com.codepath.apps.twitterclient.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends FragmentActivity {
	TweetsListFragment fragmentTweets;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		fragmentTweets = (TweetsListFragment) getSupportFragmentManager().
				findFragmentById(R.id.fragmentTweets);
		updateHomeTimeline(-1);
	}
	
	private boolean isNetworkConnected() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return (ni != null && ni.isAvailable() && ni.isConnected());
	}

	public void updateHomeTimeline(long maxId) {
		if (isNetworkConnected()) {
			TwitterClientApp.getRestClient().getHomeTimeline(maxId,
					new JsonHttpResponseHandler() {
				@Override
				public void onSuccess(JSONArray jsonTweets) {
					fragmentTweets.displayTweets(Tweet.fromJson(jsonTweets));
				}
			});
		} else {
			List<Tweet> tweets = new Select()
				.from(Tweet.class)
				.orderBy("createdAt")
				.limit("25")
				.execute();
			fragmentTweets.displayTweets(tweets);
		}
	}
	
	public void onLoadMoreClick(View view) {
		if (isNetworkConnected()) {
			updateHomeTimeline(fragmentTweets.getMinId()-1);
		}
	}
	
	public void onComposeClick(MenuItem menuItem) {
		Intent i = new Intent(this, ComposeTweetActivity.class);
		startActivityForResult(i, 1337);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent i) {
		if (resultCode == RESULT_OK) {
			JSONObject jsonTweet;
			try {
				jsonTweet = new JSONObject(i.getExtras().getString("jsonTweet"));
			} catch (JSONException e) {
				e.printStackTrace();
				return;
			}
			fragmentTweets.pushTweet(Tweet.fromJson(jsonTweet));
		}
	} 
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
	}
}
