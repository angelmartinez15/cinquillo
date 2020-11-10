package core;

import java.util.*;
/**
 * Representa la baraja del juego, con 48 cartas (12 de cada palo), desordenadas. 
 * Estructura: Las cartas se guardarán en un array estático.
 * Funcionalidad: Crear la baraja, barajar, quitar una carta, etc
 */

public class Baraja {
	public static final int MAXCARTAS = 48;
        
        private Carta[] miBaraja;

    public Baraja() {       //INICIALMENTE ORDENADA
        miBaraja = new Carta [MAXCARTAS];
        for (Carta.Palos p: Carta.Palos.values()) {             //Para cada palo
            for (int i = 0; i < 12; i++) {                      //Para cada valor
                miBaraja[12*p.ordinal()+i] = new Carta(p,i+1);  //p.ordinal() -> posicion en el array
            }
        }
    }

    public Carta getMiBaraja(int pos) {
        return miBaraja[pos];
    }
    
    public void Barajar(){
        List<Carta> lista = new ArrayList();
        for (int i = 0; i < MAXCARTAS; i++) {           //Pasamos de (Array de Cartas) a (Lista de Objetos)
            lista.add(miBaraja[i]);
        }
        Collections.shuffle(lista);                     //Metodo intercambiar aleatoriamente objetos de una lista.
        for (int i = 0; i < MAXCARTAS; i++) {
            miBaraja[i] = lista.get(i);         //sobreescribimos nuestra baraja.
        }
    }
    public void Barajar2(){
        List<Carta> lista = new ArrayList();
        List<Carta> aux = new ArrayList();     
        
        for (int i = 0; i < MAXCARTAS; i++) {
            lista.add(miBaraja[i]);
        }
        
        Random rnd = new Random(System.currentTimeMillis());
        while(!lista.isEmpty()){
            int num = rnd.nextInt(lista.size());
            aux.add(lista.get(num));
            lista.remove(lista.get(num));
        }
        
        for (int i = 0; i < MAXCARTAS; i++) {
            miBaraja[i] = aux.get(i);
        }
    }

    @Override
    public String toString() {      //Muestra las cartas en forma valor, PALO
        StringBuilder toret = new StringBuilder();
        
        for (int i = 0; i < MAXCARTAS; i++) {
            toret.append(miBaraja[i].toString()).append("\n");
        }
        return toret.toString();
    }
    
    public String toStringGrafico() {   //Muestra la cartas graficamente en vertical
        StringBuilder toret = new StringBuilder();
        
        for (int i = 0; i < MAXCARTAS; i++) {
            toret.append(miBaraja[i].toStringGrafico());
        }
        return toret.toString();
    }
    
}

