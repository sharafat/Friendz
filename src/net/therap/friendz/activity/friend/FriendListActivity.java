package net.therap.friendz.activity.friend;

import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.google.inject.Inject;
import net.therap.friendz.R;
import net.therap.friendz.controller.FriendController;
import net.therap.friendz.domain.Friend;
import roboguice.activity.RoboListActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectResource;

@ContentView(R.layout.list)
public class FriendListActivity extends RoboListActivity {
    public static final String KEY_FRIEND = "friend";

    private static final int ID_CONTEXT_MENU_EDIT = 101;
    private static final int ID_CONTEXT_MENU_DELETE = 102;

    @Inject
    private LayoutInflater layoutInflater;
    @Inject
    private FriendController friendController;

    @InjectResource(R.string.edit)
    private String edit;
    @InjectResource(R.string.delete)
    private String delete;

    private FriendListAdapter friendListAdapter;

    @Override
    @SuppressWarnings("deprecation")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        friendListAdapter = new FriendListAdapter();
        setListAdapter(friendListAdapter);

        registerForContextMenu(getListView());
    }

    @Override
    protected void onListItemClick(ListView listView, View view, int position, long id) {
        startActivityPassingFriend(FriendDetailsActivity.class, friendListAdapter.getItem(position));
    }

    private void startActivityPassingFriend(Class activityClass, Friend friend) {
        Intent intent = new Intent(this, activityClass);
        intent.putExtra(KEY_FRIEND, friend);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.friend_list_options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add_friend:
                startActivity(new Intent(this, AddEditFriendActivity.class));
                break;
        }

        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        Friend selectedFriend = friendListAdapter.getItem(info.position);

        menu.setHeaderTitle(selectedFriend.getName());
        menu.add(Menu.NONE, ID_CONTEXT_MENU_EDIT, 0, edit);
        menu.add(Menu.NONE, ID_CONTEXT_MENU_DELETE, 1, delete);
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Friend selectedFriend = friendListAdapter.getItem(info.position);

        switch (item.getItemId()) {
            case ID_CONTEXT_MENU_EDIT:
                startActivityPassingFriend(AddEditFriendActivity.class, selectedFriend);
                return true;
            case ID_CONTEXT_MENU_DELETE:
                friendController.delete(selectedFriend);
                friendListAdapter.remove(selectedFriend);
                friendListAdapter.notifyDataSetChanged(); //will update the list view
                return true;
        }

        return false;
    }


    private class FriendListAdapter extends ArrayAdapter<Friend> {
        public FriendListAdapter() {
            super(FriendListActivity.this, 0, friendController.list());
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Friend friend = getItem(position);

            View listItemView = layoutInflater.inflate(android.R.layout.simple_list_item_2, null);
            ((TextView) listItemView.findViewById(android.R.id.text1)).setText(friend.getName());
            ((TextView) listItemView.findViewById(android.R.id.text2)).setText(friend.getEmail());

            return listItemView;
        }
    }
}
