package juego;

import multimedia.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.util.Scanner;

public class Mapa implements Dibujable {
    private static Image IMAGEN_MUROS;
    private static Image imagenPuntos;
    private static final Color COLOR_FONDO = Color.BLACK;

    private static final String RUTA_MAPAS = "src/mapas/mapa";

    private final char[][] mapa = new char[20][20];

    private Lienzo lienzo;

    public Mapa(Lienzo lienzo, int nivel) {
        this.lienzo = lienzo;

        try {
            cargarMapa(nivel);
        } catch (Exception e){
            e.printStackTrace();
        }

        cargarImagenes();
        generarPuntos();
    }

    public void setLienzo(Lienzo lienzo) {

    }

    public int getAncho() {
        return mapa[1].length;
    }

    public int getAlto() {
        return mapa.length;
    }

    private char getContenido(int x, int y) {
        return mapa[y][x];
    }

    private char getContenido(Posicion posicion) {
        return mapa[posicion.getY()][posicion.getX()];
    }

    private void setContenido(int x, int y, char c) {
        mapa[y][x] = c;
    }

    private void setContenido(Posicion posicion, char c) {
        mapa[posicion.getY()][posicion.getX()] = c;
    }

    private void cargarMapa(int nivel) throws FileNotFoundException {
        String rutaCompleta = RUTA_MAPAS + nivel + ".txt";
        Scanner entrada = new Scanner(new BufferedReader(new FileReader(rutaCompleta)));
        for (int y = 0; y < 20; y++) {
            String[] linea = entrada.nextLine().split(",");
            for (int x = 0; x < 20; x++) {
                char caracter = linea[x].charAt(0);
                mapa[y][x] = caracter;
            }
        }
    }

    private static void cargarImagenes() {
        try {
            IMAGEN_MUROS = ImageIO.read(new File("src/assets/Muro32.png"));
            imagenPuntos = ImageIO.read(new File("src/assets/Moneda32.png"));
        } catch (IOException e){
            throw new RuntimeException("No se puede cargar la imagen: " + e);
        }
    }

    public boolean esTransitable(Posicion posicion) {
        int x = posicion.getX();
        int y = posicion.getY();

        return x >= 0 && x < mapa.length && y >= 0 && y < mapa[0].length && getContenido(x, y) != '#';
    }

    // TODO Esto del destino está hecho raro... Lo suyo sería que el actor se moviera hacia donde sea, o que, si no se puede, se lanzase una excepción y gestionarla. Pero esto de estar usando Posiciones de usar y tirar no mola.
    public Posicion calcularDestino(Posicion actual, Direccion dir) {
        Posicion nueva = actual.desplazar(dir);
        if (esTransitable(nueva)) return nueva;
        return actual;
    }

    public void generarPuntos() {
        for (int x = 0; x < mapa.length; x++) {
            for (int y = 0; y < mapa[0].length; y++) {
                if (getContenido(x, y) == ' ') setContenido(x, y, '·');
            }
        }
    }

    public boolean nivelSuperado() {
        for (int x = 0; x < mapa.length; x++) {
            for (int y = 0; y < mapa[0].length; y++) {
                if (getContenido(x, y) == '·') return false;
            }
        }
        return true;
    }

    public boolean hayPunto(Posicion posicion) {
        return getContenido(posicion) == '·';
    }

    public void retirarPunto(Posicion posicion) {
        setContenido(posicion, ' ');
    }

    public void dibujar() {
        for (int x = 0; x < getAncho(); x++) {
            for (int y = 0; y < getAlto(); y++) {
                lienzo.marcarPixel(x, y, COLOR_FONDO);

                if (getContenido(x, y) == '#') lienzo.dibujarImagen(x, y, IMAGEN_MUROS);
                else if (getContenido(x, y) == '·') lienzo.dibujarImagen(x, y, imagenPuntos);
            }
        }
    }
}