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
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.CursorJoiner.Result;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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

		// Récupération de l'id l'utilisateur courant
		String idUser = settings.getString("idUser", "null");
		// Récupération de l'objet user
		userDao = daoSession.getUserDao();
		listUser = userDao.queryBuilder().where(Properties.Id.eq(idUser))
				.list();
		if (listUser.isEmpty() == false) {
			user = listUser.get(0);
		}

		// Récupération de la liste des scores de l'utilisateur courant
		daoScore = daoSession.getScoreDao();

		listAllScore = daoScore.queryBuilder()
				.where(ScoreDao.Properties.UserId.eq(user.getId())).limit(15)
				.orderDesc(ScoreDao.Properties.Score).list();

		// Remplissage de la liste avec un adapter
		liste = (ListView) findViewById(R.id.listViewMyScore);
		MyAdapter adapter = new MyAdapter(this, R.layout.row_score,
				listAllScore);
		liste.setAdapter(adapter);
		
		//Au clic sur un élément de la liste on propose de partager le score
		liste.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

				Score scoreToShare = (Score) liste.getItemAtPosition(arg2);

				String shareString = "Génial ! Le score que j'ai réalisé sur Trivia est de : " + Integer.toString(scoreToShare.getScore());
				String resultatFinal = "";

				shareString = shareString + resultatFinal;

				Intent sendIntent = new Intent(
						android.content.Intent.ACTION_SEND);

				sendIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
						"Mon score Trivia");
				sendIntent.putExtra(android.content.Intent.EXTRA_TEXT,
						shareString);
				sendIntent.setType("text/plain");

				startActivity(Intent.createChooser(sendIntent,
						"Partager mon score "));

			}

		});
		

	}
	
	// Tuer l'activité lors de l'appuie sur le bouton de retour
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		this.finish();
		Intent intentHome = new Intent(MyStatsActivity.this,
				HomeActivity.class);
		startActivity(intentHome);
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

			TextView tvPseudo = (TextView) view
					.findViewById(R.id.textViewNomPrenom);
			TextView tvScore = (TextView) view
					.findViewById(R.id.textViewScorePerso);

			Score score = this.getItem(position);

			tvPseudo.setText(user.getPseudo());
			tvScore.setText("Score : " + String.valueOf(score.getScore()));
			return view;
		}

	}

}
