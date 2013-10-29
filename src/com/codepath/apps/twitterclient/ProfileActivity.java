package com.codepath.apps.twitterclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.twitterclient.fragments.UserTimelineFragment;
import com.codepath.apps.twitterclient.models.User;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ProfileActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);

		Intent i = getIntent();
		User user = (User) i.getSerializableExtra("user");
		getActionBar().setTitle("@"+user.getScreenName());
		populateProfileHeader(user);
		
		UserTimelineFragment userTimeline = new UserTimelineFragment();
		Bundle args = new Bundle();
		args.putSerializable("user", user);
		userTimeline.setArguments(args);

		getSupportFragmentManager().beginTransaction()
		.replace(R.id.frameUserTimeline, userTimeline)
		.commit();
	}
	
	public void populateProfileHeader(User user) {
		TextView tvName = (TextView) findViewById(R.id.tvName);
		TextView tvTagline = (TextView) findViewById(R.id.tvTagLine);
		TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
		TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
		ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
		
		tvName.setText(user.getName());
		tvTagline.setText(user.getDescription());
		tvFollowers.setText("Followers: " + Integer.toString(user.getFollowersCount()));
		tvFollowing.setText("Following: " + Integer.toString(user.getFriendsCount()));
		ImageLoader.getInstance().displayImage(user.getProfileImageUrl(), ivProfileImage);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}

}
