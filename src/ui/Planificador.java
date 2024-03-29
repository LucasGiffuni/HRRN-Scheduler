package ui;

import java.awt.Color;
import java.awt.Font;
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
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.MouseInputListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;

import java.awt.GridLayout;
import java.awt.event.MouseEvent;

import java.awt.event.ComponentAdapter;

import java.awt.event.ComponentEvent;
import java.awt.Component;

import model.Proceso;

public class Planificador extends JFrame implements Runnable {

    private PanelProceso procesoPanel;
    private JPanel pn = new JPanel();
    private JPanel infoPanel = new JPanel();
    private JPanel cpuInfoPanel = new JPanel();
    private JLabel CPU_LOCK_LABEL, cntProcesosBloqueados, cntProcesos, velocidadP, procesoEjecutando;
    JPanel panel = new JPanel();

    private Proceso procesoEjecutado = null;
    private int CPU_CLOCK = 0;
    boolean verificado = true;
    public boolean configurado = false;
    public ConfigWindow configWindow = new ConfigWindow();

    int tiempoLlegada;

    private JButton btnPlanificador, btnConfig, btnDesbloquear;
    private Thread planificar = new Thread(this);

    private ArrayList<Proceso> procesos = new ArrayList<Proceso>();

    private ArrayList<Proceso> procesosListos = new ArrayList<Proceso>();

    private ArrayList<Proceso> procesosBloqueados = new ArrayList<Proceso>();

    private boolean corriendo = false;
    private int contador2 = 0;

    int contadorbloq = 0;

    boolean nose = false;

    boolean aux = false;

    // CONFIG WINDOW VARIABLES
    private int CICLESPEED;
    private int PROCESSNUMBER;
    private Color cbloqueo, clisto, cejecutando;

    Border blackline2 = BorderFactory.createTitledBorder("Title");

