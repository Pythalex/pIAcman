package rl;

import java.util.ArrayList;
import java.util.Hashtable;

public class Qlearn {

	public double epsilon = 0.1; // parametre epsilon pour \epsilon-greedy
	public double alpha = 0.2; // taux d'apprentissage
	public double gamma = 0.9; // parametre gamma des eq. de Bellman/

	// Suggestions
	public int actions[];
	public Hashtable< Tuple<Integer,Integer>, Double> q;

	// Algorithme à utiliser
	private final int algo = 0; // 0 = qLearn ; 1 = SARSA

	// Constructeurs
	public Qlearn(int[] actions) {
		this.actions = actions;
		q = new Hashtable< Tuple<Integer,Integer>, Double>();
	}

	public Qlearn(int[] actions, double epsilon, double alpha, double gamma) {
		this.actions = actions;
		this.epsilon = epsilon;
		this.alpha = alpha;
		this.gamma = gamma;
		q = new Hashtable< Tuple<Integer,Integer>, Double>();
	}

	public void SARSA(int cell_state){
		// TODO : implementer SARSA
	}

	public void Q_LEARNING(int cell_state){
		// TODO : implementer Q learning
	}

	/**
	 * Apprend en fonction de la récompense de la case en cours.
	 */
	public void learn(int cell_state){
		if (this.algo == 0){
			Q_LEARNING();
		} else {
			SARSA();
		}
	}

	/**
	  * Determine une action a faire grâce au champ de vision.
	  */
	public int chooseAction(int etat){
		return 0;
	}
}
