package IU;
import static IU.ES.limpiarpantalla;
import core.*;

/**
 * Representa el cinquillo_oro, con sus reglas. 
 * Se recomienda una implementación modular.
 */

public class Juego {
    public static final int NUMJUGADORES = 4;
    public static final int PUNTOSPARTIDA = 4;
    public static final int MAXMANO = Baraja.MAXCARTAS/Juego.NUMJUGADORES;
    private static int puntosOros = 0;
    
    private static Jugador[] jugadores ;
    private static Baraja laBaraja;
    private static Mesa laMesa;
    
    /**
    * Se crean los jugadores y permite jugar o terminar el juego.
    * Es posible jugar varias partidas.
    */ 
   
    public Juego(){
        
    }

    public static void inicioJuego(){   //Ejecuta el Cinquillo
        primerAcceso();
        jugadores = new Jugador[NUMJUGADORES];
        leerJugadores();
        easterEgg();
        do{
            resetPartida();
            cargando();
            laBaraja.Barajar();
            repartir();
            //primero();    Ordena los jug para que el primero que empieza sea el del 5,OROS
            do{
                turno();
            }while(!manoVacia());
            ganadorPartida();
            gameOver();
        }while(seguir());
        
        creditos();
    }
    
    private static void  resetPartida(){     //Se encarga de devolver los objetos a un estado inicial vacio
        puntosOros += 2;
        laBaraja = new Baraja();
        laMesa = new Mesa();
        for (int i = 0; i < NUMJUGADORES; i++) {
            getJugadores(i).vaciarMano();
        }
    }
    
    private static boolean seguir(){     //Comprobacion para jugar otra partida o no.
        char c;
        
        do{
            c = (ES.leeString("Desean jugar de nuevo (s/n)?").toLowerCase()+" ").charAt(0);     // el +" " evita el error al dar Enter sin escribir nada
        }while(c != 's' && c != 'n');
        limpiarpantalla();
        
        return c == 's';
    }
    
