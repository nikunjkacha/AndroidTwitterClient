package com.codepath.apps.twitterclient;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.apps.twitterclient.fragments.HomeTimelineFragment;
import com.codepath.apps.twitterclient.fragments.MentionsTimelineFragment;
import com.codepath.apps.twitterclient.models.Tweet;
import com.codepath.apps.twitterclient.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TweetsActivity extends FragmentActivity implements TabListener {
	User myUser;
	HomeTimelineFragment htlf;
	MentionsTimelineFragment mtlf;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tweets);
		setupNavigationTabs();
		loadMyProfileInfo();
	}
	
	public void loadMyProfileInfo() {
		TwitterClientApp.getRestClient().getMyInfo(
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONObject json) {
						myUser = User.fromJson(json);
					}
				});
	}
	
	public void setupNavigationTabs() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);
		Tab tabHome = actionBar.newTab().setText("Home")
				.setTag("HomeTimelineFragment")
				.setIcon(R.drawable.ic_home)
				.setTabListener(this);
		Tab tabMentions = actionBar.newTab().setText("Mentions")
				.setTag("MentionsTimelineFragment")
				.setIcon(R.drawable.ic_mentions)
				.setTabListener(this);
		actionBar.addTab(tabHome);
		actionBar.addTab(tabMentions);
		actionBar.selectTab(tabHome);
	}

	public void onProfileClick(MenuItem menuItem) {
		Intent i = new Intent(this, ProfileActivity.class);
		i.putExtra("user", myUser);
		startActivity(i);
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

			htlf.pushTweet(Tweet.fromJson(jsonTweet));
		}
	} 
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tweets, menu);
		return true;
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		FragmentManager manager = getSupportFragmentManager();
		android.support.v4.app.FragmentTransaction fts = manager.beginTransaction();
		if (tab.getTag() == "HomeTimelineFragment") {
			if (htlf == null) {
				htlf = new HomeTimelineFragment();
			}
			fts.replace(R.id.frameContainer, htlf);
		} else {
			if (mtlf == null) {
				mtlf = new MentionsTimelineFragment();
			}
			fts.replace(R.id.frameContainer, mtlf);
		}
		fts.commit();
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	}
}
