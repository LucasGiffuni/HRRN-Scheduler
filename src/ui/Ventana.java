package ui;

import java.awt.Color;

import javax.swing.JFrame;

public class Ventana extends JFrame {
    ProcesoUI panel;

    public Ventana() {
        setTitle("Planificador UCU");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // set the size, maximum size.
        setLayout(null);
        //setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        init();
        pack();
    }

    private void init() {
        panel = new ProcesoUI(getWidth());
        add(panel);
       
    }

    public static void main(String args[]) {
        
        new Ventana().setVisible(true);
    }
}