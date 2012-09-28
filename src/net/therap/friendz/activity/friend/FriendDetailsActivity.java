package net.therap.friendz.activity.friend;

import android.os.Bundle;
import android.widget.TextView;
import net.therap.friendz.R;
import net.therap.friendz.domain.Friend;
import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.friend_details)
public class FriendDetailsActivity extends RoboActivity {
    @InjectView(R.id.friend_id)
    private TextView friendIdTextView;
    @InjectView(R.id.name)
    private TextView friendNameTextView;
    @InjectView(R.id.email)
    private TextView friendEmailTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Friend friend = (Friend) getIntent().getSerializableExtra(FriendListActivity.KEY_FRIEND);
        friendIdTextView.setText(Integer.toString(friend.getId()));
        friendNameTextView.setText(friend.getName());
        friendEmailTextView.setText(friend.getEmail());
    }
}
