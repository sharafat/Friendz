package net.therap.friendz.util;

import android.app.Application;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.google.inject.Inject;
import net.therap.friendz.app.DbHelper;

/**
 * @author The TherapJavaFest Team
 */
public abstract class DatabaseManager extends SQLiteOpenHelper {
    @Inject
    private static Application application;

    private static DbHelper instance;

    protected SQLiteDatabase database;

    public static DbHelper getInstance() {
        if (instance == null) {
            instance = new DbHelper();
            instance.database = instance.getWritableDatabase();
        }

        return instance;
    }

    protected DatabaseManager() {
        super(application, DbHelper.DB_NAME, null, DbHelper.DB_VERSION);
    }

    /**
     * Runs the provided SQL and returns a {@link Cursor} over the result set.
     *
     * @param sql           the SQL query. The SQL string must not be ; terminated
     * @param selectionArgs You may include ?s in where clause in the query,
     *                      which will be replaced by the values from selectionArgs. The
     *                      values will be bound as Strings.
     * @return A {@link Cursor} object, which is positioned before the first entry. Note that
     *         {@link Cursor}s are not synchronized, see the documentation for more details.
     */
    public Cursor select(String sql, String[] selectionArgs) {
        return database.rawQuery(sql, selectionArgs);
    }

    /**
     * Convenience method for inserting a row into the database.
     *
     * @param table  the table to insert the row into
     * @param values this map contains the initial column values for the
     *               row. The keys should be the column names and the values the
     *               column values
     * @return the row ID of the newly inserted row, or -1 if an error occurred
     */
    public long insert(String table, ContentValues values) {
        return database.insert(table, null, values);
    }

    /**
     * Convenience method for updating rows in the database.
     *
     * @param table       the table to update in
     * @param values      a map from column names to new column values. null is a
     *                    valid value that will be translated to NULL.
     * @param whereClause the optional WHERE clause to apply when updating.
     *                    Passing null will update all rows.
     * @param whereArgs   You may include ?s in where clause in the query,
     *                    which will be replaced by the values from whereArgs. The
     *                    values will be bound as Strings.
     * @return the number of rows affected
     */
    public int update(String table, ContentValues values, String whereClause, String[] whereArgs) {
        return database.update(table, values, whereClause, whereArgs);
    }

    /**
     * Convenience method for deleting rows in the database.
     *
     * @param table       the table to delete from
     * @param whereClause the optional WHERE clause to apply when deleting.
     *                    Passing null will delete all rows.
     * @param whereArgs   You may include ?s in where clause in the query,
     *                    which will be replaced by the values from whereArgs. The
     *                    values will be bound as Strings.
     * @return the number of rows affected if a whereClause is passed in, 0
     *         otherwise. To remove all rows and get a count pass "1" as the
     *         whereClause.
     */
    public int delete(String table, String whereClause, String[] whereArgs) {
        return database.delete(table, whereClause, whereArgs);
    }

    /**
     * Execute a single SQL statement that is NOT a SELECT
     * or any other SQL statement that returns data.
     * <p>
     * It has no means to return any data (such as the number of affected rows).
     * Instead, you're encouraged to use {@link #insert(String, ContentValues)},
     * {@link #update(String, ContentValues, String, String[])}, et al, when possible.
     * </p>
     *
     * @param sql the SQL statement to be executed. Multiple statements separated by semicolons are
     * not supported.
     * @param bindArgs only byte[], String, Long and Double are supported in bindArgs.
     */
    public void execSQL(String sql, Object[] bindArgs) {
        database.execSQL(sql, bindArgs);
    }

    public SQLiteDatabase getDatabase() {
        return database;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();

        if (instance != null) {
            instance.database.close();
        }
    }
}
