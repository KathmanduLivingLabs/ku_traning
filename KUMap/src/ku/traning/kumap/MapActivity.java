package ku.traning.kumap;

import java.util.ArrayList;

import org.osmdroid.api.IMapController;
import org.osmdroid.api.Marker;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedIconOverlay.OnItemGestureListener;
import org.osmdroid.views.overlay.ItemizedOverlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.ScaleBarOverlay;

import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.Toast;

public class MapActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		// Set the Activity's View to activity_map from layout directory
		setContentView(R.layout.activity_map);
//
//		// Obtain refrence to the mapview
		MapView mv = (MapView) findViewById(R.id.map);
//
//		// Set the mapviews tile source to be MapQuest Tiles
		mv.setTileSource(TileSourceFactory.MAPQUESTOSM);
//
//		// Use Offline map tiles
//		// Move the mapfile created by the Mobile Atlas creator to the SD card
//		// /osmdroid folder using adb push source destn
//
//		// set the data connection to be false so that osmdroid wont try to load
//		// online tiles
		mv.setUseDataConnection(false);
//
//		// Set zoom controls and set multi touch controls to the mapview
//		mv.setClickable(true);
		mv.setBuiltInZoomControls(true);
		mv.setMultiTouchControls(true);
//
//		// Get a refrence to Map Controller
		IMapController mc = mv.getController();
//
//		// Create a Geopoint Object with latitude and longitude of ku
		GeoPoint ku = new GeoPoint(27.6190, 85.5389);
//
//		// Use the map controller to set the map center to kathmandu and zoom
//		// level to 16
		mc.setZoom(16);
		mc.setCenter(ku);
//
//		// get an instance of the LocationManager Class
		LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
//
//		// Use the Location manager to get the last known location
		Location l = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		
		if (l == null){
			Toast.makeText(this, "Location not Avilable", Toast.LENGTH_LONG).show();
		}else{
//
//		// Get the current GeoPoint from the location object
		GeoPoint currentPos = new GeoPoint(l.getLatitude(), l.getLongitude());
		mc.setCenter(new GeoPoint(l.getLatitude(),l.getLongitude()));

		
		Toast.makeText(this,
				"Your Current position is : " + String.valueOf(currentPos),
				Toast.LENGTH_LONG).show();
		}

		 
//
//		// Add Scale Bar
//		ScaleBarOverlay myScaleBarOverlay = new ScaleBarOverlay(this);
//		mv.getOverlays().add(myScaleBarOverlay);
//
//		// Create a new overlaylist in order to add the points that can be
//		// passed to the itemized icon overlay
		ArrayList<OverlayItem> myMarkers = new ArrayList<OverlayItem>();
//
//		// create items to pass to the arraylist
		OverlayItem myPos = new OverlayItem("My Position", "You are Here",
				ku);
//		OverlayItem kuPos = new OverlayItem("Kathmandu University",
//				"This is Kathmandu Universitys", ku);
		myMarkers.add(myPos);
//		myMarkers.add(kuPos);
//
//		// Create a listener that will handel getures that will happen on the
//		// icons
		OnItemGestureListener<OverlayItem> myOnItemGestureListener = new OnItemGestureListener<OverlayItem>() {

			@Override
			public boolean onItemLongPress(int arg0, OverlayItem arg1) {
				Toast.makeText(MapActivity.this, "LongPress", Toast.LENGTH_LONG)
						.show();
				return true;
			}

			@Override
			public boolean onItemSingleTapUp(int index, OverlayItem item) {
				Toast.makeText(MapActivity.this, "Single Tap",
						Toast.LENGTH_LONG).show();
				return true;
			}

		};

//		// Create and add overlay that will have markers
		ItemizedIconOverlay<OverlayItem> markersOverlay = new ItemizedIconOverlay<OverlayItem>(
				this, myMarkers, myOnItemGestureListener);
		mv.getOverlays().add(markersOverlay);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map, menu);
		return true;
	}

}
