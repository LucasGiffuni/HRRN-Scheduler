package model;

public class Process {

    public long PID = 0;
    public long siguientePID = 0;

    public boolean bloqueado;
    public boolean activo;
    public boolean ejecutando;
    public boolean listo;
    public boolean finalizado;

    public long tBloqueadoES;

    long tInicio = 0;

    /**
     * CPU burst Time: Cantidad de tiempo que un proceso utiliza el CPU
     */
    long tBurst = 0; // [0 - 100]

    /**
     * Guarda el estado inicial del burst. Para mostrar un total restante
     */
    long tInitBurst = 0;

    /**
     * Retraso de la llegada. Cuanto tiempo despues de la llegada del proceso
     * anterior llego este proceso? Diferencia horaria en segundos
     */
    long tRetraso = 0; // [0 - 95]

    public Process() {
        siguientePID++;
        PID = siguientePID;
        tBurst = (long) (Math.random() * 99 + 1);
        tInitBurst = tBurst;
        tRetraso = (long) (Math.random() * 40 + 1);
        tBloqueadoES = (long) (Math.random() * 10 + 1);
    }

    public synchronized void ejecutando(long tiempoActual) {

        activo = true;

        if (tBurst == tInitBurst) {
            ejecutando = true;
            tInicio = tiempoActual;
            
            if (tBloqueadoES > 0) {
                bloqueado = true;
            }
        }

        if (bloqueado == false) {
            tBurst--;
        }

        if (tBurst == 0) {
            finalizado = true;
        }
    }

}
