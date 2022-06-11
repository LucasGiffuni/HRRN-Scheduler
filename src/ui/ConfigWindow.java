package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.beans.Visibility;
import java.util.Hashtable;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.MouseInputListener;

import java.awt.*;

public class ConfigWindow extends JFrame implements ChangeListener {

    static JSlider b;
    static JTextField t;

    private int CICLESPEED;
    private int PROCESSNUMBER;

    private String maxBurst, maxRetardo, maxBloqueo;

    private Color cbloqueo, clisto, cejecutando;
    String[] colors = { "red", "green", "blue", "cyan", "light gray" };

    public ConfigWindow() {

    }

    public void setup() {

        setTitle("Configuracion");
        setAlwaysOnTop(true);
        setUndecorated(true);
        setSize(750, 450);
        setResizable(false);
        getRootPane().setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.black));

        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        JPanel panel = new JPanel();
        panel.setSize(this.getSize());
        panel.setBackground(Color.pink);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel cicleSpeed = new JLabel("Velocidad de simulación en milisegundos = " + "50");
        panel.add(cicleSpeed);

        JPanel sliderPanel = new JPanel();
        JButton botonFinalzar = new JButton("Finalizar");

        JPanel processPanel = new JPanel();
        processPanel.setLayout(new GridLayout(4, 2));
        processPanel.setBackground(Color.pink);

        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new GridLayout(3, 2));
        statusPanel.setBackground(Color.pink);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 1));
        buttonPanel.setBackground(Color.pink);

        b = new JSlider(0, 2000, 1000);
        b.setPreferredSize(new Dimension(600, 50));

        Hashtable position = new Hashtable();
        position.put(0, new JLabel("0"));
        position.put(500, new JLabel("500"));
        position.put(1000, new JLabel("1000"));
        position.put(1500, new JLabel("1500"));
        position.put(1750, new JLabel("1750"));
        position.put(2000, new JLabel("2000"));
        b.setPaintLabels(true);
        b.setMajorTickSpacing(250);
        b.setPaintTicks(true);
        b.setPaintLabels(true);

        b.setLabelTable(position);
        sliderPanel.add(b);
        panel.add(sliderPanel);

        b.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                cicleSpeed.setText("Velocidad de simulación en milisegundos = " + ((JSlider) e.getSource()).getValue());
                setCICLESPEED(((JSlider) e.getSource()).getValue());
            }
        });

        JLabel processNumber = new JLabel("Cantidad de procesos: ");
        processPanel.add(processNumber);
        t = new JTextField("", 16);
        processPanel.add(t);
        t.setText("50");

        JLabel burst = new JLabel("Max Burst: ");
        processPanel.add(burst);
        JTextField t2 = new JTextField("", 16);
        processPanel.add(t2);
        t2.setText("99");

        JLabel retraso = new JLabel("Max Retraso: ");
        processPanel.add(retraso);
        JTextField t3 = new JTextField("", 16);
        processPanel.add(t3);
        t3.setText("50");

        JLabel bloqueo = new JLabel("Max Bloqueo: ");
        processPanel.add(bloqueo);
        JTextField t4 = new JTextField("", 16);
        processPanel.add(t4);
        t4.setText("10");

        setCICLESPEED(50);
        t.addActionListener(new ActionListener() {
            // capture the event on JTextField
            public void actionPerformed(ActionEvent e) {
                // get and display the contents of JTextField in the console
                setPROCESSNUMBER(Integer.parseInt(t.getText()));

            }
        });
        t2.addActionListener(new ActionListener() {
            // capture the event on JTextField
            public void actionPerformed(ActionEvent e) {
                // get and display the contents of JTextField in the console
                setMaxBurst(t2.getText());
            }
        });
        t3.addActionListener(new ActionListener() {
            // capture the event on JTextField
            public void actionPerformed(ActionEvent e) {
                // get and display the contents of JTextField in the console
                setMaxRetardo(t3.getText());
            }
        });
        t4.addActionListener(new ActionListener() {
            // capture the event on JTextField
            public void actionPerformed(ActionEvent e) {
                // get and display the contents of JTextField in the console
                setMaxBloqueo(t4.getText());
            }
        });

        JLabel cBloqueo = new JLabel("Color Bloqueo: ");
        statusPanel.add(cBloqueo);
        JComboBox colorPicker1 = new JComboBox(colors);
        statusPanel.add(colorPicker1);

        JLabel cListo = new JLabel("Color Listo: ");
        statusPanel.add(cListo);
        JComboBox colorPicker2 = new JComboBox(colors);
        statusPanel.add(colorPicker2);

        JLabel cEjecutando = new JLabel("Color Ejecutando: ");
        statusPanel.add(cEjecutando);
        JComboBox colorPicker3 = new JComboBox(colors);
        statusPanel.add(colorPicker3);

        buttonPanel.add(botonFinalzar);
        colorPicker1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (colorPicker1.getSelectedItem().toString()) {
                    case "red":
                        cbloqueo = Color.red;
                        break;
                    case "green":
                        cbloqueo = Color.green;
                        break;
                    case "blue":
                        cbloqueo = Color.blue;
                        break;
                    case "cyan":
                        cbloqueo = Color.cyan;
                        break;
                    case "light gray":
                        cbloqueo = Color.lightGray;
                        break;
                    default:
                        break;
                }

            }
        });
        colorPicker2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (colorPicker2.getSelectedItem().toString()) {
                    case "red":
                        clisto = Color.red;
                        break;
                    case "green":
                        clisto = Color.green;
                        break;
                    case "blue":
                        clisto = Color.blue;
                        break;
                    case "cyan":
                        clisto = Color.cyan;
                        break;
                    case "light gray":
                        clisto = Color.lightGray;
                        break;
                    default:
                        break;
                }

            }
        });
        colorPicker2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (colorPicker3.getSelectedItem().toString()) {
                    case "red":
                        cejecutando = Color.red;
                        break;
                    case "green":
                        cejecutando = Color.green;
                        break;
                    case "blue":
                        cejecutando = Color.blue;
                        break;
                    case "cyan":
                        cejecutando = Color.cyan;
                        break;
                    case "light gray":
                        cejecutando = Color.lightGray;
                        break;
                    default:
                        break;
                }

            }
        });

        botonFinalzar.addMouseListener(new MouseInputListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mousePressed(MouseEvent e) {

                PROCESSNUMBER = Integer.parseInt(t.getText());
                maxBurst = t2.getText();
                maxRetardo = t3.getText();
                maxBloqueo = t4.getText();

                setVisible(false);

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

        panel.add(processPanel);
        panel.add(statusPanel);
        panel.add(buttonPanel);

        getContentPane().add(panel);

    }

    public static void main(String args[]) {

        new ConfigWindow().setVisible(true);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        // TODO Auto-generated method stub

    }

    public void setCICLESPEED(int cICLESPEED) {
        CICLESPEED = cICLESPEED;

    }

    public void setPROCESSNUMBER(int pROCESSNUMBER) {
        PROCESSNUMBER = pROCESSNUMBER;
    }

    public int getCICLESPEED() {
        return CICLESPEED;
    }

    public int getPROCESSNUMBER() {
        return PROCESSNUMBER;
    }

    public Color getCbloqueo() {
        return cbloqueo;
    }

    public Color getClisto() {
        return clisto;
    }

    public Color getCejecutando() {
        return cejecutando;
    }

    public String getMaxBurst() {
        return maxBurst;
    }

    public String getMaxRetardo() {
        return maxRetardo;
    }

    public String getMaxBloqueo() {
        return maxBloqueo;
    }

    public void setMaxBurst(String maxBurst) {
        this.maxBurst = maxBurst;
    }

    public void setMaxRetardo(String maxRetardo) {
        this.maxRetardo = maxRetardo;
    }

    public void setMaxBloqueo(String maxBloqueo) {
        this.maxBloqueo = maxBloqueo;
    }

}
