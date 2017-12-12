package fr.univ_lille1.parclille1.model;

/**
 * Cette énumération stocke les valeurs de problème existantes. Cela permet de pouvoir rajouter
 * autant de problème que souhaité sans se soucier de modifier une autre partie dans le code.
 */
public enum ProblemType {

    // Constantes

    TREE_TO_BE_CUT("Arbre à tailler"),
    TREE_TO_BE_FELLED("Arbre à abattre"),
    DETRITUS("Détritus"),
    HEDGE_TO_BE_FELLED("Haie à tailler"),
    BAD_GRASS("Mauvaise herbe"),
    OTHER("Autre");

    // Attributs

    private final String text;

    // Constructeurs

    ProblemType(final String text) {
        this.text = text;
    }

    // Méthodes

    /**
     * Surcharge de la méthode toString()
     *
     * @return le texte correspondant au type de l'énumération
     */
    @Override
    public String toString() {
        return text;
    }

}
