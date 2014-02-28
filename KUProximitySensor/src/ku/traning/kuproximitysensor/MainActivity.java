package ku.traning.kuproximitysensor;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends Activity {

	LocationManager lm;
	PendingIntent pendingIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		lm = (LocationManager) getSystemService(LOCATION_SERVICE);
		if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			startActivity(new Intent(
					android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		pendingIntent = PendingIntent.getActivity(
				this,
				0,
				new Intent(android.content.Intent.ACTION_VIEW, Uri
						.parse("http://www.kathmandulivinglabs.org")), 0);
		lm.addProximityAlert(27.72278, 85.33094, 20, -1, pendingIntent);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		lm.removeProximityAlert(pendingIntent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
