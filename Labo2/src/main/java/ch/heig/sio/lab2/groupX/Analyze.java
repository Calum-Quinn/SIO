package ch.heig.sio.lab2.groupX;

import ch.heig.sio.lab2.sample.RandomTour;
import ch.heig.sio.lab2.tsp.TspConstructiveHeuristic;
import ch.heig.sio.lab2.tsp.TspData;
import ch.heig.sio.lab2.tsp.TspTour;

public final class Analyze {

  public static void main(String[] args) {
    // TODO
    //  - Renommer le package ;
    //  - Implémenter les différentes heuristiques en écrivant le code dans ce package, et uniquement celui-ci
    //    (sous-packages et packages de tests ok) ;
    //  - Factoriser le code commun entre les différentes heuristiques ;
    //  - Produire une documentation soignée comprenant :
    //    - la javadoc, avec auteurs et description des implémentations ;
    //    - des commentaires sur les différentes parties de vos algorithmes.

    // Longueurs optimales :
    // pcb442  : 50778
    // att532  : 86729
    // u574    : 36923
    // pcb1173 : 56892
    // nrw1379 : 56638
    // u1817   : 57201

    // E X A M P L E

    String[] instances = {"att532.dat", "pcb442.dat"};

    int startCity = 0;

    for (String instance : instances) {

      try {

        // Exemple de lecture d'un jeu de données :
        TspData data = TspData.fromFile("data/" + instance);

        // Exemple d'appel d'un algothithme
        TspConstructiveHeuristic algorithm = new RandomTour();
        TspTour solution = algorithm.computeTour(data, startCity);

        System.out.println("Problem: " + instance + "  Start city:" + startCity);
        System.out.println(solution);
        System.out.println();
      } catch (java.io.FileNotFoundException e) {
        e.printStackTrace();
      }

    }

    // E N D   O F   E X A M P L E

  }
}
