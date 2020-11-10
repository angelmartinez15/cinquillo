package core;
import java.util.*;

/**
* Representa la mesa de juego, donde los jugadores colocan las cartas en cada turno.
* Estructura: Se utilizará un array estático de dobles colas (Deque), una para cada palo
* Funcionalidad: insertar la carta en su lugar correcto automáticamente, visualizar, etc
*/

public class Mesa {
    
    Deque<Carta>[] lista;
    
    public Mesa() {
        this.lista = new ArrayDeque[Carta.Palos.values().length];
        for (int i = 0; i < lista.length; i++) {
            lista[i] = new ArrayDeque<>();
        }
    }

    public Deque<Carta> getLista(int pos) {
        return lista[pos];
    }
    
    public Carta getCartaMasBajaEn(int pos){
        return this.lista[pos].getFirst();
    }
    
    public Carta getCartaMasAltaEn(int pos){
        return this.lista[pos].getLast();
    }
    
    public void insertar(Carta c){      //Metodo que inserta de forma ordenada las cartas en un deque
        if (c.getValor() > 5){                      //Si el valor de la carta es mayor que cinco, la introducimos a la derecha.
            lista[c.getPalo().ordinal()].addLast(c);
        }
        else{                                       //Si es menor, por la izquierda
            lista[c.getPalo().ordinal()].addFirst(c);
        }
    }

    @Override
    public String toString() {          //Muestra las cartas en horizontal
        StringBuilder toret = new StringBuilder();
        
        toret.append("Mesa:\n");
        for (Carta.Palos p: Carta.Palos.values()) {
            for (int i = 0; i < 5; i++){  //Cogemos cada fila troceada del toStringGrafico, y vamos sumando una a una cada carta
                for (Carta laCarta : lista[p.ordinal()]) {
                    toret.append(laCarta.toStringGrafico().split("\n")[i]);
                }
            toret.append("\n");                                                                     
            }
        }
        return toret.toString();
    }
    
}