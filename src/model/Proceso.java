package model;

//Clase modelo de un proceso.
public class Proceso {

    int PID;
    String estado;

    int tInitBurst = 0;
    int tBurst = 0;
    int tBloqueo = 0;
    int tBloqueoActual = 0;

    int tLlegada = 0;
    int tInicio = 0;

    int tRespuesta = 0;
    int tTotalVida = 0;
    int tTermina = 0;
    int tiempoRetraso = 0;
    int tiempoLlegada = 0;
    int responseRatio = 0;

    String tipo;

    boolean flag;

    public Proceso() {
    }

    public Proceso(int pID, String estado, String maxBurst, String maxRetraso, String maxBloqueo, Boolean BLOQ,
            String STATICBLOQ) {
        int tipoAux;
        PID = pID;
        this.estado = estado;
        this.flag = false;
        tBurst = (int) (Math.random() * Integer.parseInt(maxBurst) + 1);

        tiempoRetraso = (int) (Math.random() * Integer.parseInt(maxRetraso) / 2 + 1);

        if (BLOQ) {
            tBloqueo = Integer.parseInt(STATICBLOQ);
        } else {
            tBloqueo = (int) (Math.random() * tBurst + 1);
        }

        tipoAux = (int) (Math.random() * 3 + 1);

        if(tipoAux == 1){
            this.tipo = "KERNEL";
        }else{
            this.tipo = "USUARIO";
        }

        System.out.println(this.tipo);

        if (tBloqueo == 1) {
            tBloqueo = tBloqueo + 2;
        }
        tBloqueoActual = tBloqueo; // Asignamos el tiempo random de bloqueo al tiempo de bloqueo actual
        tInitBurst = tBurst;
    }

    public int getResponseRatio() {
        return responseRatio;
    }

    public void setResponseRatio(int responseRatio) {
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

    public void settInitBurst(int tInitBurst) {
        this.tInitBurst = tInitBurst;
    }

    public int gettBurst() {
        return tBurst;
    }

    public void settBurst(int tBurst) {
        this.tBurst = tBurst;
    }

    public int getTiempoRetraso() {
        return tiempoRetraso;
    }

    public void setTiempoRetraso(int tiempoRetraso) {
        this.tiempoRetraso = tiempoRetraso;
    }

    public int getTiempoLlegada() {
        return tiempoLlegada;
    }

    public void setTiempoLlegada(int tiempoLlegada) {
        this.tiempoLlegada = tiempoLlegada;
    }

    public int gettTotalVida() {
        return tTotalVida;
    }

    public boolean isFlag() {
        return flag;
    }

    
    

    public String getTipo() {
        return tipo;
    }

    @Override
    public String toString() {
        return "Proceso [PID=" + PID + ", estado=" + estado + ", responseRatio=" + responseRatio + ", tBloqueo="
                + tBloqueo + ", tBurst=" + tBurst + ", tRespuesta=" + tRespuesta + ", tTotalVida=" + tTotalVida
                + ", tiempoLlegada=" + tiempoLlegada + ", tiempoRetraso=" + tiempoRetraso + ", tipo=" + tipo + "]";
    }

    public synchronized void ejecutando(int tiempoActual) {

        if (!(getEstado().equals("FINALIZADO")) && !(getEstado().equals("BLOQUEADO"))
                && !(getEstado().equals("PREPARADO"))) {
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
            if ((tTotalVida % tBloqueoActual == 0) && (tBloqueoActual != 1)) {
                System.out.println("BLOQUEANDO PROCESO POR E/S");

                setEstado("BLOQUEADO");

                System.out.println("PROCESO " + PID + " BLOQUEADO");

            }

            if (getEstado().equals("LISTO")) {
                setEstado("EJECUTANDO");
                flag = true;
            }

            // Se setea a listo el proceso
            if (tiempoActual == tLlegada) {
                System.out.println("Proceso " + getPID() + " listo para ejecutar");
                setEstado("LISTO");
                System.out.println("ESTADO PROCESO: " + getEstado());
            }

            if (tBurst == tInitBurst) {
                System.out.println("Proceso " + getPID() + " ejecutando");

                setEstado("EJECUTANDO");
                System.out.println("ESTADO PROCESO: " + getEstado());

                tInicio = tiempoActual;
                tRespuesta = tInicio - tLlegada;
                System.out.println("Tiempo Inicio: " + tInicio);
                System.out.println("Tiempo Respuesta: " + tRespuesta);

            }

            if ((getEstado().equals("EJECUTANDO"))) {
                flag = false;
                tBurst--;
                System.out.println("Tiempo burst: " + tBurst);

            }
        } else if (getEstado().equals("BLOQUEADO")) {

            if (tBloqueoActual != 0) {
                tBloqueoActual--;
                System.out.println("Tiempo actual bloqueo: " + tBloqueoActual);

            } else {
                setEstado("LISTO");
                tBloqueoActual = tBloqueo;
                System.out.println("PROCESO " + PID + " DESBLOQUEADO");
            }

            tTotalVida++;
        }
        if (tBurst <= 1) {
            setEstado("FINALIZADO");
            System.out.println("ESTADO PROCESO: " + getEstado());

            tTermina = tiempoActual;
            System.out.println("Tiempo finalizado: " + tTermina);

        }

    }

}