package com.codepath.apps.twitterclient;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.activeandroid.query.Select;
import com.codepath.apps.twitterclient.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends Activity {
	ListView lvTweets;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		lvTweets = (ListView) findViewById(R.id.lvTweets);
		updateHomeTimeline(-1);
	}
	
	public void displayTweets(List<Tweet> tweets) {
		TweetsAdapter adapter = (TweetsAdapter) lvTweets.getAdapter();
		if (adapter == null) {
			adapter = new TweetsAdapter(getBaseContext(), tweets);
			lvTweets.setAdapter(adapter);
		} else {
			adapter.addAll(tweets);
		}
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
					ArrayList<Tweet> tweets = Tweet.fromJson(jsonTweets);
					displayTweets(tweets);
					Log.d("DEBUG", Integer.toString(tweets.size()));
					Log.d("DEBUG", jsonTweets.toString());
				}
			});
		} else {
			List<Tweet> tweets = new ArrayList<Tweet>();
			if (lvTweets.getAdapter() == null) {
				tweets = new Select()
				.from(Tweet.class)
				.orderBy("createdAt")
				.limit("25")
				.execute();
			}
			displayTweets(tweets);
		}
	}
	
	public long getMinId() {
		TweetsAdapter adapter = (TweetsAdapter)lvTweets.getAdapter();
		return adapter.getItem(adapter.getCount()-1).getTweetId();
	}
	
	public void onLoadMoreClick(View view) {
		if (isNetworkConnected()) {
			updateHomeTimeline(getMinId()-1);
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
			Tweet tweet = Tweet.fromJson(jsonTweet);
			TweetsAdapter adapter = (TweetsAdapter) lvTweets.getAdapter();
			adapter.insert(tweet, 0);
		}
	} 
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
	}

}
