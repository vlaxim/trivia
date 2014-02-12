/* class Subscribe Activity
 * author : Maxime FLASQUIN
 * Date : 11/02/2014
 * Description : gère l'inscription
 */
package com.vlaxim.trivia;

import com.vlaxim.dao.DaoMaster;
import com.vlaxim.dao.DaoMaster.DevOpenHelper;
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
				//Ajout de l'utilisateur si tous les champs sont remplis
				if (nom.getText().toString().equals("") == false
						&& prenom.getText().toString().equals("") == false
						&& pseudo.getText().toString().equals("") == false
						&& password.getText().toString().equals("") == false
						&& mail.getText().toString().equals("") == false){
					
					DevOpenHelper helper = new DaoMaster.DevOpenHelper(
							SubscribeActivity.this,
							"trivia-db",
							null);
			        db = helper.getWritableDatabase();
			        daoMaster = new DaoMaster(db);
			        daoSession = daoMaster.newSession();
			        userDao = daoSession.getUserDao();
					
			        User user = new User(null,
			        		prenom.getText().toString(),
			        		nom.getText().toString(),
			        		pseudo.getText().toString(),
			        		password.getText().toString(),
			        		mail.getText().toString());
			        
			        userDao.insert(user);
			        
			        Toast.makeText(SubscribeActivity.this,
			        		"Merci de vous être enregistré",
			        		Toast.LENGTH_SHORT).show();
			        
			        Intent intent = new Intent(SubscribeActivity.this, LoginActivity.class);
			        startActivity(intent);
		}
				
				
				//Sinon on affiche un toast indiquant les infos manquantes
				else {
					Toast.makeText(SubscribeActivity.this, 
							"Veuillez remplir tous les champs",
							Toast.LENGTH_LONG).show();
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
