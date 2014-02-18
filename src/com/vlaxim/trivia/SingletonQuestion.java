/* class SingletonQuestion
 * author : Maxime Flasquin
 * Date : 12/02/2014
 * Description : permet de g�rer le score et de le passer dans chaque vue
 */
package com.vlaxim.trivia;

import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.vlaxim.dao.DaoMaster;
import com.vlaxim.dao.DaoSession;
import com.vlaxim.dao.Question;
import com.vlaxim.dao.QuestionDao;
import com.vlaxim.dao.DaoMaster.DevOpenHelper;

import de.greenrobot.dao.query.QueryBuilder;

public final class SingletonQuestion {

	private SQLiteDatabase db;
	private DaoMaster daoMaster;
	private DaoSession daoSession;
	private QuestionDao questionDao;

	private List<Question> listAllQuestion;
	// L'utilisation du mot cl� volatile permet, en Java version 5 et sup�rieur,
	// permet d'�viter le cas o� "Singleton.instance" est non-nul,
	// mais pas encore "r�ellement" instanci�.
	// De Java version 1.2 � 1.4, il est possible d'utiliser la classe
	// ThreadLocal.
	private static volatile SingletonQuestion instance = null;

	// D'autres attributs, classiques et non "static".

	/**
	 * Constructeur de l'objet.
	 */
	private SingletonQuestion(Context context) {
		// La pr�sence d'un constructeur priv� supprime le constructeur public
		// par d�faut.
		// De plus, seul le singleton peut s'instancier lui m�me.
		super();
		InitialiserListe(context);
	}

	/**
	 * M�thode permettant de renvoyer une instance de la classe Singleton
	 * 
	 * @return Retourne l'instance du singleton.
	 */
	public final static SingletonQuestion getInstance(Context context) {
		// Le "Double-Checked Singleton"/"Singleton doublement v�rifi�" permet
		// d'�viter un appel co�teux � synchronized,
		// une fois que l'instanciation est faite.
		if (SingletonQuestion.instance == null) {
			// Le mot-cl� synchronized sur ce bloc emp�che toute instanciation
			// multiple m�me par diff�rents "threads".
			// Il est TRES important.
			synchronized (SingletonQuestion.class) {
				if (SingletonQuestion.instance == null) {
					SingletonQuestion.instance = new SingletonQuestion(context);
				}
			}
		}
		return SingletonQuestion.instance;
	}

	public void InitialiserListe(Context context) {
		// On r�cup�re la base de donn�es
		DevOpenHelper helper = new DaoMaster.DevOpenHelper(context,
				"trivia-db", null);
		db = helper.getWritableDatabase();
		daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();

		// On r�cup�re toutes les questions
		questionDao = daoSession.getQuestionDao();
		QueryBuilder qb = questionDao.queryBuilder();
		listAllQuestion = qb.list();
	}

	public List<Question> getListQuestion() {
		return this.listAllQuestion;
	}

}