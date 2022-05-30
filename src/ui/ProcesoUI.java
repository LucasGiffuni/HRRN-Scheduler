package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.HexFormat;
import java.util.List;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import model.Proceso;

public class ProcesoUI extends JPanel {

    Proceso proceso;
    List<Proceso> procesos = new ArrayList<Proceso>();
    // List<ProcessChartUI> processUI = new ArrayList<ProcessChartUI>();

    final int HEIGHT = 200;
    JButton t = new JButton("tt");

    public ProcesoUI(int width) {

        setBounds(0, 200, width, HEIGHT);
        // setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.LIGHT_GRAY);
        setBorder(new EmptyBorder(50, 50, 0, 0));
        getProcesos();
    }

    private void getProcesos() {
        this.procesos.add(new Proceso(1, "BLOQUEADO", "Kernel", 1, ((int) (Math.random() * (10 - 1))) + 1, 2, 5));
        this.procesos.add(new Proceso(2, "BLOQUEADO", "Kernel", 1, ((int) (Math.random() * (10 - 1))) + 1, 2, 5));
        this.procesos.add(new Proceso(3, "BLOQUEADO", "Kernel", 1, ((int) (Math.random() * (10 - 1))) + 1, 2, 5));
        this.procesos.add(new Proceso(4, "LISTO", "Kernel", 1, ((int) (Math.random() * (10 - 1))) + 1, 2, 5));
        this.procesos.add(new Proceso(5, "EJECUCION", "Kernel", 1, ((int) (Math.random() * (10 - 1))) + 1, 2, 5));
        this.procesos.add(new Proceso(5, "LISTO", "Kernel", 1, ((int) (Math.random() * (10 - 1))) + 1, 2, 5));
        this.procesos.add(new Proceso(5, "LISTO", "Kernel", 1, ((int) (Math.random() * (10 - 1))) + 1, 2, 5));
        this.procesos.add(new Proceso(5, "LISTO", "Kernel", 1, ((int) (Math.random() * (10 - 1))) + 1, 2, 5));
        this.procesos.add(new Proceso(5, "LISTO", "Kernel", 1, ((int) (Math.random() * (10 - 1))) + 1, 2, 5));
        this.procesos.add(new Proceso(5, "LISTO", "Kernel", 1, ((int) (Math.random() * (10 - 1))) + 1, 2, 5));
        this.procesos.add(new Proceso(5, "LISTO", "Kernel", 1, ((int) (Math.random() * (10 - 1))) + 1, 2, 5));
        this.procesos.add(new Proceso(5, "LISTO", "Kernel", 1, ((int) (Math.random() * (10 - 1))) + 1, 2, 5));
        this.procesos.add(new Proceso(5, "LISTO", "Kernel", 1, ((int) (Math.random() * (10 - 1))) + 1, 2, 5));
        this.procesos.add(new Proceso(5, "LISTO", "Kernel", 1, ((int) (Math.random() * (10 - 1))) + 1, 2, 5));
        this.procesos.add(new Proceso(5, "LISTO", "Kernel", 1, ((int) (Math.random() * (10 - 1))) + 1, 2, 5));
        this.procesos.add(new Proceso(5, "LISTO", "Kernel", 1, ((int) (Math.random() * (10 - 1))) + 1, 2, 5));
        this.procesos.add(new Proceso(5, "LISTO", "Kernel", 1, ((int) (Math.random() * (10 - 1))) + 1, 2, 5));
        this.procesos.add(new Proceso(5, "LISTO", "Kernel", 1, ((int) (Math.random() * (10 - 1))) + 1, 2, 5));
        this.procesos.add(new Proceso(5, "LISTO", "Kernel", 1, ((int) (Math.random() * (10 - 1))) + 1, 2, 5));

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (procesos == null || procesos.size() == 0)
            return;
        double minValue = 0;
        double maxValue = 0;
        for (int i = 0; i < procesos.size(); i++) {
            if (minValue > procesos.get(i).getTiempoEnEjecucion())
                minValue = procesos.get(i).getTiempoEnEjecucion();
            if (maxValue < procesos.get(i).getTiempoEnEjecucion())
                maxValue = procesos.get(i).getTiempoEnEjecucion();
        }

        Dimension d = getSize();
        int clientWidth = d.width;
        int clientHeight = d.height;
        int barWidth = clientWidth / procesos.size();

        Font titleFont = new Font("SansSerif", Font.BOLD, 20);
        FontMetrics titleFontMetrics = g.getFontMetrics(titleFont);
        Font labelFont = new Font("SansSerif", Font.PLAIN, 10);
        FontMetrics labelFontMetrics = g.getFontMetrics(labelFont);

        int y;
        int x;

        int top = titleFontMetrics.getHeight();
        int bottom = labelFontMetrics.getHeight();
        if (maxValue == minValue)
            return;
        double scale = (clientHeight - top - bottom) / (maxValue - minValue);
        y = clientHeight - labelFontMetrics.getDescent();
        g.setFont(labelFont);

        for (int i = 0; i < procesos.size(); i++) {
            int valueX = i * barWidth + 1;
            int valueY = top;
            int height = (int) (procesos.get(i).getTiempoEnEjecucion() * scale);
            if (procesos.get(i).getTiempoEnEjecucion() >= 0)
                valueY += (int) ((maxValue - procesos.get(i).getTiempoEnEjecucion()) * scale);
            else {
                valueY += (int) (maxValue * scale);
                height = -height;
            }

            switch (procesos.get(i).getEstado()) {
                case "BLOQUEADO":
                    g.setColor(Color.blue);
                    break;
                case "LISTO":
                    g.setColor(Color.green);

                    break;
                case "EJECUCION":
                    g.setColor(Color.red);

                    break;

                default:
                    break;
            }
            int width = barWidth - 2;
            

            int labelWidth = labelFontMetrics.stringWidth("Proceso" + i);
            x = i * barWidth + (barWidth - labelWidth) / 2;
            g.drawString("Proceso" + i, x, y);
            
          
        }

    }

    public Proceso getProceso() {
        return proceso;
    }

    public void setProceso(Proceso proceso) {
        this.proceso = proceso;
    }

}
