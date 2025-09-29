package juego;

public class NivelSuperado extends RuntimeException {
    public NivelSuperado(String message) {
        super(message);
    }
}
