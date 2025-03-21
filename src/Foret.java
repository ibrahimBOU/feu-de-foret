import java.util.*;

// Classe représentant la forêt dans la grille
class Foret {
    private final int h, l;
    private final double p;
    private final Random random;

    private Cellule[][] grille;

    public Foret(int h, int l, double p, List<int[]> feuxInitiaux) {
        this.h = h;
        this.l = l;
        this.p = p;
        this.grille = new Cellule[h][l];
        this.random = new Random();

        // Initialisation de la grille
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < l; j++) {
                grille[i][j] = new Cellule(EtatCellule.ARBRE);
            }
        }

        // Placer les cases initialement en feu
        for (int[] coord : feuxInitiaux) {
            int x = coord[0];
            int y = coord[1];
            if (x >= 0 && x < h && y >= 0 && y < l) {
                grille[x][y].setEtat(EtatCellule.FEU);
            }
        }
    }

    // Methode pour faire évoluer la forêt d'un pas de temps
    public boolean evoluer() {
        Cellule[][] nouvelleGrille = new Cellule[h][l];
        boolean feuPresent = false ;

        // Copier l'état actuel de la grille
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < l; j++) {
                nouvelleGrille[i][j] = new Cellule(grille[i][j].getEtat());
            }
        }

        // Propagation du feu
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < l; j++) {
                // Le feu s'éteint, propagation aux cases adjacentes
                if (grille[i][j].getEtat() == EtatCellule.FEU) {
                    nouvelleGrille[i][j].setEtat(EtatCellule.CENDRES);
                    propagerFeu(nouvelleGrille, i, j);
                }
            }
        }

        // Vérifier s'il reste du feu
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < l; j++) {
                if (nouvelleGrille[i][j].getEtat() == EtatCellule.FEU) {
                    feuPresent = true;
                    break;
                }
            }
            if (feuPresent) break;
        }

        grille = nouvelleGrille;
        return feuPresent;
    }

    // Methode pour propager le feu aux cases
    private void propagerFeu(Cellule[][] nouvelleGrille, int i, int j) {
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};

        for (int k = 0; k < 4; k++) {
            int x = i + dx[k];
            int y = j + dy[k];

            if (x >= 0 && x < h && y >= 0 && y < l && grille[x][y].getEtat() == EtatCellule.ARBRE && random.nextDouble() < p) {
                nouvelleGrille[x][y].setEtat(EtatCellule.FEU);
            }
        }
    }

    // Methode pour afficher la forêt
    public void afficher() {
        for (int i = h - 1; i >= 0; i--) { // Commencer par la dernière ligne (bas)
            for (int j = 0; j < l; j++) {
                switch (grille[i][j].getEtat()) {
                    case ARBRE:
                        System.out.print("🌳 ");
                        break;
                    case FEU:
                        System.out.print("🔥 ");
                        break;
                    case CENDRES:
                        System.out.print("⬛ ");
                        break;
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}