    public Planificador() {

        setTitle("HRRN SCHEDULER");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        initUI();

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
                pn.setBorder(BorderFactory.createTitledBorder(blackline2, "procesos"));
                // Ordenamos los procesos a ejecutar por orden del RESPONSE RATIO

                panel.removeAll();
                for (Proceso proceso : procesos) {
                    JLabel pr = new JLabel("Proceso: " + proceso.getPID() + ", Estado: " + proceso.getEstado());
                    pr.setFont(new Font(pr.getFont().getName(), Font.PLAIN, 20));

                    if (proceso.getEstado().equals("BLOQUEADO")) {
                        contadorbloq++;
                        cntProcesosBloqueados.setText("Procesos Bloqueados: " + contadorbloq);

                    }
                    repaint();
                    panel.add(pr);
                }
                contadorbloq = 0;
                if (corriendo) {
                    CPU_CLOCK++; // CONTADOR CICLOS CPU
                    cntProcesos.setText("Cantidad de procesos: " + configWindow.getPROCESSNUMBER());
                    velocidadP.setText("Velocidad de simulación: " + configWindow.getCICLESPEED());
                    CPU_LOCK_LABEL.setText("CICLO: " + CPU_CLOCK);

                    if (!procesoEjecutado.getEstado().equals("BLOQUEADO")
                            && !procesoEjecutado.getEstado().equals("FINALIZADO")) {
                        procesoEjecutado.ejecutando(CPU_CLOCK);
                    } else if (procesoEjecutado.getEstado().equals("BLOQUEADO")) {
                        if (procesosListos.size() > 0 && !procesoEjecutado.getEstado().equals("EJECUTADO")
                                && !procesoEjecutado.getEstado().equals("BLOQUEADO")) {

                            procesoEjecutado = null;

                            procesoEjecutado = procesos.get(0);
                            procesosListos.remove(procesoEjecutado);

                            System.out.println("PROCESO " + procesosListos.get(0).getPID() + " LISTO PARA EJECUTAR");

                        } else {
                            System.out.println("AGREGANDO PROCESO : " + procesoEjecutado.getPID() + " A BLOQUEADO");
                            procesosBloqueados.add(procesoEjecutado);
                            procesos.remove(procesoEjecutado);
                            procesoEjecutado = null;
                            procesoEjecutado = procesos.get(0);

                        }

                    } else if (procesoEjecutado.getEstado().equals("FINALIZADO")) {
                        procesos.remove(procesoEjecutado);
                        procesoEjecutado = null;
                        procesoEjecutado = procesos.get(0);
                    }

                    repaint();

                    for (int i = 0; i < procesosBloqueados.size(); i++) {

                        if (procesosBloqueados.get(i).getEstado().equals("BLOQUEADO")) {
                            System.out.println("DESBLOQUEANDO: " + procesosBloqueados.get(i).getPID());
                            procesosBloqueados.get(i).ejecutando(CPU_CLOCK);
                        } else {
                            procesosBloqueados.remove(procesosBloqueados.get(i));
                        }
                        if (procesosBloqueados.get(i).getEstado().equals("LISTO")) {
                            System.out.println("PROCESO " + procesosBloqueados.get(i).getPID() + " AGREGADO A LISTO");
                            procesosListos.add(procesosBloqueados.get(i));
                            // procesos.add(procesosBloqueados.get(i));
                            
                            procesosListos = algoritmoHRRN(procesosListos);

                            procesosBloqueados.remove(procesosBloqueados.get(i));
                        }
                    }
                }

                repaint();

            }

        }, 0, configWindow.getCICLESPEED(), TimeUnit.MILLISECONDS);

    }

    private void init() {
        pn.setBorder(BorderFactory.createTitledBorder(blackline2, "procesos"));
        pn.setBounds(0, 200, this.getWidth() - 10, 200);
        // pn.setBackground(Color.lightGray);

        pn.setBorder(new EmptyBorder(25, 25, 25, 35));
        pn.setLayout(new GridLayout(0, this.procesos.size(), 3, 3));
        add(pn);

        // Ordenamos los procesos en orden por PID para generar el GUI
        Collections.sort(procesos, new Comparator<Proceso>() {
            @Override
            public int compare(Proceso p1, Proceso p2) {
                return new Long(p1.getPID()).compareTo(new Long(p2.getPID()));
            }
        });
        for (Proceso proceso : procesos) {
            procesoPanel = new PanelProceso(proceso);
            pn.add(procesoPanel);
        }
    }

    // Problema con orden de la lista, al volver a ordenar por responseTime se
    // uestran los procesos en ese orden.
    // Problema con como se muestran los procesos, una vez finalizado el proceso se
    // "borra", no queda el marco, esto es porque borramos el proceso de la lista
    // cuando este finaliza.cls
    private void cargarProcesos() {
        int contador = 0;
        this.contador2 = 0;
        for (int i = 0; i < configWindow.getPROCESSNUMBER(); i++) {
            Proceso p = new Proceso(contador, "LLEGADO", configWindow.getMaxBurst(), configWindow.getMaxRetardo(),
                    configWindow.getMaxBloqueo());
            tiempoLlegada -= p.getTiempoRetraso();
            p.setTiempoLlegada(tiempoLlegada);
            this.procesos.add(p);
            contador++;
        }

        procesos = algoritmoHRRN(this.procesos); // Ordenamos los proceso por response ratio, esto significa que se
                                                 // ejecutaran en este orden.
        ordenarLista();
        procesoEjecutado = this.procesos.get(0); // Seteamos a fuego el primer proceso de la lista ya ordenada como
                                                 // ejectuando.

    }

    private void ordenarLista() {
        Collections.sort(procesos, new Comparator<Proceso>() {
            public int compare(Proceso o1, Proceso o2) {
                if (o1.getResponseRatio() > o2.getResponseRatio()) {
                    return 1;
                } else if (o1.getResponseRatio() < o2.getResponseRatio()) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });

    }

    private ArrayList<Proceso> algoritmoHRRN(ArrayList<Proceso> aux) {
        System.out.println("ORDENANDO PROCESOS POR RESPONSE RATIO");
        int responseRatio;

        for (Proceso proceso : aux) {
            if (proceso.getEstado().equals("FINALIZADO")) {
                proceso.setResponseRatio(10000);
            } else {
                responseRatio = 0;
                responseRatio = (proceso.getTiempoLlegada() + proceso.gettBurst()) / proceso.gettBurst();
                proceso.setResponseRatio(responseRatio);
            }

        }
        ordenarLista();
        for (Proceso p : aux) {
            System.out.println("PID " + p.getPID() + " Ratio: " + p.getResponseRatio());

        }

        return aux;
    }

    private void initUI() {

        // Process panel config

        infoPanel.setBorder(BorderFactory.createTitledBorder(blackline2, "status"));

        // Information panel config
        infoPanel.setBounds(0, getWidth() / 2 - 150, this.getWidth(), 200);

        // infoPanel.setBackground(Color.cyan);
        add(infoPanel);
        infoPanel.setLayout(null);

        // CPU information panel config
        cpuInfoPanel.setBounds(50, 10, 200, 100);
        // cpuInfoPanel.setBackground(Color.pink);

        // Cantidad Procesos label
        velocidadP = new JLabel("Velocidad de simulación: " + configWindow.getCICLESPEED());
        cpuInfoPanel.add(velocidadP);
        // Cantidad Procesos Listos
        cntProcesos = new JLabel("Cantidad de procesos " + configWindow.getPROCESSNUMBER());
        cpuInfoPanel.add(cntProcesos);
        // Cantidad Procesos Bloqueados
        cntProcesosBloqueados = new JLabel("Procesos Bloqueados: " + 0);
        cpuInfoPanel.add(cntProcesosBloqueados);
        // Proceso haciendo uso de CPU
        procesoEjecutando = new JLabel("Proceso usando CPU: ");

        cpuInfoPanel.setBorder(BorderFactory.createTitledBorder(blackline2, "Información simulador"));

        infoPanel.add(procesoEjecutando);
        infoPanel.add(cpuInfoPanel);

        // panel.setBounds(getWidth() - 500, 0, 400, 200);
        panel.setLayout(new BoxLayout(panel, 1));

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(getWidth() - 475, 10, 400, 200);
        Border blackline2 = BorderFactory.createTitledBorder("Title");
        scrollPane.setBorder(BorderFactory.createTitledBorder(blackline2, "Estados proceso"));
        infoPanel.add(scrollPane);

        // CICLE LABEL
        CPU_LOCK_LABEL = new JLabel("CICLO: ");
        CPU_LOCK_LABEL.setBounds(getHeight() - cpuInfoPanel.getWidth() + 65, 0, 100, 40);
        infoPanel.add(CPU_LOCK_LABEL);

        // Button planificador
        btnPlanificador = new JButton("Comenzar");
        btnPlanificador.setBounds(getHeight() - cpuInfoPanel.getWidth() + 50, 50, 100, 40);
        infoPanel.add(btnPlanificador);

        btnPlanificador.setEnabled(false);

        btnPlanificador.addMouseListener(new MouseInputListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub

                if (!corriendo) {
                    corriendo = true;
                    btnPlanificador.setText("Pausar");

                    planificar.start();
                } else {
                    corriendo = false;
                    btnPlanificador.setText("Continuar");
                }

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

        // Button planificador
        btnConfig = new JButton("Configurar");
        btnConfig.setBounds(getHeight() - cpuInfoPanel.getWidth() + 50, 100, 100, 40);
        infoPanel.add(btnConfig);

        btnConfig.addMouseListener(new MouseInputListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub
                configWindow.setup(); // Inicializamos ventana de configuracion
                configWindow.setVisible(true);
                btnPlanificador.setEnabled(true);
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

        configWindow.addComponentListener(new ComponentAdapter() {
            public void componentShown(ComponentEvent evt) {
                Component c = (Component) evt.getSource();

            }

            public void componentHidden(ComponentEvent evt) {

                tiempoLlegada = configWindow.getPROCESSNUMBER();

                cargarProcesos();
                init();
                repaint();
                invalidate();
                validate();
                btnConfig.setEnabled(false);

            }

            public void componentMoved(ComponentEvent evt) {

            }

            public void componentResized(ComponentEvent evt) {

            }
        });

        repaint();
    }

    public void setCICLESPEED(int cICLESPEED) {
        CICLESPEED = cICLESPEED;
    }

    public void setPROCESSNUMBER(int pROCESSNUMBER) {
        PROCESSNUMBER = pROCESSNUMBER;
    }

    public void setCbloqueo(Color cbloqueo) {
        this.cbloqueo = cbloqueo;
    }

    public void setClisto(Color clisto) {
        this.clisto = clisto;
    }

    public void setCejecutando(Color cejecutando) {
        this.cejecutando = cejecutando;
    }

    public static void main(String args[]) {

        new Planificador().setVisible(true);
    }
}
