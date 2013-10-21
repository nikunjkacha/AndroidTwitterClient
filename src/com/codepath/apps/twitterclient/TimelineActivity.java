package com.codepath.apps.twitterclient;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

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
	
	public void updateHomeTimeline(long maxId) {
		TwitterClientApp.getRestClient().getHomeTimeline(maxId,
				new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray jsonTweets) {
				ArrayList<Tweet> tweets = Tweet.fromJson(jsonTweets);
				TweetsAdapter adapter = (TweetsAdapter) lvTweets.getAdapter();
				if (adapter == null) {
					adapter = new TweetsAdapter(getBaseContext(), tweets);
					lvTweets.setAdapter(adapter);
				} else {
					adapter.addAll(tweets);
				}

				Log.d("DEBUG", Integer.toString(tweets.size()));
				Log.d("DEBUG", jsonTweets.toString());
			}
		});
	}
	
	public long getMaxId() {
		TweetsAdapter adapter = (TweetsAdapter)lvTweets.getAdapter();
		return adapter.getItem(adapter.getCount()-1).getId();
	}
	
	public void onLoadMoreClick(View view) {
		updateHomeTimeline(getMaxId()-1);
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
