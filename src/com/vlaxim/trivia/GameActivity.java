/* class Game Activity
 * author : Maxime FLASQUIN
 * Date : 11/02/2014
 * Description : decrit le fonctionnement du jeu
 */
package com.vlaxim.trivia;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.vlaxim.dao.DaoMaster;
import com.vlaxim.dao.DaoSession;
import com.vlaxim.dao.Question;
import com.vlaxim.dao.QuestionDao;
import com.vlaxim.dao.DaoMaster.DevOpenHelper;
import com.vlaxim.dao.Score;
import com.vlaxim.dao.ScoreDao;

import de.greenrobot.dao.query.QueryBuilder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Contacts.Settings;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.vlaxim.trivia.SingletonScore;

public class GameActivity extends Activity {

	private ProgressBar mProgressBar;
	private TextView txtQuestion;
	private EditText editAnswer;
	private TextView txtScore;
	private Button validate;
	private ProgressBar progressTransition;
	private TextView scoreFinal;
	private Button quitter;
	private Button rejouer;
	private ProgressBarCompteur timer;

	private List<Question> listAllQuestion;
	private List<Question> listQuestionGame;

	private SQLiteDatabase db;
	private DaoMaster daoMaster;
	private DaoSession daoSession;
	private QuestionDao questionDao;
	private ScoreDao scoreDao;
	private Question question;
	private SharedPreferences settings;

	// Création de la dialog en cas de perte
	@Override
	protected Dialog onCreateDialog(int score) {
		final Dialog box;
		box = new Dialog(this);
		box.setTitle("Vous avez perdu !");
		box.setContentView(R.layout.dialog_restart);

		scoreFinal = (TextView) box.findViewById(R.id.textViewScoreFinal);
		quitter = (Button) box.findViewById(R.id.buttonLeave);
		rejouer = (Button) box.findViewById(R.id.buttonReplay);

		scoreFinal.setText(Integer.toString(SingletonScore.getInstance()
				.getScore()));

		quitter.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intentHome = new Intent(GameActivity.this,
						HomeActivity.class);
				SingletonScore.getInstance().scoreAZero();
				startActivity(intentHome);
				box.cancel();
				finish();
			}
		});

		rejouer.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				SingletonScore.getInstance().scoreAZero();
				recreate();
				box.cancel();
			}
		});

		return box;
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);

		// Création des préférences
		settings = this.getSharedPreferences("com.vlaxim.trivia",
				Context.MODE_WORLD_READABLE);

		// Récupération des éléments du layout
		txtQuestion = (TextView) findViewById(R.id.textViewQuestion);
		editAnswer = (EditText) findViewById(R.id.editTextAnswer);
		validate = (Button) findViewById(R.id.buttonValidate);
		mProgressBar = (ProgressBar) findViewById(R.id.pBAsync);
		progressTransition = (ProgressBar) findViewById(R.id.progressBarTransition);
		txtScore = (TextView) findViewById(R.id.textViewScore);
		timer = new ProgressBarCompteur();

		// On affiche le score
		txtScore.setText(Integer.toString(SingletonScore.getInstance()
				.getScore()));
		// On initialise à 0 la progressBar
		mProgressBar.setProgress(0);
		// On cache la progressBar de transition
		progressTransition.setVisibility(View.INVISIBLE);

		// On récupère la base de données
		DevOpenHelper helper = new DaoMaster.DevOpenHelper(GameActivity.this,
				"trivia-db", null);
		db = helper.getWritableDatabase();
		daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();

		// On récupère toutes les questions
		questionDao = daoSession.getQuestionDao();
		QueryBuilder qb = questionDao.queryBuilder();
		listAllQuestion = qb.list();


		question = newQuestion();

		// On lance le thread du compteur
		timer.execute();

		// On écoute le bouton valider la réponse
		validate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String answer = question.getAnswer();
				String answerPlayer = editAnswer.getText().toString();

				// Si la réponse est correcte..
				if (answer.equals(answerPlayer)) {
					// On incrémente le score
					SingletonScore.getInstance().scoreIncrement();
					// On affiche un mesage
					Toast.makeText(GameActivity.this, "Bonne réponse",
							Toast.LENGTH_SHORT).show();
					// ..On stop le thread
					timer.cancel(true);

					// On lance le thread de transition
					ProgressBarTransition transition = new ProgressBarTransition();
					transition.execute();
				} else {
					// Mauvaise réponse
					Toast.makeText(GameActivity.this, "Mauvaise réponse",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

	}
	



	// Tuer l'activité lors de l'appuie sur le bouton de retour
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		timer.cancel(true);
		this.finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}

	
	

	
	// classe asynchrone qui affiche la progressbar chronomètre
	private class ProgressBarCompteur extends AsyncTask<Void, Integer, Void> {

		// appelé avant
		@Override
		protected void onPreExecute() {
			validate.setEnabled(true);
			validate.setText("Valider");
			txtQuestion.setText(question.getQuestion());
		};

		// appelé pendant le thread, modifie l'UI principale
		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
			mProgressBar.setProgress(values[0]);
			// ancienne place du validate et listener

		}

		// tache de fond
		@Override
		protected Void doInBackground(Void... arg0) {
			for (int i = 1; i <= 15; i++) {
				if (isCancelled() == true) {
					break;
				}
				SystemClock.sleep(1000);
				publishProgress(i);

			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {

			// Enregistrement du score dans la base de données
			scoreDao = daoSession.getScoreDao();
			String idUser = settings.getString("idUser", "null");
			if (idUser.equals("null") == false) {
			Score leScore = new Score(null, SingletonScore.getInstance()
					.getScore(), Integer.parseInt(idUser));
			scoreDao.insert(leScore);
			}

			// Le joueur n'a pas réussi à répondre dans le temps imparti
			showDialog(SingletonScore.getInstance().getScore());

		}

		// executer si la tache ne fini pas ( à l'appel de cancel)
		@Override
		protected void onCancelled() {
			super.onCancelled();
			validate.setEnabled(false);
			validate.setText("Bonne Réponse");
		}
	}

	// classe asynchrone qui affiche la progressbar chronomètre
	private class ProgressBarTransition extends AsyncTask<Void, Void, Void> {

		// appelé avant
		@Override
		protected void onPreExecute() {
			editAnswer.setText("");
			txtQuestion.setVisibility(View.INVISIBLE);
			mProgressBar.setVisibility(View.INVISIBLE);
			editAnswer.setVisibility(View.INVISIBLE);
			validate.setVisibility(View.INVISIBLE);
			progressTransition.setVisibility(View.VISIBLE);
		}

		// tache de fond pour la transition
		@Override
		protected Void doInBackground(Void... arg0) {
			SystemClock.sleep(3000);
			return null;
		}

		// executer a la fin de la tache
		@Override
		protected void onPostExecute(Void result) {
			// On relance une question et un nouveau thread
			recreate();
		}

	}

	public Question newQuestion() {
		// Création de la liste des questions de la partie
		Random rand = new Random();
		int max = listAllQuestion.size();
		int nombreAleatoire = rand.nextInt(max);
		question = listAllQuestion.get(nombreAleatoire);
		listAllQuestion.remove(nombreAleatoire);
		return question;
	}
}
