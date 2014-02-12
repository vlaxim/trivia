package com.vlaxim.trivia;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.vlaxim.dao.DaoMaster;
import com.vlaxim.dao.DaoSession;
import com.vlaxim.dao.Question;
import com.vlaxim.dao.QuestionDao;
import com.vlaxim.dao.DaoMaster.DevOpenHelper;

import de.greenrobot.dao.query.QueryBuilder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends Activity {

	private ProgressBar mProgressBar;
	private TextView txtQuestion;
	private EditText editAnswer;
	private Button validate;
	
	private List<Question> listAllQuestion;
	private List<Question> listQuestionGame;

	private SQLiteDatabase db;
	private DaoMaster daoMaster;
	private DaoSession daoSession;
	private QuestionDao questionDao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);

		// Récupération des éléments du layout
		txtQuestion = (TextView) findViewById(R.id.textViewQuestion);
		editAnswer = (EditText) findViewById(R.id.editTextAnswer);
		validate = (Button) findViewById(R.id.buttonValidate);
		final ProgressBarCompteur progressTask = new ProgressBarCompteur();

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
		listQuestionGame = new ArrayList<Question>();

		// Création de la liste des questions de la partie
		Random rand = new Random();
		for (int i = 1; i <= 5; i++) {
			int max = listAllQuestion.size();
			int nombreAleatoire = rand.nextInt(max);
			listQuestionGame.add(listAllQuestion.get(nombreAleatoire));
			listAllQuestion.remove(nombreAleatoire);
		}

		mProgressBar = (ProgressBar) findViewById(R.id.pBAsync);
		final ProgressBarCompteur timer = new ProgressBarCompteur();
		timer.execute();
		
		validate.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String answer = listQuestionGame.get(0).getAnswer();
				String answerPlayer = editAnswer.getText().toString();
				if (answer.equals(answerPlayer)) {
					Toast.makeText(GameActivity.this, "Bonne réponse", Toast.LENGTH_SHORT).show();

					timer.cancel(true);

				}
			}});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}
	
	//classe asynchrone qui affiche la progressbar chronomètre
	private class ProgressBarCompteur extends AsyncTask<Void, Integer, Void> {
	
	//classe asynchrone qui affiche la progressbar chronomètre
		@Override
		protected void onPreExecute() {
			txtQuestion.setText(listQuestionGame.get(0).getQuestion());
		};

		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
			mProgressBar.setProgress(values[0]);
			//ancienne place du validate et listener

		}

		@Override
		protected Void doInBackground(Void... arg0) {
			for (int i = 1; i <= 15; i++) {
				if (isCancelled()==true){
					break;
				}	
				SystemClock.sleep(1000);
				publishProgress(i);
				
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			Toast.makeText(GameActivity.this, "temps écoulé",
					Toast.LENGTH_SHORT).show();
		}
		
        @Override
        protected void onCancelled(){
            super.onCancelled();
            validate.setEnabled(false);
            validate.setText("Bonne Réponse");
            // traitement à effectuer si la tâche est annulée
        }
	}
}
