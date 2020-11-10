package core;

import IU.*;

/**
 * Representa a un jugador de la partida, identificado por el nombre, las cartas de la mano y puntos acumulados 
 * Funcionalidad: recibir las 12 cartas, de entre las cartas posibles a colocar selecciona una, consultar/modificar puntos, etc
 */
public class Jugador {
    /**
    * Representa las cartas de cada jugador en cada momento de la partida. 
    * Estructura: se almacenarán en un array estático.
    * Funcionalidad: añadir una carta, quitar una carta, devolver cartas posibles, visualizar, etc 
    */
    
    
    private String nombre;
    private int puntuacion;
    private Mano laMano;

    public Jugador() {
        this.nombre = "";
        this.puntuacion = 0;
        this.laMano = new Mano();
    }
    
    public void vaciarMano(){
        this.laMano = new Mano();
    }
    
    public String getNombre() {
        return nombre;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public Carta getLaMano(int pos) {
        return laMano.miMano[pos];
    }
    
    public int getNumCartas(){
        return laMano.numCartas;
    }
    
    public void eliminar(Carta c){
        laMano.eliminar(c);
    }
    
    public void insertar(Carta c){
        laMano.insertar(c);
    }
    
    public void sumaPuntuacion(int p){
        puntuacion += p;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public static boolean esJugable(Carta c, Mesa laMesa){   //Condiciones de que la siguiente carta sea jugable, en funcion del estado de la mesa.
        
        if(laMesa.getLista(Carta.Palos.OROS.ordinal()).isEmpty()){    //Si no hay oros en la mesa:
            return c.compararCartas(new Carta(Carta.Palos.OROS,5));     //Solo se podra jugar el 5,OROS.
        }else{                  //Si hay oros:
            if(!laMesa.getLista(c.getPalo().ordinal()).isEmpty() && laMesa.getCartaMasBajaEn(c.getPalo().ordinal()).getValor() == (c.getValor())+1){
                return true;    //O bien es 1-valor de la mas baja de dicho palo
            }
            else if(!laMesa.getLista(c.getPalo().ordinal()).isEmpty() && laMesa.getCartaMasAltaEn(c.getPalo().ordinal()).getValor() == (c.getValor())-1){
                return true;    //O bien es 1+valor de la mas alta de dicho palo
            }
            else if (c.getValor() == 5){
                return true;    //O es un 5, que siempre se podrá jugar
            }
            else{
                return false;
            }
        }
    }
    
    @Override
    public String toString() {
        StringBuilder toret = new StringBuilder();
        
        toret.append("Mano de ").append(nombre).append(": \n");
        toret.append(laMano.toString());
        
        return toret.toString();
    }
    
    
    public class Mano {
        
        private Carta[] miMano;
        private int numCartas;

        public Mano() {
            this.numCartas = 0;
            this.miMano = new Carta[Juego.MAXMANO];
        }
        
        public void insertar(Carta c){
            miMano[numCartas] = c;
            numCartas++;
        }
        
        public void eliminar(Carta c){
            boolean b = false;
            int i = 0;
            
            while(!b){
                if (miMano[i].compararCartas(c)) {
                    b = true;
                }
                i++;
            }
            for (int j = i-1; j < numCartas-1; j++) {
                        miMano[j] = miMano[j+1];
            }
            numCartas--;
        }
        
        @Override
        public String toString() {      //Muestra las cartas en horizontal
            StringBuilder toret = new StringBuilder();

            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < numCartas; j++) {   //Cogemos cada fila troceada del toStringGrafico, y vamos sumando una a una cada carta
                    toret.append(miMano[j].toStringGrafico().split("\n")[i]);
                }
                if(i<4) toret.append("\n");         //Ajustar el HUD
            }
            return toret.toString();
        }
    }
}