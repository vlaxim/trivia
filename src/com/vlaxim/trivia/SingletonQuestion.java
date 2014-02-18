/* class SingletonQuestion
 * author : Maxime Flasquin
 * Date : 12/02/2014
 * Description : permet de gérer le score et de le passer dans chaque vue
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
	// L'utilisation du mot clé volatile permet, en Java version 5 et supérieur,
	// permet d'éviter le cas où "Singleton.instance" est non-nul,
	// mais pas encore "réellement" instancié.
	// De Java version 1.2 à 1.4, il est possible d'utiliser la classe
	// ThreadLocal.
	private static volatile SingletonQuestion instance = null;

	// D'autres attributs, classiques et non "static".

	/**
	 * Constructeur de l'objet.
	 */
	private SingletonQuestion(Context context) {
		// La présence d'un constructeur privé supprime le constructeur public
		// par défaut.
		// De plus, seul le singleton peut s'instancier lui même.
		super();
		InitialiserListe(context);
	}

	/**
	 * Méthode permettant de renvoyer une instance de la classe Singleton
	 * 
	 * @return Retourne l'instance du singleton.
	 */
	public final static SingletonQuestion getInstance(Context context) {
		// Le "Double-Checked Singleton"/"Singleton doublement vérifié" permet
		// d'éviter un appel coûteux à synchronized,
		// une fois que l'instanciation est faite.
		if (SingletonQuestion.instance == null) {
			// Le mot-clé synchronized sur ce bloc empêche toute instanciation
			// multiple même par différents "threads".
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
		// On récupère la base de données
		DevOpenHelper helper = new DaoMaster.DevOpenHelper(context,
				"trivia-db", null);
		db = helper.getWritableDatabase();
		daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();

		// On récupère toutes les questions
		questionDao = daoSession.getQuestionDao();
		QueryBuilder qb = questionDao.queryBuilder();
		listAllQuestion = qb.list();
	}

	public List<Question> getListQuestion() {
		return this.listAllQuestion;
	}

}