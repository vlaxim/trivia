/* class SingletonScore
 * author : Fabien HUAULME
 * Date : 12/02/2014
 * Description : permet de g�rer le score et de le passer dans chaque vue
 */
package com.vlaxim.trivia;

public final class SingletonScore {

	// L'utilisation du mot cl� volatile permet, en Java version 5 et sup�rieur,
	// permet d'�viter le cas o� "Singleton.instance" est non-nul,
	// mais pas encore "r�ellement" instanci�.
	// De Java version 1.2 � 1.4, il est possible d'utiliser la classe
	// ThreadLocal.
	private static volatile SingletonScore instance = null;

	// D'autres attributs, classiques et non "static".
	private int score = 0;

	/**
	 * Constructeur de l'objet.
	 */
	private SingletonScore() {
		// La pr�sence d'un constructeur priv� supprime le constructeur public
		// par d�faut.
		// De plus, seul le singleton peut s'instancier lui m�me.
		super();
	}

	/**
	 * M�thode permettant de renvoyer une instance de la classe Singleton
	 * 
	 * @return Retourne l'instance du singleton.
	 */
	public final static SingletonScore getInstance() {
		// Le "Double-Checked Singleton"/"Singleton doublement v�rifi�" permet
		// d'�viter un appel co�teux � synchronized,
		// une fois que l'instanciation est faite.
		if (SingletonScore.instance == null) {
			// Le mot-cl� synchronized sur ce bloc emp�che toute instanciation
			// multiple m�me par diff�rents "threads".
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

	// Remet le score � z�ro
	public void scoreAZero() {
		this.score = 0;
	}

}