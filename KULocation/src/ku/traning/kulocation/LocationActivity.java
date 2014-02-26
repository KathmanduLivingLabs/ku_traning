package ku.traning.kulocation;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class LocationActivity extends Activity implements LocationListener {
  private TextView latituteField, criteriaFeild, providerFeild;
  private TextView longitudeField;
  private LocationManager locationManager;
  private String provider;
  private Criteria criteria;

  
/** Called when the activity is first created. */

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_location);
    latituteField = (TextView) findViewById(R.id.TextView02);
    longitudeField = (TextView) findViewById(R.id.TextView04);
    criteriaFeild = (TextView) findViewById(R.id.TextView06);
    providerFeild = (TextView) findViewById(R.id.TextView08);

    // Get the location manager
    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    // Define the criteria how to select the locatioin provider 
    criteria = new Criteria();
    
    criteria.setAccuracy(Criteria.ACCURACY_FINE);
    criteria.setPowerRequirement(Criteria.POWER_HIGH);
    Log.i("Criteria",String.valueOf(criteria));
    
    //Get the best provider using the best criteria
    provider = locationManager.getBestProvider(criteria, false);
    Log.i("Provider",provider);
    
    //Get Location using the best provider
    Location location = locationManager.getLastKnownLocation(provider);

    // Initialize the location fields
    if (location != null) {
      Toast.makeText(this, "Provider " + provider + " has been selected.", Toast.LENGTH_LONG).show();
      onLocationChanged(location);
    } else {
      latituteField.setText("Location not available");
      longitudeField.setText("Location not available");
      
    }
  }

  /* Request updates at startup */
  @Override
  protected void onResume() {
    super.onResume();
    locationManager.requestLocationUpdates(provider, 400, 1, this);
  }

  /* Remove the locationlistener updates when Activity is paused */
  @Override
  protected void onPause() {
    super.onPause();
    locationManager.removeUpdates(this);
  }

  @Override
  public void onLocationChanged(Location location) {
    double lat =  location.getLatitude();
    double lng =  location.getLongitude();
    latituteField.setText(String.valueOf(lat));
    longitudeField.setText(String.valueOf(lng));
    criteriaFeild.setText(String.valueOf(criteria));
    providerFeild.setText(provider);
  }

  @Override
  public void onStatusChanged(String provider, int status, Bundle extras) {
    // TODO Auto-generated method stub

  }

  @Override
  public void onProviderEnabled(String provider) {
    Toast.makeText(this, "Enabled new provider " + provider,
        Toast.LENGTH_SHORT).show();

  }

  @Override
  public void onProviderDisabled(String provider) {
    Toast.makeText(this, "Disabled provider " + provider,
        Toast.LENGTH_SHORT).show();
  }
} 
