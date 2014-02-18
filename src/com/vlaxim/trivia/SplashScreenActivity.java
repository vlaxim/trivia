/* class SplashScreenActivity
 * author : Fabien HUAULME
 * Date : 11/02/2014
 * Description : decrit le fonctionnement du splash screen layout
 */

package com.vlaxim.trivia;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vlaxim.dao.DaoMaster;
import com.vlaxim.dao.DaoSession;
import com.vlaxim.dao.Question;
import com.vlaxim.dao.QuestionDao;
import com.vlaxim.dao.Score;
import com.vlaxim.dao.ScoreDao;
import com.vlaxim.dao.DaoMaster.DevOpenHelper;
import com.vlaxim.dao.User;
import com.vlaxim.dao.UserDao;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class SplashScreenActivity extends Activity {

	// Splash screen timer
	private static int SPLASH_TIME_OUT = 3000;

	private RequestQueue mRequestQueue;
	private static final String SCORE_JSON_URL = "http://max-site.fr/ws/public/score/index.php?method=getAll";
	private static final String QUESTION_JSON_URL = "http://max-site.fr/ws/public/question/index.php?method=getAll";
	private static final String USER_JSON_URL = "http://max-site.fr/ws/public/user/index.php?method=getAll";

	private SQLiteDatabase db;
	private DaoMaster daoMaster;
	private DaoSession daoSession;
	private ScoreDao daoScore;
	private QuestionDao daoQuestion;
	private UserDao daoUser;
	private Score leScore;
	private Question laQuestion;
	private User unUser;

	/*
	 * Test si le téléphone a accès à une connexion internet
	 */
	public boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnected()) {
			return true;
		}
		return false;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_screen_splash);

		// On commence par tester si l'utilisateur a une connexion internet
		if (isOnline() == false) {
			Toast.makeText(SplashScreenActivity.this,
					"Veuillez vous connecter à internet", Toast.LENGTH_SHORT)
					.show();
			this.finish();
		}

		else {

			// On récupère notre RequestQueue
			NetworkRequest app = (NetworkRequest) getApplication();
			mRequestQueue = app.getVolleyRequestQueue();

			// On récupère la base de données
			DevOpenHelper helper = new DaoMaster.DevOpenHelper(
					SplashScreenActivity.this, "trivia-db", null);
			db = helper.getWritableDatabase();
			daoMaster = new DaoMaster(db);
			daoSession = daoMaster.newSession();
			daoScore = daoSession.getScoreDao();
			daoQuestion = daoSession.getQuestionDao();
			daoUser = daoSession.getUserDao();
			// On vide les tables pour ne pas faire de doublons
			daoQuestion.deleteAll();
			daoScore.deleteAll();
			daoUser.deleteAll();

			// On récupère les scores
			RequestScore();
			// On récupère les questions
			RequestQuestion();
			// On récupère les utilisateurs
			RequestUser();

			new Handler().postDelayed(new Runnable() {

				/*
				 * Showing splash screen with a timer. This will be useful when
				 * you want to show case your app logo / company
				 */

				@Override
				public void run() {
					// This method will be executed once the timer is over
					// Start your app main activity
					Intent i = new Intent(SplashScreenActivity.this,
							LoginActivity.class);
					startActivity(i);

					// close this activity
					finish();
				}
			}, SPLASH_TIME_OUT);

		}
	}

	/*
	 * Récupère les scores dans la bdd distante
	 */
	private void RequestScore() {

		// Requête réseau qui récupère les scores
		JsonArrayRequest requestScore = new JsonArrayRequest(SCORE_JSON_URL,
				new Response.Listener<JSONArray>() {
					@Override
					public void onResponse(JSONArray jsonArray) {
						// Ce code est appelé quand la requête réussi
						for (int i = 0; i < jsonArray.length(); i++) {
							try {
								JSONObject score = jsonArray.getJSONObject(i);
								Long id = score.getLong("id");
								int idUser = score.getInt("idUser");
								int scorePlayer = score.getInt("score");

								leScore = new Score(id, scorePlayer, idUser);
								daoScore.insert(leScore);

							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}

					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {
						// Le code suivant est appelé lorsque Volley n'a pas
						// réussi à récupérer le résultat de la requête
						// Toast.makeText(SplashScreenActivity.this,
						// "Error while getting JSON: " +
						// volleyError.getMessage(), Toast.LENGTH_SHORT).show();
					}
				});
		requestScore.setTag(this);

		// On ajoute la Request au RequestQueue pour la lancer
		mRequestQueue.add(requestScore);

	}

	/*
	 * Récupère les questions dans la bdd distante
	 */
	private void RequestQuestion() {

		// Requête réseau qui récupère les questions
		JsonArrayRequest requestQuestion = new JsonArrayRequest(
				QUESTION_JSON_URL, new Response.Listener<JSONArray>() {
					@Override
					public void onResponse(JSONArray jsonArray) {
						// Ce code est appelé quand la requête réussi
						for (int i = 0; i < jsonArray.length(); i++) {
							try {
								JSONObject question = jsonArray
										.getJSONObject(i);
								Long id = question.getLong("id");
								String txtQuestion = question
										.getString("question");
								String answer = question.getString("answer");

								laQuestion = new Question(id, txtQuestion,
										answer);
								daoQuestion.insert(laQuestion);

							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}

					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {
						// Le code suivant est appelé lorsque Volley n'a pas
						// réussi à récupérer le résultat de la requête
						// Toast.makeText(SplashScreenActivity.this,
						// "Error while getting JSON: " +
						// volleyError.getMessage(), Toast.LENGTH_SHORT).show();
					}
				});
		requestQuestion.setTag(this);

		// On ajoute la Request au RequestQueue pour la lancer
		mRequestQueue.add(requestQuestion);
	}

	/*
	 * Récupère les utilisateurs dans la bdd distante
	 */
	private void RequestUser() {
		// Requête réseau qui récupère les utilisateurs
		JsonArrayRequest requestQuestion = new JsonArrayRequest(USER_JSON_URL,
				new Response.Listener<JSONArray>() {
					@Override
					public void onResponse(JSONArray jsonArray) {
						// Ce code est appelé quand la requête réussi
						for (int i = 0; i < jsonArray.length(); i++) {
							try {
								JSONObject user = jsonArray.getJSONObject(i);
								Long id = user.getLong("id");
								String firstName = user.getString("firstName");
								String lastName = user.getString("lastName");
								String pseudo = user.getString("pseudo");
								String password = user.getString("password");
								String mail = user.getString("mail");

								unUser = new User(id, firstName, lastName,
										pseudo, password, mail);
								daoUser.insert(unUser);

							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}

					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {
						// Le code suivant est appelé lorsque Volley n'a pas
						// réussi à récupérer le résultat de la requête
						// Toast.makeText(SplashScreenActivity.this,
						// "Error while getting JSON: " +
						// volleyError.getMessage(), Toast.LENGTH_SHORT).show();
					}
				});
		requestQuestion.setTag(this);

		// On ajoute la Request au RequestQueue pour la lancer
		mRequestQueue.add(requestQuestion);
	}

	@Override
	protected void onStop() {
		mRequestQueue.cancelAll(this);
		super.onStop();
	}

}
