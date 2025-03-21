import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

// Classe pour lancer la simulation
public class SimulationFeuForet {
    public static void main(String[] args) {
        // Charger les paramètres depuis le fichier de configuration
        File fichierConfig = new File("src/config.txt");
        int h = 0, l = 0;
        double p = 0;
        List<int[]> feuxInitiaux = new ArrayList<>();

        try (Scanner scanner = new Scanner(fichierConfig)) {
            while (scanner.hasNextLine()) {
                String ligne = scanner.nextLine();
                String[] parties = ligne.split("=");
                switch (parties[0]) {
                    case "h":
                        h = Integer.parseInt(parties[1]);
                        break;
                    case "l":
                        l = Integer.parseInt(parties[1]);
                        break;
                    case "p":
                        p = Double.parseDouble(parties[1]);
                        break;
                    case "feu_initiaux":
                        String[] coordonnees = parties[1].split(";");
                        for (String coord : coordonnees) {
                            String[] xy = coord.split(",");
                            int x = Integer.parseInt(xy[0]);
                            int y = Integer.parseInt(xy[1]);
                            feuxInitiaux.add(new int[]{x, y});
                        }
                        break;
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Fichier de configuration non trouvé !");
            return;
        }

        // Créer la forêt
        Foret foret = new Foret(h, l, p, feuxInitiaux);

        // Simulation étape par étape
        int step = 0;
        boolean feuPresent = true;
        while (feuPresent) {
            System.out.println("Step " + step);
            foret.afficher();
            feuPresent = foret.evoluer();
            step++;
        }

        System.out.println("Simulation terminée. Plus de feu dans la foret");
    }
}