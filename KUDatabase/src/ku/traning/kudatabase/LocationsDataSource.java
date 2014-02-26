package ku.traning.kudatabase;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class LocationsDataSource {

  // Database fields
  private SQLiteDatabase database;
  private MySQLiteHelper dbHelper;
  private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
      MySQLiteHelper.COLUMN_LOCATION};

  public LocationsDataSource(Context context) {
    dbHelper = new MySQLiteHelper(context);
  }

  public void open() throws SQLException {
    database = dbHelper.getWritableDatabase();
  }

  public void close() {
    dbHelper.close();
  }

  public LocationModel createLocation(String location) {
    ContentValues values = new ContentValues();
    values.put(MySQLiteHelper.COLUMN_LOCATION, location);
    long insertId = database.insert(MySQLiteHelper.TABLE_LOCATIONS, null,
        values);
    Cursor cursor = database.query(MySQLiteHelper.TABLE_LOCATIONS,
        allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
        null, null, null);
    cursor.moveToFirst();
    LocationModel newLocation = cursorToLocation(cursor);
    cursor.close();
    return newLocation;
  }

  public void deleteLocation(LocationModel location) {
    long id = location.getId();
    Log.i("Comment deleted with id: " ,String.valueOf(id));
    database.delete(MySQLiteHelper.TABLE_LOCATIONS, MySQLiteHelper.COLUMN_ID
        + " = " + id, null);
  }

  public List<LocationModel> getAllLocations() {
    List<LocationModel> locations = new ArrayList<LocationModel>();

    Cursor cursor = database.query(MySQLiteHelper.TABLE_LOCATIONS,
        allColumns, null, null, null, null, null);

    cursor.moveToFirst();
    while (!cursor.isAfterLast()) {
      LocationModel location = cursorToLocation(cursor);
      locations.add(location);
      cursor.moveToNext();
    }
    // make sure to close the cursor
    cursor.close();
    return locations;
  }

  private LocationModel cursorToLocation(Cursor cursor) {
    LocationModel location = new LocationModel();
    location.setId(cursor.getLong(0));
    location.setLocation(cursor.getString(1));
    return location;
  }
} 