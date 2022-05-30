package ui;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.event.MouseInputListener;

import model.Proceso;

public class PanelProceso extends JPanel {

    Proceso proceso;
    JLabel pidLbl;
    Color burstColor, initBurstColor = Color.darkGray, unarrivedColor,
    lblColor;

    static final int PPANCHO = 50;

    static final int PPALTO = 150;

    static final int BARALTURA = 135;

    static boolean showHidden = true;
    	long tInitBurst = 0;




    PanelProceso(Proceso p) {
        proceso = p;
        initPanel();
    }

    /**
     * Construye el panel
     */
    void initPanel() {


        this.addMouseListener(new MouseInputListener() {

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
                
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub
                System.out.println("PID proceso: " + proceso.getPID());
                
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



        setAlignmentX(Component.LEFT_ALIGNMENT);
        setLayout(new BorderLayout());

        pidLbl = new JLabel("" + (int) proceso.getPID());
        pidLbl.setFont(new Font("Helvetica", Font.BOLD, 8));
        pidLbl.setToolTipText(proceso.toString());
        pidLbl.setHorizontalAlignment(SwingConstants.CENTER);

        pidLbl.addMouseListener(new MouseListener() {

            @Override
            public void mouseReleased(MouseEvent arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mousePressed(MouseEvent arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseExited(MouseEvent arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseEntered(MouseEvent arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseClicked(MouseEvent arg0) {
                JOptionPane.showMessageDialog(null, proceso.toString(), "Informacion del Proceso",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        setSize(PPANCHO, PPALTO);

       
        setOpaque(true);
        add(pidLbl, "South");
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        DrawBursts(g);

    }


    void DrawBursts(Graphics g) {
		int initBurstHeight = 0, burstHeight = 0;
		int width = 0;

		initBurstHeight = (int) proceso.gettInitBurst();
		burstHeight = (int) proceso.gettBurst();
		width = (int) PPANCHO - 2; // fuera por un error in swing?

		lblColor = (proceso.getEstado().equals("LLEGADO") ? Color.black
				: (showHidden ? Color.lightGray : Color.white));

		initBurstColor = (proceso.getEstado().equals("LLEGADO") ? Color.lightGray
				: Color.lightGray);

		burstColor = (proceso.getEstado().equals("LLEGADO")) ? (proceso.getEstado().equals("LISTO") == true ? Color.red
				: Color.cyan)
				: (showHidden ? Color.darkGray : Color.white);

		pidLbl.setForeground(lblColor);
		pidLbl.setBackground(proceso.getEstado().equals("LISTO") ? Color.red : Color.white);

		if (proceso.getEstado().equals("LLEGADO") && !proceso.getEstado().equals("BLOQUEADO")) {
			g.setColor(initBurstColor);
			g.drawRect(0, BARALTURA - initBurstHeight, width,
					initBurstHeight);
			g.setColor(burstColor);
			g.fillRect(1, BARALTURA - burstHeight + 1, width - 1,
					burstHeight - 1);
		} else if(proceso.getEstado().equals("BLOQUEADO")){
			g.setColor(Color.green);
			g.drawRect(0, BARALTURA - initBurstHeight, width,
					initBurstHeight);
			g.setColor(Color.green);
			g.fillRect(1, BARALTURA - burstHeight + 1, width - 1,
					burstHeight - 1);
		} else if (showHidden) {
			g.setColor(initBurstColor);
			g.drawRect(0, BARALTURA - initBurstHeight, width,
					initBurstHeight);
		}

	}
}
