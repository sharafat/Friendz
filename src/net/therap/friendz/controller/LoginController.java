package net.therap.friendz.controller;

import android.database.Cursor;
import net.therap.friendz.app.DbHelper;
import roboguice.inject.ContextSingleton;

@ContextSingleton
public class LoginController {
    private DbHelper dbHelper = DbHelper.getInstance();

    /**
     * Logs a user in the application.
     *
     * @param email email of the user
     * @param password password of the user
     * @return true if user's credentials are correct, false otherwise
     */
    public boolean login(String email, String password) {
        Cursor userCursor = dbHelper.select("select _id from users where email = ? and password = ?",
                new String[]{email, password});

        if (!userCursor.moveToFirst()) {    //oops, no such email/password found!
            userCursor.close();
            return false;
        }

        int userId = Integer.parseInt(userCursor.getString(0));
        userCursor.close();

        //do something to save the userId, for example, in shared preference so that it may be used later...
        //and then return the glad tidings of successful login -
        return true;
    }

}
