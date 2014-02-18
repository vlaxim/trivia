/* class Home Activity
 * author : Maxime FLASQUIN
 * Date : 09/02/2014
 * Description : donne acces aux differentes vues de l'application ,jeu stats..
 */

package com.vlaxim.trivia;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends Activity {

	private String[] drawerListViewItems;
	private DrawerLayout drawerLayout;
	private ListView drawerListView;
	private ActionBarDrawerToggle actionBarDrawerToggle;

	private Button play;
	private Button myStats;
	
	//Position des items du menu
	private int jouer = 0;
	private int mesStats = 1;
	private int lesStats = 2;
	private int regles = 3;
	private int aPropos = 4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		// get list items from strings.xml
		drawerListViewItems = getResources().getStringArray(R.array.items);
		// get ListView defined in activity_main.xml
		drawerListView = (ListView) findViewById(R.id.left_drawer);

		// Set the adapter for the list view
		drawerListView.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_listview_item, drawerListViewItems));

		// 2. App Icon
		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		// 2.1 create ActionBarDrawerToggle
		actionBarDrawerToggle = new ActionBarDrawerToggle(
			this,/* host Activity */
			drawerLayout, /* DrawerLayout object */
			R.drawable.ic_drawer,/* nav drawer icon to replace 'Up' caret */
			R.string.drawer_open, /* "open drawer" description */
			R.string.drawer_close /* "close drawer" description */
		);

		// 2.2 Set actionBarDrawerToggle as the DrawerListener
		drawerLayout.setDrawerListener(actionBarDrawerToggle);

		// 2.3 enable and show "up" arrow
		getActionBar().setDisplayHomeAsUpEnabled(true);

		// just styling option
		drawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);

		drawerListView.setOnItemClickListener(new DrawerItemClickListener());

		// Actions sur les boutons de la page
		play = (Button) findViewById(R.id.buttonPlay);
		play.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intentRebours = new Intent(HomeActivity.this,
						ReboursActivity.class);
				startActivity(intentRebours);
				HomeActivity.this.finish();
			}
		});
		
		myStats = (Button) findViewById(R.id.buttonMyStats);
		myStats.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intentMyStats = new Intent(HomeActivity.this, MyStatsActivity.class);
				startActivity(intentMyStats);
				HomeActivity.this.finish();
			}
		});

	}
	
	// Tuer l'activité lors de l'appuie sur le bouton de retour
	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		this.finish();
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		actionBarDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		actionBarDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		// call ActionBarDrawerToggle.onOptionsItemSelected(), if it returns
		// true
		// then it has handled the app icon touch event

		if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView parent,
				View view,
				int position,
				long id) {
			//Au clic sur jouer
			if (position == jouer) {
				Intent intent = new Intent(HomeActivity.this, ReboursActivity.class);
				startActivity(intent);
				HomeActivity.this.finish();
			}
			
			//Au clic sur Mes stats
			if (position == mesStats) {
				Intent intent = new Intent(HomeActivity.this, MyStatsActivity.class);
				startActivity(intent);
				HomeActivity.this.finish();
			}
			
			//Au clic sur Les stats
			if(position == lesStats) {
				Intent intent = new Intent(HomeActivity.this, StatsActivity.class);
				startActivity(intent);
				HomeActivity.this.finish();
			}
			
			//Au clic sur Règles du jeu
			if(position == regles) {
				Intent intent = new Intent(HomeActivity.this, RulesActivity.class);
				startActivity(intent);
				HomeActivity.this.finish();
			}
			
			//Au clic sur a propos
			if(position == aPropos) {
				Intent intent = new Intent(HomeActivity.this, AboutActivity.class);
				startActivity(intent);
				HomeActivity.this.finish();
			}
			drawerLayout.closeDrawer(drawerListView);

		}
	}
}
