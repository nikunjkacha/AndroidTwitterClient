package com.codepath.apps.twitterclient.fragments;

import org.json.JSONArray;

import android.os.Bundle;

import com.codepath.apps.twitterclient.EndlessScroll;
import com.codepath.apps.twitterclient.TwitterClientApp;
import com.codepath.apps.twitterclient.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class MentionsTimelineFragment extends TweetsListFragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		updateMentionsTimeline(-1);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		lvTweets.setOnScrollListener(new EndlessScroll() {
			@Override
			public void onLoadMore(int page, 
					int totalItemsCount) {
				updateMentionsTimeline(getMinId()-1);
			}
		});
	};
	
	public void updateMentionsTimeline(long maxId) {
		TwitterClientApp.getRestClient().getMentions(
				maxId,
				new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray jsonTweets) {
				displayTweets(Tweet.fromJson(jsonTweets));
			}
		});
		
	}
}
