package net.therap.friendz.controller;

import android.content.ContentValues;
import android.database.Cursor;
import net.therap.friendz.app.DbHelper;
import net.therap.friendz.domain.Friend;
import roboguice.inject.ContextSingleton;

import java.util.ArrayList;
import java.util.List;

@ContextSingleton
public class FriendController {
    private DbHelper dbHelper = DbHelper.getInstance();

    public List<Friend> list() {
        List<Friend> friendList = new ArrayList<Friend>();

        Cursor friendListCursor = dbHelper.select("select * from friends", null);
        while (friendListCursor.moveToNext()) {
            friendList.add(populateFriend(friendListCursor));
        }

        return friendList;
    }

    private Friend populateFriend(Cursor friendListCursor) {
        int id = Integer.parseInt(getValueOfColumn("_id", friendListCursor));
        String name = getValueOfColumn("name", friendListCursor);
        String email = getValueOfColumn("email", friendListCursor);

        return new Friend(id, name, email);
    }

    private String getValueOfColumn(String columnName, Cursor friendListCursor) {
        return friendListCursor.getString(friendListCursor.getColumnIndex(columnName));
    }

    public void save(Friend friend) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", friend.getName());
        contentValues.put("email", friend.getEmail());

        if (friend.getId() == 0) {
            dbHelper.insert("friends", contentValues);
        } else {
            dbHelper.update("friends", contentValues, "_id = ?", new String[]{Integer.toString(friend.getId())});
        }
    }

    public void delete(Friend friend) {
        dbHelper.delete("friends", "_id = ?", new String[]{Integer.toString(friend.getId())});
    }
}
