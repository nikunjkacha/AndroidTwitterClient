package com.codepath.apps.twitterclient;

import java.util.ArrayList;

import org.json.JSONArray;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.codepath.apps.twitterclient.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class ComposeTweetActivity extends Activity {
	private EditText etTweetText;
	private Button btnTweet;
	private Button btnCancel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose_tweet);
		etTweetText = (EditText) findViewById(R.id.etTweetText);
		btnTweet = (Button) findViewById(R.id.btnTweet);
		btnCancel = (Button) findViewById(R.id.btnCancel);
	}
	
	public void onTweetClick(View v) {
		String tweetText = etTweetText.getText().toString();

		TwitterClientApp.getRestClient().postTweet(tweetText,
				new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray jsonTweets) {
//				ArrayList<Tweet> tweets = Tweet.fromJson(jsonTweets);
//				ListView lvTweets = (ListView) findViewById(R.id.lvTweets);
//				TweetsAdapter adapter = new TweetsAdapter(getBaseContext(), tweets);
//				lvTweets.setAdapter(adapter);
				Log.d("DEBUB", jsonTweets.toString());
			}
		});
	}
	
	public void onCancelClick(View v) {
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.compose_tweet, menu);
		return true;
	}

}
