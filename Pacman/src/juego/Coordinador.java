package juego;

import multimedia.*;

import java.awt.*;
import java.util.ArrayList;

/**
 * Encargado de coordinar el juego.
 * Incluye:
 * Teclado   ->       Recibe los inputs para moverse
 * Lienzo    ->       Objeto en el que se dibujan los graficos.
 * Mapa      ->       Guarda lo relacionado con el mapa/nivel actual
 * Pacman    ->       Objeto del jugador
 * Estado    ->       Estado actual del juego
 * Fantasmas ->       Objetos de los enemigos
 */
public class Coordinador implements Dibujable {
    private Lienzo lienzo;
    private Teclado teclado;
    private Mapa mapa;

    private Pacman pacman;
    private ArrayList<Fantasma> fantasmas = new ArrayList<>();
    private EstadoJuego estado;

    public Coordinador(Lienzo lienzo, Teclado teclado) {
        this.lienzo = lienzo;
        this.teclado = teclado;
        estado = new EstadoJuego(lienzo);
        mapa = new Mapa(lienzo, estado.getNivel());

        situarActores();
    }

    public void setLienzo(Lienzo lienzo) {
        this.lienzo = lienzo;
    }

    /**
     * Se encarga de colocar tanto pacman como los fantasmas en su posicion de inicio
     */
    private void situarActores() {
        pacman = new Pacman(this, lienzo, teclado, mapa, estado);

        for (int i = 0; i < 3; i++) {
            fantasmas.add(new Fantasma(this, lienzo, pacman, mapa));
       }
    }

    /**
     * Mira si la posicion indicada esta libre o no
     * @param posicion Recibe la posicion para comprobar
     * @return Devuelve un true si esta libre, false si no
     */
    private boolean estaLibre(Posicion posicion) {
        if (!mapa.esTransitable(posicion)) return false;

        if (posicion.equals(pacman.getPosicion())) return false;

        for (Fantasma fantasma : fantasmas) {
            if (posicion.equals(fantasma.getPosicion())) return false;
        }

        return true;
    }

    /**
     * Genera una posicion vacia aleatoriamente en el mapa actual
     * @return Devuelve la posicion aleatoria
     */
    public Posicion obtenerPosicionVaciaAleatoria() {
        Posicion posicion;

        do {
            posicion = new Posicion(mapa);
        } while (!estaLibre(posicion));

        return posicion;
    }

    public void tick() throws PacmanComidoException, SalirDelJuegoException {
        pacman.tick();

        if (mapa.hayPunto(pacman.getPosicion())) {
            estado.incrementarPuntuacion();
            mapa.retirarPunto(pacman.getPosicion());
        }

        for (Fantasma fantasma : fantasmas) {
            fantasma.tick();
        }

        if (mapa.nivelSuperado()) {
            estado.subirNivel();
            mapa = null;
            mapa = new Mapa(lienzo, estado.getNivel());
        }
    }

    public void dibujar() {
        lienzo.limpiar();

        mapa.dibujar();
        pacman.dibujar();

        for (Fantasma fantasma : fantasmas) {
            fantasma.dibujar();
        }

        estado.dibujar();

        lienzo.volcar();
    }
}