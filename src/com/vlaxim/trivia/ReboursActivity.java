package com.vlaxim.trivia;


import android.media.MediaPlayer;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class ReboursActivity extends Activity {
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rebours);
		
		
		CompteARebours compteARebours = new CompteARebours();
		compteARebours.execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.rebours, menu);
		return true;
	}
	
	private class CompteARebours extends AsyncTask<Void, Integer, Void>
	{
		private TextView number;
		
		@Override
		protected void onPreExecute() {
			number = (TextView) findViewById(R.id.textViewRebours);
			number.setText("3");	
		};

		@Override
		protected void onProgressUpdate(Integer... values){
			
			super.onProgressUpdate(values);
			
			switch (values[0]) {
			case 3:
				number.setText("2");
				break;
			case 2: 
				number.setText("1");
				break;
			case 1:
				number.setText("Go !");
				break;
			}
		}

		@Override
		protected Void doInBackground(Void... arg0) {

			MediaPlayer three = MediaPlayer.create(ReboursActivity.this, R.raw.three);
			three.start();
			SystemClock.sleep(1000);
			publishProgress(3);
			MediaPlayer two = MediaPlayer.create(ReboursActivity.this, R.raw.two);
			two.start();
			SystemClock.sleep(1000);
			publishProgress(2);
			MediaPlayer one = MediaPlayer.create(ReboursActivity.this, R.raw.one);
			one.start();
			SystemClock.sleep(1000);
			publishProgress(1);
			MediaPlayer go = MediaPlayer.create(ReboursActivity.this, R.raw.go);
			go.start();
		return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			//Lorsque le compte à rebours est terminé on lance le jeu
			Intent intentGame = new Intent(ReboursActivity.this, GameActivity.class);
			startActivity(intentGame);
		}
	}
 
}
