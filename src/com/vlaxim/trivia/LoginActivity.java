package com.vlaxim.trivia;

import java.util.List;

import com.vlaxim.dao.DaoMaster;
import com.vlaxim.dao.DaoSession;
import com.vlaxim.dao.Question;
import com.vlaxim.dao.QuestionDao;
import com.vlaxim.dao.User;
import com.vlaxim.dao.UserDao;
import com.vlaxim.dao.DaoMaster.DevOpenHelper;
import com.vlaxim.dao.UserDao.Properties;

import android.R.anim;
import android.os.Bundle;
import android.app.Activity;
import android.app.DownloadManager.Query;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;
import de.greenrobot.dao.query.QueryBuilder;
import de.greenrobot.dao.query.WhereCondition.StringCondition;

public class LoginActivity extends Activity {

	private EditText login;
	private EditText password;

	private Button connexion;
	private Button subscribe;

	private SQLiteDatabase db;
	private DaoMaster daoMaster;
	private DaoSession daoSession;
	private UserDao userDao;
	private QuestionDao questionDao;

	private List<User> user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		connexion = (Button) findViewById(R.id.buttonConnect);
		login = (EditText) findViewById(R.id.editTextLogin);
		password = (EditText) findViewById(R.id.editTextPassword);

		connexion.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				DevOpenHelper helper = new DaoMaster.DevOpenHelper(
						LoginActivity.this, "trivia-db", null);
				db = helper.getWritableDatabase();
				daoMaster = new DaoMaster(db);
				daoSession = daoMaster.newSession();

				userDao = daoSession.getUserDao();
				questionDao = daoSession.getQuestionDao();

				//Récupération des questions
				Question question1 = new Question(null, getString(R.string.question1),
						getString(R.string.answer1));
				Question question2 = new Question(null, getString(R.string.question2),
						getString(R.string.answer2));
				Question question3 = new Question(null, getString(R.string.question3),
						getString(R.string.answer3));
				Question question4 = new Question(null, getString(R.string.question4),
						getString(R.string.answer4));
				Question question5 = new Question(null, getString(R.string.question5),
						getString(R.string.answer5));
				
				questionDao.insert(question1);
				questionDao.insert(question2);
				questionDao.insert(question3);
				questionDao.insert(question4);
				questionDao.insert(question5);
				
				
				// On récupère le login entré dans le champ
				String valueLogin = login.getText().toString();
				String valuePassword = password.getText().toString();

				user = userDao
						.queryBuilder()
						.where(Properties.Pseudo.eq(valueLogin),
								Properties.Password.eq(valuePassword)).list();

				if (user.size() == 1) {
					Toast.makeText(LoginActivity.this,
							"Authentification réussie", Toast.LENGTH_LONG)
							.show();
					user.clear();

					Intent intentHome = new Intent(LoginActivity.this,
							HomeActivity.class);
					startActivity(intentHome);
				}

				else {
					Toast.makeText(LoginActivity.this, "Mauvais identifiants",
							Toast.LENGTH_LONG).show();
				}

			}
		});

		subscribe = (Button) findViewById(R.id.buttonSubscribeLogin);

		subscribe.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// Action a éxécuter lors du clic sur je m'inscrit
				Intent intent = new Intent(LoginActivity.this,
						SubscribeActivity.class);
				startActivity(intent);

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

}
