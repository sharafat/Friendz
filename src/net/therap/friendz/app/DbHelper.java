package net.therap.friendz.app;

import android.database.sqlite.SQLiteDatabase;
import net.therap.friendz.util.DatabaseManager;

public class DbHelper extends DatabaseManager {
    public static final String DB_NAME = "friends.db";
    public static final int DB_VERSION = 1;

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUsersTableSql = "CREATE TABLE users(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "email TEXT NOT NULL," +
                "password TEXT NOT NULL)";
        String createFriendsTableSql = "CREATE TABLE friends(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "email TEXT NOT NULL)";

        db.execSQL(createUsersTableSql);
        db.execSQL(createFriendsTableSql);

        //insert a few dummy records into database
        db.execSQL("INSERT INTO users (email, password) VALUES ('dummy@therapservices.net', 'dummy')");
        db.execSQL("INSERT INTO friends (name, email) VALUES ('Best Friend', 'best.friend@therapservices.net')");
        db.execSQL("INSERT INTO friends (name, email) VALUES ('Good Friend', 'good.friend@therapservices.net')");
        db.execSQL("INSERT INTO friends (name, email) VALUES ('Lousy Friend', 'lousy.friend@therapservices.net')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
