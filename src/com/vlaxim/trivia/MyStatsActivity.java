/* class Game Activity
 * author : Fabien HUAULME
 * Date : 12/02/2014
 * Description : decrit le fonctionnement du jeu
 */package com.vlaxim.trivia;

import java.util.List;

import com.vlaxim.dao.DaoMaster;
import com.vlaxim.dao.DaoSession;
import com.vlaxim.dao.DaoMaster.DevOpenHelper;
import com.vlaxim.dao.User;
import com.vlaxim.dao.UserDao.Properties;
import com.vlaxim.dao.Score;
import com.vlaxim.dao.ScoreDao;
import com.vlaxim.dao.UserDao;

import de.greenrobot.dao.query.QueryBuilder;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MyStatsActivity extends Activity {

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
		setContentView(R.layout.activity_my_stats);

		// Création des préférences
		settings = this.getSharedPreferences("com.vlaxim.trivia",
				Context.MODE_WORLD_READABLE);

		// On récupère la base de données
		DevOpenHelper helper = new DaoMaster.DevOpenHelper(
				MyStatsActivity.this, "trivia-db", null);
		db = helper.getWritableDatabase();
		daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();

		// Récupération de la liste des scores
		daoScore = daoSession.getScoreDao();
		QueryBuilder qb = daoScore.queryBuilder();
		qb.orderDesc(ScoreDao.Properties.Score);
		listAllScore = qb.list();

		// Récupération de l'id l'utilisateur courant
		String idUser = settings.getString("idUser", "null");
		// Récupération de l'objet user
		userDao = daoSession.getUserDao();
		listUser = userDao
				.queryBuilder()
				.where(Properties.Id.eq(idUser)).list();
		if (listUser.isEmpty() == false) {
			user = listUser.get(0);
		}

		// Remplissage de la liste avec un adapter
		liste = (ListView) findViewById(R.id.listViewMyScore);
		MyAdapter adapter = new MyAdapter(this, R.layout.row_score,
				listAllScore);
		liste.setAdapter(adapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_stats, menu);
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

			TextView tvNomPrenom = (TextView) view
					.findViewById(R.id.textViewNomPrenom);
			TextView tvScore = (TextView) view
					.findViewById(R.id.textViewScorePerso);

			Score score = this.getItem(position);

			tvNomPrenom.setText(user.getFirstName() + " " + user.getLastName());
			tvScore.setText("Score : " + String.valueOf(score.getScore()));
			return view;
		}

	}

}
