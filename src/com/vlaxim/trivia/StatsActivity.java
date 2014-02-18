/* class StatsActivity
 * author : Maxime FLASQUIN
 * Date : 18/02/2014
 * Description : Affiche les statistiques globaux
 */

package com.vlaxim.trivia;

import java.util.List;

import com.vlaxim.dao.DaoMaster;
import com.vlaxim.dao.DaoSession;
import com.vlaxim.dao.Score;
import com.vlaxim.dao.ScoreDao;
import com.vlaxim.dao.User;
import com.vlaxim.dao.UserDao;
import com.vlaxim.dao.DaoMaster.DevOpenHelper;
import com.vlaxim.dao.UserDao.Properties;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class StatsActivity extends Activity {

	private SQLiteDatabase db;
	private DaoMaster daoMaster;
	private DaoSession daoSession;
	private ScoreDao daoScore;
	private List<Score> listAllScore;
	private ListView liste;
	private SharedPreferences settings;
	private UserDao userDao;
	private List<User> listUser;
	private User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stats);

		// On récupère la base de données
		DevOpenHelper helper = new DaoMaster.DevOpenHelper(StatsActivity.this,
				"trivia-db", null);
		db = helper.getWritableDatabase();
		daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();
		userDao = daoSession.getUserDao();

		// Récupération de la liste des scores
		daoScore = daoSession.getScoreDao();

		listAllScore = daoScore.queryBuilder().limit(15)
				.orderDesc(ScoreDao.Properties.Score).list();

		// Remplissage de la liste avec un adapter
		liste = (ListView) findViewById(R.id.listViewLesStats);
		MyAdapter adapter = new MyAdapter(this, R.layout.row_score,
				listAllScore);
		liste.setAdapter(adapter);
	}

	// Tuer l'activité lors de l'appuie sur le bouton de retour
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		this.finish();
		Intent intentHome = new Intent(StatsActivity.this, HomeActivity.class);
		startActivity(intentHome);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.stats, menu);
		return true;
	}

	private class MyAdapter extends ArrayAdapter<Score> {
		private Context context;
		private int resource;
		private LayoutInflater Inflater;

		public MyAdapter(Context context, int resource, List<Score> items) {
			super(context, resource, items);
			this.context = context;
			this.resource = resource;
			this.Inflater = LayoutInflater.from(this.context);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = Inflater.inflate(this.resource, null);

			TextView tvPseudo = (TextView) view
					.findViewById(R.id.textViewNomPrenom);
			TextView tvScore = (TextView) view
					.findViewById(R.id.textViewScorePerso);

			// Récupération du score courant
			Score score = this.getItem(position);

			// Récupération du user
			listUser = userDao.queryBuilder()
					.where(UserDao.Properties.Id.eq(score.getId())).list();

			tvPseudo.setText(score.getUser().getPseudo().toString());
			tvScore.setText("Score : " + String.valueOf(score.getScore()));
			return view;
		}

	}

}
