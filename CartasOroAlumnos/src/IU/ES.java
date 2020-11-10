package IU;

import java.awt.*;
import java.awt.event.KeyEvent;

import java.util.Scanner;

public class ES {
    
    
    /**
     * Lee un num. de teclado
     * @param msg El mensaje a visualizar.
     * @return El num., como entero
     */
    public static int leeNum(String msg){
        boolean repite;
        int toret = 0;
        Scanner teclado = new Scanner( System.in );

        do {
            repite = false;
            System.out.print( msg );

            try {
                toret = Integer.parseInt( teclado.nextLine() );
            } catch (NumberFormatException exc) {
                System.err.println("Formato incorrecto");
                repite = true;
            }
        } while( repite );

        return toret;
    }
    
    public static String leeString(String msg){
        String toret = "";
        Scanner teclado = new Scanner( System.in );

        System.out.print( msg );

        toret = teclado.nextLine();
        
        return toret;
    }

    public static void limpiarpantalla(){  //Funcion para limpiar la consola
        String OS = System.getProperty("os.name").toLowerCase();
        int ctrl = KeyEvent.VK_CONTROL;
        if (OS.contains("mac"))
            ctrl = KeyEvent.VK_META;
        
        try {
            Robot robot = new Robot();
            robot.keyPress(ctrl);
            robot.keyPress(KeyEvent.VK_L);
            robot.keyRelease(ctrl);
            robot.keyRelease(KeyEvent.VK_L);
            Thread.sleep(50);
        }catch(AWTException | InterruptedException e) {   //Excepcion del Thread.Sleep
            System.out.println(e);
        }
    }
}