package com.vlaxim.trivia;

import com.vlaxim.dao.DaoMaster;
import com.vlaxim.dao.DaoMaster.DevOpenHelper;
import com.vlaxim.dao.DaoSession;
import com.vlaxim.dao.User;
import com.vlaxim.dao.UserDao;

import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {

	private SQLiteDatabase db;

	private DaoMaster daoMaster;
	private DaoSession daoSession;
	private UserDao userDao;

	private Cursor cursor;

	private User user;
	private User user2;

	private TextView txtId;
	private TextView txtNom;
	private TextView txtPrenom;
	private TextView txtPseudo;
	private TextView txtPassword;
	private TextView txtMail;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Création de la base de données
		DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "trivia-db",
				null);
		db = helper.getWritableDatabase();

		// Récupération des DAO
		daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();
		userDao = daoSession.getUserDao();

		user = new User((long) 1, "Maxime", "Flasquin", "maxou", "mdp",
				"maxdu35@gmail.com");
		userDao.insert(user);
		Log.d("DaoExample", "Inserted new note, ID: " + user.getId());

		txtId = (TextView) this.findViewById(R.id.textViewId);
		txtNom = (TextView) this.findViewById(R.id.textViewNom);
		txtPrenom = (TextView) this.findViewById(R.id.textViewPrenom);
		txtPseudo = (TextView) this.findViewById(R.id.textViewPseudo);
		txtPassword = (TextView) this.findViewById(R.id.textViewPassword);
		txtMail = (TextView) this.findViewById(R.id.textViewEmail);

		user2 = userDao.loadByRowId(1);

		txtId.setText(user2.getId().toString());
		txtNom.setText(user2.getLastName());
		txtPrenom.setText(user2.getFirstName());
		txtPseudo.setText(user2.getPseudo());
		txtPassword.setText(user2.getPassword());
		txtMail.setText(user2.getMail());

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
