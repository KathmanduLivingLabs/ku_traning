package ku.traning.kudatabase;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MapActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		TextView tv = (TextView) findViewById(R.id.textView1);
		Intent intent = getIntent();
		String loc = intent.getStringExtra("Location");
		tv.setText(loc);

	}

}
