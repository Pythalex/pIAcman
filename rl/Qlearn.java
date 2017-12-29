package rl;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Random;


public class Qlearn {

	public double epsilon = 0.2; // parametre epsilon pour \epsilon-greedy
	public double alpha = 0.2; // taux d'apprentissage
	public double gamma = 0.9; // parametre gamma des eq. de Bellman/
	public int t = 0;

	// tableau des actions possibles
	public int actions[];
	public final int nombreActions = 8;
	public Hashtable<Integer, Double[]> q; // s -> a -> q

	public double last_reward = 0;
	public int a_prime;

	// Algorithme à utiliser
	private final int algo = 1; // 0 = qLearning ; 1 = SARSA

	// Constructeurs
	public Qlearn(int[] actions) {
		this.actions = actions;
		q = new Hashtable<>();
	}

	public Qlearn(int[] actions, double epsilon, double alpha, double gamma) {
		this.actions = actions;
		this.epsilon = epsilon;
		this.alpha = alpha;
		this.gamma = gamma;
		q = new Hashtable<>();
	}

	public void SARSA(int s, int a, int s_prime){
		a_prime = e_greedy(q.get(s_prime));

		q.get(s)[a] = q.get(s)[a] + alpha * (last_reward + gamma*q.get(s_prime)[a_prime] - q.get(s)[a]);
	}

	public void Q_LEARNING(int s, int a, int s_prime){

		// On choisit l'action considérée comme étant la meilleure par l'IA
		a_prime = argmax(q.get(s_prime));

		q.get(s)[a] = q.get(s)[a] + alpha * (last_reward + gamma*q.get(s_prime)[a_prime] - q.get(s)[a]);

	}

	/**
	 * Apprend en fonction de la récompense de la case en cours.
	 * Mémorise la récompense courante pour la prochaine étape.
	 * @param s : état précédent (temps t)
	 * @param a : action précédente (temps t)
	 * @param s_prime : état courant (temps t + 1)
	 * @param reward_prime : récompense de l'état courant (temps t + 1)
	 */
	public void learn(int s, int a, int s_prime, double reward_prime){

		//if (t == 2000000)
		//    epsilon = 0.0;
		//t++;

		// si l'état est inconnu, on initialise les q values
		if (!etatDejaRencontre(s))
			initQValues(s);

		if (!etatDejaRencontre(s_prime))
			initQValues(s_prime);

		if(algo==0)
			Q_LEARNING(s, a, s_prime);
		else
			SARSA(s, a, s_prime);

		last_reward = reward_prime;
	}

	/**
	 * Determine une action a faire grâce au champ de vision.
	 * avec une stratégie E-greedy
	 */
	public int chooseAction(int etat){
		if(algo == 1) return a_prime;
		return e_greedy(q.get(etat));
	}

	/**
	 * Initialises les q-values de l'état renseigné à 0, ne
	 * vérifie pas si les valeurs sont déjà initialisées.
	 * @param s : état à initialiser
	 */
	public void initQValues(int s){
		Double[] q_values = new Double[nombreActions];
		for (int i = 0; i < nombreActions; i++)
			q_values[i] = 0d;
		q.put(s, q_values);
	}

	/**
	 * Indique si l'état a déjà été rencontré par l'IA.
	 * @param s : etat courant
	 * @return etat rencontré vrai, ou etat inconnu faux
	 */
	public boolean etatDejaRencontre(int s){
		return q.containsKey(s);
	}

	/**
	 * Renvoie l'argmax du tableau correspondant aux q-values
	 * en fonction de l'action considérée.
	 * @param q vecteur des q values selon a
	 * @return argmax
	 */
	public int argmax(Double[] q){

        /*
            Pour les premiers choix, il se peut que q ne soit pas définit
            dans ces cas-là on renvoie un hasard.
         */
		if (q == null){
			Random rdmGen = new Random();
			return rdmGen.nextInt(actions.length);
		}

		int aStar = 0;
		for (int a: actions){
			if (q[aStar] < q[a])
				aStar = a;
		}
		return aStar;
	}

	/*
	 *
	 */
	public int e_greedy(Double[] q){
		Random rdmGen = new Random();
		if(rdmGen.nextDouble() < 1-this.epsilon){
			return argmax(q);
		}
		return rdmGen.nextInt(actions.length);
	}
}
