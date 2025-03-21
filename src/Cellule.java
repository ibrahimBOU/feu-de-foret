// Classe représentant une cellule de la grille
class Cellule {
    private EtatCellule etat;

    public Cellule(EtatCellule etat) {
        this.etat = etat;
    }

    public EtatCellule getEtat() {
        return etat;
    }

    public void setEtat(EtatCellule etat) {
        this.etat = etat;
    }
}
