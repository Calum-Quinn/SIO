package ch.heig.sio.lab2.groupX;

import ch.heig.sio.lab2.display.HeuristicComboItem;
import ch.heig.sio.lab2.display.TspSolverGui;
import ch.heig.sio.lab2.sample.CanonicalTour;
import ch.heig.sio.lab2.sample.RandomTour;
import com.formdev.flatlaf.FlatLightLaf;

public final class Gui {
  public static void main(String[] args) {
    HeuristicComboItem[] heuristics = {
        new HeuristicComboItem("Canonical tour", new CanonicalTour()),
        new HeuristicComboItem("Random tour", new RandomTour()),

        // TODO: Add your heuristics here, to appear in the drop-down
    };

    // May not work on all platforms, comment out if necessary
    System.setProperty("sun.java2d.opengl", "true");

    FlatLightLaf.setup();
    new TspSolverGui(1400, 800, "TSP solver", heuristics);
  }
}
