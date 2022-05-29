package model;

import java.util.ArrayList;
import java.util.List;

public class Planificador {

    List<Proceso> colaProcesosCreados;
    List<Proceso> colaProcesosListos;
    List<Proceso> colaProcesosBloqueados;
    Proceso procesoEnEjecucion;

    // Constructor vacío para inicializar variables
    public Planificador() {
        this.colaProcesosBloqueados = new ArrayList<Proceso>();
        this.colaProcesosListos = new ArrayList<Proceso>();
        this.colaProcesosBloqueados = new ArrayList<Proceso>();
    }

    public void agregarProcesoaCreados(Proceso proceso) {
        this.colaProcesosCreados.add(proceso);
    }

    public void agregarProcesoABloqueados(Proceso proceso) {
        this.colaProcesosBloqueados.add(proceso);
    }

    public void agregarProcesoAListo(Proceso proceso) {
        this.colaProcesosListos.add(proceso);
    }

    public void asignarPocesoACPU(Proceso proceso) {

        this.procesoEnEjecucion = proceso;
        // WIP
    }

}