    private static void turno(){    
        boolean pasar;
        boolean b = true;
        int i = 0; 
        
        while (!manoVacia()) {  //Por cada jugador y mientras nadie se quede sin cartas.

            pasar = true;
            System.out.println(laMesa);
            System.out.println(getJugadores(i).toString());
            for (int j = 0; j < getJugadores(i).getNumCartas(); j++) {     //Numeramos las cartas a seleccionar.
                String OS = System.getProperty("os.name").toLowerCase();
                    String espacio = "";
                    if (OS.contains("win"))
                        espacio = " ";
                if( Jugador.esJugable(getJugadores(i).getLaMano(j),laMesa)){                               //Jugables en verde
                    if (j<=3)   System.out.print("   "+espacio+("\033[32m"+(j+1))+"   ");
                    else if (j>3 && j<8)   System.out.print("   "+espacio+("\033[32m"+(j+1))+"   "+espacio);
                    else    System.out.print("   "+("\033[32m"+(j+1))+"   ");
                    pasar = false;            //Si hay jugables, será obligado a jugar
                }else{                                                                                  //No jugables en rojo
                    if (j<=3)   System.out.print("   "+espacio+("\033[31m"+(j+1))+"   ");
                    else if (j>3 && j<8)   System.out.print("   "+espacio+("\033[31m"+(j+1))+"   "+espacio);
                    else    System.out.print("   "+("\033[31m"+(j+1))+"   ");
                }
                System.out.print("\033[30m");    //Reset de los colores a negro.
            }
            if (!pasar) {                       //Si el jugador puede jugar alguna carta
                int selec = 0;
                    do{                         //Comprobaciones hasta seleccionar una carta correcta y jugable
                        try{
                                selec = (ES.leeNum("\nSeleccion: ")-1);
                                if( selec <0 || selec >getJugadores(i).getNumCartas()-1 ){
                                    b=true;
                                    throw new Exception( "SELECCION INCORRECTA" );
                                }
                                
                                if (!Jugador.esJugable(getJugadores(i).getLaMano(selec),laMesa)) {
                                    b=true;
                                    throw new Exception ("LA CARTA NO ES JUGABLE!!");
                                }
                            b=false;
                        }catch(Exception e){
                            System.err.println(e.getMessage());
                        }
                    }while(b);
                    if (getJugadores(i).getLaMano(selec).compararCartas(new Carta(Carta.Palos.OROS,1))) {
                        getJugadores(i).sumaPuntuacion(puntosOros);    //Si jugamos el 1,OROS y en la mesa hay del 2-12
                        puntosOros = 0;                             //Sumamos puntosOros al jugador y reseteamos la variable.
                    }
                    laMesa.insertar(getJugadores(i).getLaMano(selec));                       //Insertamos en mesa
                    getJugadores(i).eliminar(getJugadores(i).getLaMano(selec));     //Borramos de la mano
            }else{      //Caso en el que es obligatorio pasar,
                System.out.print("\nNo hay cartas que puedas jugar. Pasas.");
                try {
                    Thread.sleep(3000);     //Dejamos ver el mensaje al jugador
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
            }
            limpiarpantalla();   //Limpieza del HUD

            i++;
            if(i>=NUMJUGADORES) i=0;    //Si llegamos al ultimo jugador, reseteamos a 0
        }
    }
    
    private static void ganadorPartida(){    //Al jugador sin cartas en la mano, le sumamos PUNTOSPARTIDA
        int i = 0;
        while(i<NUMJUGADORES){
            if(getJugadores(i).getNumCartas() == 0){
                getJugadores(i).sumaPuntuacion(PUNTOSPARTIDA);
            }
            i++;
        }
        
    }
    
    private static boolean manoVacia(){  //Recorremos todos los jugadores hasta encontrar una mano vacia.
        int i = 0;
        while(i < NUMJUGADORES){
            if(getJugadores(i).getNumCartas() == 0){
                return true;    //Si sucede, true
            }
            i++;
        }
        return false;           //Si no, false
    }
    
    private static void leerJugadores (){    //Pregunta por los nombres de los jugadores
        String nombre;
        
        for (int i = 0; i < NUMJUGADORES; i++) {
            do{
                nombre = ES.leeString("Introduce el nombre del jugador "+(i+1)+" : ");
                jugadores[i] = new Jugador();
            }while("".equals(nombre.trim()));
            getJugadores(i).setNombre(nombre);
        }
        limpiarpantalla();   //Limpieza del HUD

    }
       
    private static void repartir(){      //Dada una baraja, repartimos a partes iguales las cartas a unos jugadores.
        for (int i = 0; i < NUMJUGADORES ; i++) {
            for (int j = i*MAXMANO; j < (i+1)*MAXMANO; j++) {
                getJugadores(i).insertar(laBaraja.getMiBaraja(j));
            }
        }
    }
    
//    private static void primero(){   //Detectamos quien tiene el 5,OROS y ordenamos los jugadores
//        int j = 0;                  //Siendo este el primero, seguido de los jugadores a su derecha
//        int c = 0;
//        Carta oro = new Carta (Carta.Palos.OROS,5);
//        
//        while( oro.compararCartas(getJugadores(j).getLaMano(c))==false ){
//            if (c==MAXMANO-1) {
//               j++;
//               c=0;
//            }else   c++;
//        }
//        if (j!=0) {             //Si el jugador que tiene el 5,OROS es el primero, no hace falta reordenar nada.
//            int aux = j;
//            int x = 0;
//            Jugador[] torw = new Jugador[NUMJUGADORES];
//            
//            do {
//                torw[x] = getJugadores(j);
//                j++;
//                x++;
//                if (j>NUMJUGADORES-1) {
//                    j=0;
//                }
//            } while (j!= aux);
//            jugadores = torw;
//        }        
//    }
//    
    
    private static void gameOver(){  //Pantalla de finalizacion del juego + Marcadores ordenados por puntuacion
        limpiarpantalla(); //Limpieza HUD
        System.out.print("\033[35m┌─────────────┐\n"+"\033[35m| ");
        System.out.print("\033[33m   JUEGO FINALIZADO   ");
        System.out.println("\033[35m|\n"+"\033[35m└─────────────┘");
        
        System.out.println("\033[36mMARCADOR:\033[30m");
        ordenarPorPuntuacion();
        
        System.out.println("\t\033[32m"+getJugadores(0).getNombre()+": "+getJugadores(0).getPuntuacion()+" puntos"+"\033[0m");    //para visualizar en verde al que mas puntos tenga 
        for (int i = 1; i < NUMJUGADORES; i++) {
                System.out.println("\t"+getJugadores(i).getNombre()+": "+getJugadores(i).getPuntuacion()+" puntos");
        }
        System.out.println("\n");
    }
    
    private static void primerAcceso(){  //Mensaje de bienvenida
        System.out.println("\033[35m    ┌───┘\\ "+"\033[36m| |"+" \033[33mCINQUILLO "+"\033[36mo o"+" \033[35m/└───┐");
        System.out.println("\033[35m    └───┐/ "+"\033[36mo o"+"   \033[33mPRIME   "+"\033[36m| |"+" \033[35m\\┌───┘");
        System.out.println("\033[30m");
    }
    
    private static void cargando(){      //Animación de carga antes de las partidas.
            System.out.print("Cargando partida");   
            for (int j = 0; j < 5; j++) {
                System.out.print(".");      //Añadimos un . por cada 400 milesimas
                try {
                   Thread.sleep(400);
                } catch (InterruptedException e) {      //Excepción del Thread.Sleep
                   System.out.println(e);
                }
            }
        limpiarpantalla();
    } 
    
    
    private static void ordenarPorPuntuacion(){
        Jugador[] aux = new Jugador[NUMJUGADORES+1];
        aux[NUMJUGADORES] = new Jugador();
        System.arraycopy(jugadores, 0, aux, 0, NUMJUGADORES);   //(origen,inicioOrigen,destino,inicioDestino,tamaño_a_copiar)
        
        for (int i = 0; i < NUMJUGADORES; i++) {
            for (int j = i+1; j < NUMJUGADORES+1; j++) {
                if (aux[i].getPuntuacion() < aux[j].getPuntuacion()){
                    jugadores[0] = aux[i];
                    aux[i] = aux[j];
                    aux[j]=jugadores[0];
                }
            }
        }
        System.arraycopy(aux, 0, jugadores, 0, NUMJUGADORES);
    }
    
    private static void creditos(){      //Mesaje de créditos a los creadores
        StringBuilder toret = new StringBuilder();
        
        toret.append("\t\033[35mJUEGO REALIZADO POR:\n").
            append("Pablo González González\n").
            append("Ángel Martínez Villar\n").
            append("Héctor Martínez López\n").
            append("Eva Nogueiras Ferreiro\n");
        toret.append("\n\t\033[35mGracias por jugar :)");
        
        System.out.println(toret.toString());
    }
    
    private static void easterEgg(){
        if (EasterEgg.comprobacion(jugadores)) EasterEgg.activacion();
    }

    public static Jugador getJugadores(int pos) {
        return jugadores[pos];
    }   
}