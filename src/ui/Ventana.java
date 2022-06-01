package ui;

import java.awt.Dimension;

import java.awt.Color;
import java.awt.Container;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.MouseInputListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;

import java.awt.GridLayout;
import java.awt.event.MouseEvent;

import model.Proceso;

public class Ventana extends JFrame implements Runnable {
    private PanelProceso procesoPanel;
    private JPanel pn = new JPanel();
    private JPanel infoPanel = new JPanel();
    private JPanel cpuInfoPanel = new JPanel();
    private JLabel cpuClockLbl, cntProcesosBloqueados, cntProcesos, cntProcesosListos, procesoEjecutando;

    private int cntListo = 0, cntBloqueado = 0;
    private Proceso procesoEjecutado = null;
    private int CPU_CLOCK = 0;
    boolean verificado = true;

    int tiempoLlegada = 0;

    private int SizeProcesos; // Cantidad de procesos en la lista
    private int SizeProcesosPrev; // Valor anterior de la cantidad, sirve para verificar si se modifico la lista

    private JButton boton1;
    private Thread planificar = new Thread(this);

    private List<Proceso> procesos = new ArrayList<Proceso>();
    List<Proceso> procesosOrdenados = new ArrayList<Proceso>();




    public Ventana() {

        setTitle("Planificador UCU");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        cargarProcesos(); // Cargar procesos al panel de los procesos.
        initUI();
        init();

    }

    private void init() {
        for (Proceso proceso : procesos) {
            procesoPanel = new PanelProceso(proceso);
            pn.add(procesoPanel);

        }
    }

    /*
     * Generamos un thread el cual simulará el pasaje de los ciclos (1 seg = 1 hz).
     * Impementamos la libreria ScheduledExecutorService para manejar precisamente
     * el tiempo.
     * 
     */
    public void run() {
        ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();

        ses.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                CPU_CLOCK++;
               
                cpuClockLbl.setText("Actual Clock: " + CPU_CLOCK + " Hz");
                repaint();

                if (procesoEjecutado != null) {
                    System.out.println("Proceso usando CPU: " + procesoEjecutado.getPID());

                    System.out.println("");
                    System.out.println("");

                    if (!(procesoEjecutado.getEstado().equals("FINALIZADO"))) {
                        procesoEjecutado.ejecutando(CPU_CLOCK);
                    } else {
                        procesos.remove(procesoEjecutado);
                        procesoEjecutado = null;
                        System.out.println("PROCESO FINALIZADO");
                        System.out.println("PREPARANDO SIGUIENTE PROCESO PARA EJECUTAR");
                        System.out.println("");
                        System.out.println("SIGUIENTE PROCESO A EJECUTAR: " + procesos.get(0).getPID());
                        procesoEjecutado = procesos.get(0); //Asignamos el siguiente proceso a ejecutar
                    }
                }

                // Verificacion de los estados de los procesos
                if (verificado || (SizeProcesosPrev != procesos.size();)) {
                    verificado = false;
                    for (Proceso proceso : procesos) {
                        if (proceso.getEstado().equals("BLOQUEADO")) {

                            cntBloqueado++;
                            cntProcesosBloqueados.setText("Procesos Bloqueados: " + cntBloqueado);

                        } else if (proceso.getEstado().equals("LISTO")) {

                            cntListo++;
                            cntProcesosListos.setText("Procesos Listos: " + cntBloqueado);

                        } else if (proceso.getEstado().equals("EJECUTANDO")) {

                            procesoEjecutado = proceso;
                            procesoEjecutando.setText("Proceso usando CPU: " + procesoEjecutado.getPID());

                        }
                    }
                }

            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    private void cargarProcesos() {
        int contador = 0;
        for (int i = 0; i < 40; i++) {
            Proceso p = new Proceso(contador, "LLEGADO", "Kernel", 1, ((int) (Math.random() * (10 - 1))) + 1, 2, 5);
            tiempoLlegada += p.getTiempoRetraso();
            p.setTiempoLlegada(tiempoLlegada);
            this.procesos.add(p);
            contador++;
        }

        this.SizeProcesos = procesos.size();
        this.SizeProcesosPrev = SizeProcesos;

        this.procesos = algoritmoHRRN(this.procesos); // Ordenamos los proceso por response ratio, esto significa que se ejecutaran en este orden.
        this.procesoEjecutado = this.procesos.get(0); //Seteamos a fuego el primer proceso de la lista ya ordenada como ejectuando.

    }

    private List<Proceso> algoritmoHRRN(List<Proceso> procesos) {
        long responseRatio ;


        for (Proceso proceso : procesos) {
            responseRatio = 0;
            responseRatio = (proceso.getTiempoLlegada() + proceso.gettBurst())/ proceso.gettBurst();
            proceso.setResponseRatio(responseRatio);
            System.out.println("Response ratio: " + responseRatio);

        }

        Collections.sort(procesos, new Comparator<Proceso>() {
            @Override
            public int compare(Proceso p1, Proceso p2) {
                return new Long(p1.getResponseRatio()).compareTo(new Long(p2.getResponseRatio()));
            }
        });

        for (Proceso proceso : procesos) {
            System.out.println("PID: " + proceso.getPID() +" , Response Ratio: " + proceso.getResponseRatio());
        }
       



        return procesos;
    }

    private void initUI() {

        // Process panel config
        pn.setBounds(0, 200, this.getWidth(), 200);
        pn.setBackground(Color.LIGHT_GRAY);

        pn.setBorder(new EmptyBorder(25, 25, 25, 35));
        pn.setLayout(new GridLayout(0, this.procesos.size(), 3, 3));
        add(pn);

        // Information panel config
        infoPanel.setBounds(0, getWidth() / 2 - 50, this.getWidth(), 100);
        infoPanel.setBackground(Color.cyan);
        add(infoPanel);
        infoPanel.setLayout(null);

        // CPU information panel config
        cpuInfoPanel.setBounds(50, 0, 200, 100);
        cpuInfoPanel.setBackground(Color.pink);
        // CPU clock label
        cpuClockLbl = new JLabel("Actual Clock: " + CPU_CLOCK + " Hz");
        cpuInfoPanel.add(cpuClockLbl);
        // Cantidad Procesos label
        cntProcesos = new JLabel("Cantidad Procesos: " + procesos.size());
        cpuInfoPanel.add(cntProcesos);
        // Cantidad Procesos Listos
        cntProcesosListos = new JLabel("Procesos Listos: " + 0);
        cpuInfoPanel.add(cntProcesosListos);
        // Cantidad Procesos Bloqueados
        cntProcesosBloqueados = new JLabel("Procesos Bloqueados: " + 0);
        cpuInfoPanel.add(cntProcesosBloqueados);
        // Proceso haciendo uso de CPU
        procesoEjecutando = new JLabel("Proceso usando CPU: ");

        infoPanel.add(procesoEjecutando);
        infoPanel.add(cpuInfoPanel);

        // Button config
        boton1 = new JButton("Comenzar");
        boton1.setBounds(getHeight() - cpuInfoPanel.getWidth() + 50, 50, 100, 40);
        // add(boton1);
        infoPanel.add(boton1);
        boton1.addMouseListener(new MouseInputListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub
                planificar.start();

            }

            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseDragged(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseMoved(MouseEvent e) {
                // TODO Auto-generated method stub

            }

        });
    }

    public static void main(String args[]) {

        new Ventana().setVisible(true);
    }
}