package com.codepath.apps.twitterclient;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
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
			public void onSuccess(JSONObject jsonTweet) {
				Intent i = new Intent();
				i.putExtra("jsonTweet", jsonTweet.toString());
				setResult(RESULT_OK, i);
				finish();
				Log.d("DEBUG", jsonTweet.toString());
			}
		});
	}
	
	public void onCancelClick(View v) {
		setResult(RESULT_CANCELED, null); 
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.compose_tweet, menu);
		return true;
	}

}
