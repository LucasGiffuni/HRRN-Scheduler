package model;

//Clase modelo de un proceso.
public class Proceso {

    int PID; // FINAL
    String estado;
    String modo;
    int prioridad;
    int tiempoEnEjecucion;
    int tiempoEYS; // FINAL
    int tiempoEspera;

    long tInitBurst = 0;
    long tBurst = 0; // [0 - 100]

    public Proceso() {
    }

    public Proceso(int pID, String estado, String modo, int prioridad, int tiempoEnEjecucion, int tiempoEYS,
            int tiempoEspera) {
        PID = pID;
        this.estado = estado;
        this.modo = modo;
        this.prioridad = prioridad;

        this.tiempoEnEjecucion = tiempoEnEjecucion;
        this.tiempoEYS = tiempoEYS;
        this.tiempoEspera = tiempoEspera;
        tBurst = (long) (Math.random() * 99 + 1);
        tInitBurst = tBurst;
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

    public String getModo() {
        return modo;
    }

    public void setModo(String modo) {
        this.modo = modo;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    public int getTiempoEnEjecucion() {
        return tiempoEnEjecucion;
    }

    public void setTiempoEnEjecucion(int tiempoEnEjecucion) {
        this.tiempoEnEjecucion = tiempoEnEjecucion;
    }

    public int getTiempoEYS() {
        return tiempoEYS;
    }

    public int getTiempoEspera() {
        return tiempoEspera;
    }

    public void setTiempoEspera(int tiempoEspera) {
        this.tiempoEspera = tiempoEspera;
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

    @Override
    public String toString() {
        return "Proceso [PID=" + PID + ", estado=" + estado + ", modo=" + modo + ", prioridad=" + prioridad
                + ", tBurst=" + tBurst + ", tiempoEYS=" + tiempoEYS + ", tiempoEnEjecucion=" + tiempoEnEjecucion
                + ", tiempoEspera=" + tiempoEspera + "]";
    }

    // Método para bloquear y desbloquear estados.
    public void BloquearDesbloquear() {
        if (this.estado.equals("BLOQUEADO")) {
            this.setEstado("LISTO");
        } else {
            this.setEstado("BLOQUEADO");
        }
    }

}
