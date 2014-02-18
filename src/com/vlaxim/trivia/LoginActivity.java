/* class Longin Activity
 * author : Maxime FLASQUIN
 * Date : 09/02/2014
 * Description : page de connexion de l'application
 */
package com.vlaxim.trivia;

import java.util.List;

import com.vlaxim.dao.DaoMaster;
import com.vlaxim.dao.DaoSession;
import com.vlaxim.dao.User;
import com.vlaxim.dao.UserDao;
import com.vlaxim.dao.DaoMaster.DevOpenHelper;
import com.vlaxim.dao.UserDao.Properties;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private EditText login;
	private EditText password;

	private Button connexion;
	private Button subscribe;

	private SQLiteDatabase db;
	private DaoMaster daoMaster;
	private DaoSession daoSession;
	private UserDao userDao;
	private SharedPreferences settings;

	private List<User> user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		connexion = (Button) findViewById(R.id.buttonConnect);
		login = (EditText) findViewById(R.id.editTextLogin);
		password = (EditText) findViewById(R.id.editTextPassword);

		// Création des préférences
		settings = this.getSharedPreferences("com.vlaxim.trivia",
				Context.MODE_WORLD_READABLE);

		connexion.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				DevOpenHelper helper = new DaoMaster.DevOpenHelper(
						LoginActivity.this, "trivia-db", null);
				db = helper.getWritableDatabase();
				daoMaster = new DaoMaster(db);
				daoSession = daoMaster.newSession();

				userDao = daoSession.getUserDao();

				// On récupère le login entré dans le champ
				String valueLogin = login.getText().toString();
				String valuePassword = password.getText().toString();

				user = userDao
						.queryBuilder()
						.where(Properties.Pseudo.eq(valueLogin),
								Properties.Password.eq(valuePassword)).list();

				if (user.size() == 1) {
					Toast.makeText(LoginActivity.this,
							"Authentification réussie", Toast.LENGTH_SHORT)
							.show();

					// Enregistrement de l'id de l'utilisateur dans les
					// préferences
					SharedPreferences.Editor editor = settings.edit();
					editor.putString("idUser",
							Long.toString(user.get(0).getId()));
					editor.commit();

					user.clear();

					Intent intentHome = new Intent(LoginActivity.this,
							HomeActivity.class);
					startActivity(intentHome);
				}

				else {
					login.setError("Mauvais identifiants");
					password.setError("");
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
