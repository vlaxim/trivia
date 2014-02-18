/* class Subscribe Activity
 * author : Maxime FLASQUIN
 * Date : 11/02/2014
 * Description : gère l'inscription
 */
package com.vlaxim.trivia;

import java.util.List;

import com.vlaxim.dao.DaoMaster;
import com.vlaxim.dao.DaoMaster.DevOpenHelper;
import com.vlaxim.dao.UserDao.Properties;
import com.vlaxim.dao.DaoSession;
import com.vlaxim.dao.User;
import com.vlaxim.dao.UserDao;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SubscribeActivity extends Activity {

	private EditText nom;
	private EditText prenom;
	private EditText pseudo;
	private EditText password;
	private EditText mail;

	private Button subscribe;

	private SQLiteDatabase db;
	private DaoMaster daoMaster;
	private DaoSession daoSession;
	private UserDao userDao;

	private List<User> pseudoIsExist;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_subscribe);

		nom = (EditText) findViewById(R.id.editTextNom);
		prenom = (EditText) findViewById(R.id.editTextPrenom);
		pseudo = (EditText) findViewById(R.id.editTextPseudo);
		password = (EditText) findViewById(R.id.editTextPwd);
		mail = (EditText) findViewById(R.id.editTextMail);

		subscribe = (Button) findViewById(R.id.buttonSubscribe);

		subscribe.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				DevOpenHelper helper = new DaoMaster.DevOpenHelper(
						SubscribeActivity.this, "trivia-db", null);
				db = helper.getWritableDatabase();
				daoMaster = new DaoMaster(db);
				daoSession = daoMaster.newSession();
				userDao = daoSession.getUserDao();

				// On vérifie que le pseudo choisi n'existe pas déjà en base
				pseudoIsExist = userDao
						.queryBuilder()
						.where(Properties.Pseudo
								.eq(pseudo.getText().toString())).list();
				if (pseudoIsExist.size() >= 1) {
					pseudo.setError("Ce pseudo n'est pas disponible");
				} else {
					// Ajout de l'utilisateur si tous les champs sont remplis
					if (nom.getText().toString().equals("") == false
							&& prenom.getText().toString().equals("") == false
							&& pseudo.getText().toString().equals("") == false
							&& password.getText().toString().equals("") == false
							&& mail.getText().toString().equals("") == false) {

						User user = new User(null, prenom.getText().toString(),
								nom.getText().toString(), pseudo.getText()
										.toString(), password.getText()
										.toString(), mail.getText().toString());

						userDao.insert(user);

						// Enregistrement dans la base de données distante
						NetworkRequest app = (NetworkRequest) getApplication();
						new UserRequest(app, prenom.getText().toString(), nom
								.getText().toString(), pseudo.getText()
								.toString(), password.getText().toString(),
								mail.getText().toString());

						Toast.makeText(SubscribeActivity.this,
								"Merci de vous être enregistré",
								Toast.LENGTH_SHORT).show();

						Intent intent = new Intent(SubscribeActivity.this,
								LoginActivity.class);
						startActivity(intent);
					}

					// Sinon on affiche les erreurs
					else {
						if (nom.getText().toString().equals("")) {
							nom.setError("Veuillez remplir le nom");
						}

						if (prenom.getText().toString().equals("")) {
							prenom.setError("Veuillez remplir le prenom");
						}

						if (pseudo.getText().toString().equals("")) {
							pseudo.setError("Veuillez remplir le pseudo");
						}

						if (password.getText().toString().equals("")) {
							password.setError("Veuillez remplir le password");
						}

						if (mail.getText().toString().equals("")) {
							mail.setError("Veuillez remplir le mail");
						}
					}

				}
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.subscribe, menu);
		return true;
	}

}
