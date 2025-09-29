package juego;

import multimedia.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

public class Pacman extends Actor {
    private EstadoJuego estado;
    private Teclado teclado;
    private Mapa mapa;

    public Pacman(Coordinador coordinador, Lienzo lienzo, Teclado teclado, Mapa mapa, EstadoJuego estado) {
        super(coordinador, lienzo, mapa);
        this.estado = estado;
        this.teclado = teclado;
        this.posicion = new Posicion(7, 8);
        this.mapa = mapa;
        cargarImagen("PacmanDerecha32.png");
    }

    public void tick() throws SalirDelJuegoException {
        try {
            if (teclado.pulsada(KeyEvent.VK_UP) || teclado.pulsada(KeyEvent.VK_W)) {
                cargarImagen("PacmanArriba32.png");
                mover(Direccion.ARR);
            }
            else if (teclado.pulsada(KeyEvent.VK_LEFT) || teclado.pulsada(KeyEvent.VK_A)){
                cargarImagen("PacmanIzq32.png");
                mover(Direccion.IZD);
            }
            else  if (teclado.pulsada(KeyEvent.VK_DOWN) || teclado.pulsada(KeyEvent.VK_S)){
                cargarImagen("PacmanAbajo32.png");
                mover(Direccion.ABA);
            }
            else if (teclado.pulsada(KeyEvent.VK_RIGHT) || teclado.pulsada(KeyEvent.VK_D)){
                cargarImagen("PacmanDerecha32.png");
                mover(Direccion.DCH);
            }
            if (teclado.pulsada(KeyEvent.VK_Q)) throw new SalirDelJuegoException("Saliendo del juego...");
        } catch (MovimientoInvalidoException e) {
            // No hacemos nada. Pierde el turno.
        }
    }
}