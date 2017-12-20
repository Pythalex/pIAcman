package rl;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Random;


public class Qlearn {

    public double epsilon = 0.1; // parametre epsilon pour \epsilon-greedy
    public double alpha = 0.2; // taux d'apprentissage
    public double gamma = 0.9; // parametre gamma des eq. de Bellman/

    // tableau des actions possibles
    public int actions[];
    public final int nombreActions = 8;
    public HashMap<Integer, Double[]> q; // s -> a -> q

    public Pacman pacman;

    // Algorithme à utiliser
    private final int algo = 0; // 0 = qLearning ; 1 = SARSA

    // Constructeurs
    public Qlearn(int[] actions, Pacman pacman) {
        this.actions = actions;
        q = new HashMap<>();
        this.pacman = pacman;
    }

    public Qlearn(int[] actions, double epsilon, double alpha, double gamma, Pacman pacman) {
        this.actions = actions;
        this.epsilon = epsilon;
        this.alpha = alpha;
        this.gamma = gamma;
        q = new HashMap<>();
        this.pacman = pacman;
    }

    public void SARSA(int id_state, double reward){

    }

    public void Q_LEARNING(int id_state, double reward){

        if (!q.containsKey(id_state)){
            Double[] tmp = new Double[nombreActions];
            for (int i = 0; i < nombreActions; i++)
                tmp[i] = 0d;
            q.put(id_state, tmp);
        }

        int action = chooseAction(id_state);
        pacman.goInDirection(action);
        int new_state = pacman.calcState();

        if (!q.containsKey(new_state)){
            Double[] tmp = new Double[nombreActions];
            for (int i = 0; i < nombreActions; i++)
                tmp[i] = 0d;
            q.put(new_state, tmp);
        }

        int new_action = argmax(q.get(new_state));

        q.get(id_state)[action] = q.get(id_state)[action] + alpha
                * (reward + gamma*q.get(new_state)[new_action]
                - q.get(id_state)[action]);
    }

    /**
     * Apprend en fonction de la récompense de la case en cours.
     */
    public void play(int id_state, double reward){
        if (algo == 0){ // QLearning
            Q_LEARNING(id_state, reward);
        } else {
            SARSA(id_state, reward);
        }
    }

    /**
     * Determine une action a faire grâce au champ de vision.
     * avec une stratégie E-greedy
     */
    public int chooseAction(int etat){
        Random rdmGen = new Random();
        double rdm = rdmGen.nextDouble();
        if (rdm < epsilon){
            return argmax(q.get(etat));
        } else {
            // on renvoie un random
            return rdmGen.nextInt(actions.length);
        }
    }

    public int argmax(Double[] q){
        int aStar = 0;
        for (int a: actions){
            if (q[aStar] < q[a])
                aStar = a;
        }
        return aStar;
    }
}
