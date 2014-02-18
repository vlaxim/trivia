/* class SingletonScore
 * author : Fabien HUAULME
 * Date : 12/02/2014
 * Description : permet de gérer le score et de le passer dans chaque vue
 */
package com.vlaxim.trivia;

public final class SingletonScore {

	// L'utilisation du mot clé volatile permet, en Java version 5 et supérieur,
	// permet d'éviter le cas où "Singleton.instance" est non-nul,
	// mais pas encore "réellement" instancié.
	// De Java version 1.2 à 1.4, il est possible d'utiliser la classe
	// ThreadLocal.
	private static volatile SingletonScore instance = null;

	// D'autres attributs, classiques et non "static".
	private int score = 0;

	/**
	 * Constructeur de l'objet.
	 */
	private SingletonScore() {
		// La présence d'un constructeur privé supprime le constructeur public
		// par défaut.
		// De plus, seul le singleton peut s'instancier lui même.
		super();
	}

	/**
	 * Méthode permettant de renvoyer une instance de la classe Singleton
	 * 
	 * @return Retourne l'instance du singleton.
	 */
	public final static SingletonScore getInstance() {
		// Le "Double-Checked Singleton"/"Singleton doublement vérifié" permet
		// d'éviter un appel coûteux à synchronized,
		// une fois que l'instanciation est faite.
		if (SingletonScore.instance == null) {
			// Le mot-clé synchronized sur ce bloc empêche toute instanciation
			// multiple même par différents "threads".
			// Il est TRES important.
			synchronized (SingletonScore.class) {
				if (SingletonScore.instance == null) {
					SingletonScore.instance = new SingletonScore();
				}
			}
		}
		return SingletonScore.instance;
	}

	public int getScore() {
		return this.score;
	}

	// Incremente le score
	public void scoreIncrement() {
		score++;
	}

	// Remet le score à zéro
	public void scoreAZero() {
		this.score = 0;
	}

}