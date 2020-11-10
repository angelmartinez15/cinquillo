package core;
/**
 * Representa una carta de la baraja, formada por un numero(1..12) y un palo(oros, copas, espadas y bastos) 
 */

public class Carta {
    public enum Palos {BASTOS,COPAS,ESPADAS,OROS};

    private final Palos palo;
    private final int valor;

    public Carta(Palos palo, int valor) {
        this.palo = palo;
        this.valor = valor;
    }

    public Palos getPalo() {
        return palo;
    }

    public int getValor() {
        return valor;
    }
    
    public boolean compararCartas(Carta c){     //Comparacion de cartas iguales
        
        return (getPalo().compareTo(c.getPalo())==0) && getValor()==c.getValor();
    }
    
    @Override
    public String toString() {      //valor, PALO
        StringBuilder toret = new StringBuilder();
        
        toret.append(getValor()).append(", ").append(getPalo());
        return toret.toString();
    }
    
    public String toStringGrafico() {       //Diseño grafico de una carta
        StringBuilder toret = new StringBuilder();
        String OS = System.getProperty("os.name").toLowerCase();
        String ancho = "───";
        if (!OS.contains("win"))
            ancho = "─────";
        
        toret.append("┌").append(ancho).append("┐\n│").append(getPalo().toString().charAt(0));
        
        if(getValor()<10)   toret.append("    │\n│  ");
        else toret.append("    │\n│ ");
        
        toret.append(getValor()).append("  │\n│    ").append(getPalo().toString().charAt(0)).append("│\n").append("└").append(ancho).append("┘\n");
        return toret.toString();
    }
}