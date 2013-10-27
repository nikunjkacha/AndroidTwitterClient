package com.codepath.apps.twitterclient.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.codepath.apps.twitterclient.R;
import com.codepath.apps.twitterclient.TweetsAdapter;
import com.codepath.apps.twitterclient.models.Tweet;

public class TweetsListFragment extends Fragment {
	ListView lvTweets;
	TweetsAdapter tweetsAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragments_tweets_list, parent, false);
	}
	
	public void pushTweet(Tweet tweet) {
		tweetsAdapter.insert(tweet, 0);
	}

	public void displayTweets(List<Tweet> tweets) {
		tweetsAdapter.addAll(tweets);
		Log.d("DEBUG", Integer.toString(tweets.size()));
	}

	public long getMinId() {
		return tweetsAdapter.getItem(
				tweetsAdapter.getCount()-1
				).getTweetId();
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		lvTweets = (ListView) getActivity().findViewById(R.id.lvTweets);
		tweetsAdapter = new TweetsAdapter(getActivity(), new ArrayList<Tweet>());
		lvTweets.setAdapter(tweetsAdapter);
	};
	
}
