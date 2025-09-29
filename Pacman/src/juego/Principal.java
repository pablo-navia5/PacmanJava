package juego;

import multimedia.*;
import java.awt.*;

public class Principal {
    private static final int MILLIS = 150;

    /**
     * Pausa el programa durante los milisegundos indicados.
     * @param milisegundos Cantidad de milisegundos de espera
     */
    public static void espera(int milisegundos) {
        try {
            Thread.sleep(milisegundos);
        } catch (InterruptedException e) {
        }
    }

    public static void main(String[] args) {
        int anchoVentana = 20;
        int altoVentana = 23;
        int tamPixel = 32;
        Color colorFondo = Color.BLACK;

        VentanaMultimedia ventana = new VentanaMultimedia("PacMan", anchoVentana, altoVentana, tamPixel, colorFondo);
        Coordinador coordinador = new Coordinador(ventana, ventana.getTeclado());

        try {
            while (true) {
                coordinador.dibujar();
                ventana.getTeclado().tick();

                coordinador.tick();

                espera(MILLIS);
            }
        } catch (PacmanComidoException e) {
            System.out.println("¡Game Over! Te han comido.");
        } catch (SalirDelJuegoException e) {
            System.out.println("Has elegido salir del juego.");
        } finally {
            coordinador.dibujar();
        }
    }
}