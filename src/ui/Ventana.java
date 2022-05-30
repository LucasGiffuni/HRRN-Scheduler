package ui;

import java.awt.Color;
import java.awt.Container;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import model.Proceso;

public class Ventana extends JFrame {
    ProcesoUI panel;
    PanelProceso procesoPanel;
    JPanel pn = new JPanel();
    List<Proceso> procesos = new ArrayList<Proceso>();

    public Ventana() {

        setTitle("Planificador UCU");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // set the size, maximum size.
        setLayout(null);
        // setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        cargarProcesos(); //Cargar procesos al panel de los procesos.
        pn.setBounds(0, 200, this.getWidth(), 200);
        pn.setBackground(Color.LIGHT_GRAY);

        pn.setBorder(new EmptyBorder(25, 25, 25, 35));
        pn.setLayout(new GridLayout(0, this.procesos.size(), 3, 3));

        add(pn);
        init();

        pack();

    }

    private void init() {
       for (Proceso proceso : procesos) {
            procesoPanel = new PanelProceso(proceso);
            pn.add(procesoPanel);

       }


    }

    private void cargarProcesos() {
        this.procesos.add(new Proceso(1, "LLEGADO", "Kernel", 1, ((int) (Math.random() * (10 - 1))) + 1, 2, 5));
        this.procesos.add(new Proceso(2, "LLEGADO", "Kernel", 1, ((int) (Math.random() * (10 - 1))) + 1, 2, 5));
        this.procesos.add(new Proceso(3, "BLOQUEADO", "Kernel", 1, ((int) (Math.random() * (10 - 1))) + 1, 2, 5));
        this.procesos.add(new Proceso(4, "EJECUCION", "Kernel", 1, ((int) (Math.random() * (10 - 1))) + 1, 2, 5));
        this.procesos.add(new Proceso(5, "LISTO", "Kernel", 1, ((int) (Math.random() * (10 - 1))) + 1, 2, 5));

    }

    public static void main(String args[]) {

        new Ventana().setVisible(true);
    }
}