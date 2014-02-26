package ku.traning.kudatabase;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ListActivity implements LocationListener {
	private LocationsDataSource datasource;
	private LocationManager lm;
	private Location currentPos;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		lm = (LocationManager) getSystemService(LOCATION_SERVICE);

		datasource = new LocationsDataSource(this);
		datasource.open();

		List<LocationModel> values = datasource.getAllLocations();
		

		// use the SimpleCursorAdapter to show the
		// elements in a ListView
		ArrayAdapter<LocationModel> adapter = new ArrayAdapter<LocationModel>(
				this,android.R.layout.simple_list_item_1, values);
		setListAdapter(adapter);
	}

	// Will be called via the onClick attribute
	// of the buttons in main.xml
	public void onClick(View view) {
		@SuppressWarnings("unchecked")
		ArrayAdapter<LocationModel> adapter = (ArrayAdapter<LocationModel>) getListAdapter();
		LocationModel location = null;
		switch (view.getId()) {
		case R.id.add:
			if (currentPos == null)
			{
				Toast.makeText(this, "Location Not Available, Nothing to Add", Toast.LENGTH_SHORT).show();
				break;
			}
			location = datasource.createLocation(String.valueOf(currentPos));
			adapter.add(location);
			Toast.makeText(this, "ADDED", Toast.LENGTH_SHORT).show();
			break;
		case R.id.delete:
			Toast.makeText(this, "DELETED", Toast.LENGTH_SHORT).show();
			 if (getListAdapter().getCount() > 0) {
			 location = (LocationModel) getListAdapter().getItem(0);
			 datasource.deleteLocation(location);
			 adapter.remove(location);
			 }
			break;
		}
		adapter.notifyDataSetChanged();
	}

	@Override
	protected void onResume() {
		datasource.open();
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, this);
		super.onResume();
	}

	@Override
	protected void onPause() {
		datasource.close();
		lm.removeUpdates(this);
		super.onPause();
	}

	@Override
	public void onLocationChanged(Location l) {
		currentPos = l;
		
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		LocationModel locitem = (LocationModel) getListAdapter().getItem(position);
		String loc = locitem.getLocation();
		Intent i = new Intent (this, MapActivity.class);
		i.putExtra("Location", loc);
		startActivity(i);
	}

}
