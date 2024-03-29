package com.codepath.apps.twitterclient;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.twitterclient.models.Tweet;
import com.codepath.apps.twitterclient.models.User;
import com.nostra13.universalimageloader.core.ImageLoader;

public class TweetsAdapter extends ArrayAdapter<Tweet> {
	
	public TweetsAdapter(Context context, List<Tweet> tweets) {
		super(context, 0, tweets);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.tweet_item, null);
		}
		
		Tweet tweet = getItem(position);
		
		ImageView imageView = (ImageView) view.findViewById(R.id.ivProfile);
		final User user = tweet.getUser();
		ImageLoader.getInstance().displayImage(user.getProfileImageUrl(), imageView);

		imageView.setOnClickListener(new View.OnClickListener(){
		    public void onClick(View v) {
				Intent i = new Intent(getContext(), ProfileActivity.class);
				i.putExtra("user", user);
				getContext().startActivity(i);
		    }
		});

		TextView nameView = (TextView) view.findViewById(R.id.tvName);
		String formattedName = "<b>" + tweet.getUser().getName() + " </b><small><font color='#777777'>@"+
				tweet.getUser().getScreenName() + "</font><small>";
		nameView.setText(Html.fromHtml(formattedName));

		SimpleDateFormat df =
				new SimpleDateFormat("dd MMM yy", Locale.ENGLISH);
		TextView dateView = (TextView) view.findViewById(R.id.tvDate);
		dateView.setText(df.format(tweet.getDate()).toString());
		
		TextView bodyView = (TextView) view.findViewById(R.id.tvBody);
		bodyView.setText(Html.fromHtml(tweet.getBody()));
		
		return view;
	}
}
