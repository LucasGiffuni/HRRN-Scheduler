package model;

//Clase modelo de un proceso.
public class Proceso {

    int PID; // FINAL
    String estado;

    long tInitBurst = 0;
    long tBurst = 0; // [0 - 100]

    long tLlegada = 0;

    long tInicio = 0;

    long tRespuesta = 0;

    long tTotalVida = 0;

    long tTermina = 0;

    long tiempoRetraso = 0;

    long tiempoLlegada = 0;
    long responseRatio = 0;

    public Proceso() {
    }

    public Proceso(int pID, String estado) {
        PID = pID;
        this.estado = estado;
        tBurst = (long) (Math.random() * 99 + 1);
        tiempoRetraso = (long) (Math.random() * 40 + 1);
        tInitBurst = tBurst;
    }



    public long getResponseRatio() {
        return responseRatio;
    }

    public void setResponseRatio(long responseRatio) {
        this.responseRatio = responseRatio;
    }

    public int getPID() {
        return PID;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }



    public long gettInitBurst() {
        return tInitBurst;
    }

    public void settInitBurst(long tInitBurst) {
        this.tInitBurst = tInitBurst;
    }

    public long gettBurst() {
        return tBurst;
    }

    public void settBurst(long tBurst) {
        this.tBurst = tBurst;
    }

    

    public long getTiempoRetraso() {
        return tiempoRetraso;
    }

    public void setTiempoRetraso(long tiempoRetraso) {
        this.tiempoRetraso = tiempoRetraso;
    }

    public long getTiempoLlegada() {
        return tiempoLlegada;
    }

    public void setTiempoLlegada(long tiempoLlegada) {
        this.tiempoLlegada = tiempoLlegada;
    }

    @Override
    public String toString() {
        return "Proceso [PID=" + PID + ", estado=" + estado + ", responseRatio=" + responseRatio + ", tBurst=" + tBurst
                + ", tInicio=" + tInicio + ", tInitBurst=" + tInitBurst + ", tLlegada=" + tLlegada + ", tRespuesta="
                + tRespuesta + ", tTermina=" + tTermina + ", tTotalVida=" + tTotalVida + ", tiempoLlegada="
                + tiempoLlegada + ", tiempoRetraso=" + tiempoRetraso + "]";
    }

    // Método para bloquear y desbloquear estados.
    public void BloquearDesbloquear() {
        if (this.estado.equals("BLOQUEADO")) {
            this.setEstado("LISTO");
        } else {
            this.setEstado("BLOQUEADO");
        }
    }

    public synchronized void ejecutando(long tiempoActual) {

        
        if (!(getEstado().equals("FINALIZADO"))) {
            
            System.out.println("ESTADO PROCESO: " + getEstado());

            if (tiempoActual == tLlegada) {
                setEstado("LISTO");
                System.out.println("ESTADO PROCESO: " + getEstado());

            }

            if (tBurst == tInitBurst) {
                setEstado("EJECUTADO");
                System.out.println("ESTADO PROCESO: " + getEstado());

                tInicio = tiempoActual;
                tRespuesta = tInicio - tLlegada;
                System.out.println("Tiempo Inicio: " + tInicio);
                System.out.println("Tiempo Respuesta: " + tRespuesta);

        
            }

            if (getEstado().equals("BLOQUEADO") == false) {
                tBurst--;
                System.out.println("Tiempo burst: " + tBurst);

            }

            tTotalVida++;
            System.out.println("Tiempo vida: " + tTotalVida);

            if (tBurst == 0) {
                setEstado("FINALIZADO");
                System.out.println("ESTADO PROCESO: " + getEstado());

                tTermina = tiempoActual;
                System.out.println("Tiempo finalizado: " + tTermina);

            }
        }

    }
}
