package juego;

import multimedia.Dibujable;
import multimedia.Lienzo;

import java.awt.*;

public class EstadoJuego implements Dibujable {
    private int puntuacion;
    private Lienzo lienzo;
    private int nivel = 1;

    public EstadoJuego(Lienzo lienzo) {
        setLienzo(lienzo);
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public int getNivel() {
        return nivel;
    }

    public void incrementarPuntuacion() {
        puntuacion++;
    }

    public void subirNivel() {
        nivel++;
    }

    public void setLienzo(Lienzo lienzo) {
        this.lienzo = lienzo;
    }
    public void dibujar() {
        lienzo.escribirEnMargen(0, 0, "Puntos: " + puntuacion, Color.RED);
    }
}
