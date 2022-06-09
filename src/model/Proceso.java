package model;

//Clase modelo de un proceso.
public class Proceso {

    int PID;
    String estado;

    long tInitBurst = 0;
    long tBurst = 0;
    long tBloqueo = 0;
    long tBloqueoActual = 0;

    long tLlegada = 0;
    long tInicio = 0;

    long tRespuesta = 0;
    long tTotalVida = 0;
    long tTermina = 0;
    long tiempoRetraso = 0;
    long tiempoLlegada = 0;
    long responseRatio = 0;


    private boolean boolBloqueado = false;
    public Proceso() {
    }

    public Proceso(int pID, String estado, String maxBurst, String maxRetraso, String maxBloqueo) {
        PID = pID;
        this.estado = estado;
  
        tBurst = (long) (Math.random() * Integer.parseInt(maxBurst) + 1);
        
        tiempoRetraso = (long) (Math.random() * Integer.parseInt(maxRetraso) /2  + 1);
        
        tBloqueo = (long) (Math.random() * tBurst /2  + 1);
        tBloqueoActual = tBloqueo; // Asignamos el tiempo random de bloqueo al tiempo de bloqueo actual
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



    public synchronized void ejecutando(long tiempoActual) {

        if (!(getEstado().equals("FINALIZADO")) && !(getEstado().equals("BLOQUEADO")) && !(getEstado().equals("PREPARADO"))) {
            System.out.println("ESTADO PROCESO: " + getEstado());
            tTotalVida++;

            System.out.println("");
            System.out.println("");
            System.out.println("##################################");
            System.out.println("");
            System.out.println("TIEMPO BLOQUEO: " + tBloqueo);
            System.out.println("TIEMPO BLOQUEO ACTUAL: " + tBloqueoActual);
            System.out.println("TIEMPO DE VIDA: " + tTotalVida);
            System.out.println("");

            System.out.println("ESTADO: " + estado);
            System.out.println("");
            System.out.println("##################################");
            System.out.println("");
            System.out.println("");

            // Logica para bloquear
            if ((tTotalVida % tBloqueoActual == 0 )) {
                System.out.println("BLOQUEANDO PROCESO POR E/S");
                
                setEstado("BLOQUEADO");
               

                boolBloqueado = true;
            }

            // Se setea a listo el proceso
            if (tiempoActual == tLlegada) {
                System.out.println("Proceso " + getPID() + " listo para ejecutar");
                setEstado("LISTO");
                System.out.println("ESTADO PROCESO: " + getEstado());
            }

            if (tBurst == tInitBurst) {
                System.out.println("Proceso " + getPID() + " ejecutando");

                setEstado("EJECUTADO");
                System.out.println("ESTADO PROCESO: " + getEstado());

                tInicio = tiempoActual;
                tRespuesta = tInicio - tLlegada;
                System.out.println("Tiempo Inicio: " + tInicio);
                System.out.println("Tiempo Respuesta: " + tRespuesta);

            }

            if ((getEstado().equals("LISTOBLOQ"))||(getEstado().equals("EJECUTADO"))) {
                tBurst--;
                System.out.println("Tiempo burst: " + tBurst);

            }
            if((getEstado().equals("LISTOBLOQ"))){
                setEstado("EJECUTADO");

            }

            if (tBurst == 0) {
                setEstado("FINALIZADO");
                System.out.println("ESTADO PROCESO: " + getEstado());

                tTermina = tiempoActual;
                System.out.println("Tiempo finalizado: " + tTermina);

            }
            System.out.println("Tiempo vida: " + tTotalVida);

        } else if (getEstado().equals("BLOQUEADO")) {

            if (tBloqueoActual != 0) {
                tBloqueoActual--;
                System.out.println("Tiempo actual bloqueo: " + tBloqueoActual);

            } else {
                setEstado("PREPARADO");
                tBloqueoActual = tBloqueo;
            }

            tTotalVida++;
        }

    }
    

 
}
