package ku.traning.kusharedpreference;

import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {

	SharedPreferences prefs;
	String prefName = "myPrefs";
	TextView txt1, txt2, txt3;

	public void readPrefValues() {
		prefs = getSharedPreferences(prefName, MODE_PRIVATE);
		float temperature = prefs.getFloat("temperature", 50);
		boolean authenticated = prefs.getBoolean("authenticated", false);
		String username = prefs.getString("username", "");

		txt1.setText(String.valueOf(authenticated));
		txt2.setText(username);
		txt3.setText(String.valueOf(temperature));

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		txt1 = (TextView) findViewById(R.id.txt1);
		txt2 = (TextView) findViewById(R.id.txt2);
		txt3 = (TextView) findViewById(R.id.txt3);

		prefs = getSharedPreferences(prefName, MODE_PRIVATE);

		SharedPreferences.Editor editor = prefs.edit();

		editor.putFloat("temperature", 10);
		editor.putBoolean("authenticated", true);
		editor.putString("username", "Nirab Pudasaini");

		editor.commit();

		readPrefValues();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
