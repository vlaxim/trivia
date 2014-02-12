/* class Game Activity
 * author : Fabien HUAULME
 * Date : 12/02/2014
 * Description : decrit le fonctionnement du jeu
 */package com.vlaxim.trivia;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MyStatsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_stats);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_stats, menu);
		return true;
	}

}
