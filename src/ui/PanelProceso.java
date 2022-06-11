package ui;

import javax.swing.JPanel;
import javax.swing.Popup;
import javax.swing.PopupFactory;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

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

    static final int anchoProceso = 50;

    static final int altoProceso = 150;

    static final int altura = 135;

    static boolean showHidden = false;
    long tInitBurst = 0;

    private Popup popup;

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
                if (popup != null) {
                    popup.hide();
                }
                JLabel text = new JLabel(proceso.toString());
                popup = PopupFactory.getSharedInstance().getPopup(e.getComponent(), text, e.getXOnScreen(),
                        e.getYOnScreen());
                popup.show();
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub
                popup.hide();

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

        setSize(anchoProceso, altoProceso);

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
        width = (int) anchoProceso - 2;

        lblColor = (proceso.getEstado().equals("LLEGADO") ? Color.black
                : (showHidden ? Color.lightGray : Color.black));

        initBurstColor = (proceso.getEstado().equals("LLEGADO") ? Color.lightGray
                : Color.lightGray);

        initBurstColor = (proceso.getEstado().equals("BLOQUEADO") ? Color.lightGray
                : Color.lightGray);

        burstColor = (proceso.getEstado().equals("LLEGADO"))
                ? (proceso.getEstado().equals("EJECUTADO") == true ? Color.red
                        : Color.cyan)
                : (showHidden ? Color.darkGray : Color.red);

        pidLbl.setForeground(lblColor);
        pidLbl.setBackground(proceso.getEstado().equals("EJECUTADO") ? Color.red : Color.black);

        if (proceso.getEstado().equals("EJECUTADO") && !proceso.getEstado().equals("FINALIZADO")) {
            g.setColor(initBurstColor);
            g.drawRect(0, altura - initBurstHeight, width,
                    initBurstHeight);
            g.setColor(burstColor);
            g.fillRect(1, altura - burstHeight + 1, width - 1,
                    burstHeight - 1);
        } else if (proceso.getEstado().equals("LLEGADO")) {
            g.setColor(Color.cyan);
            g.drawRect(0, altura - initBurstHeight, width,
                    initBurstHeight);
            g.setColor(Color.cyan);
            g.fillRect(1, altura - burstHeight + 1, width - 1,
                    burstHeight - 1);
        } else if (showHidden) {
            g.setColor(initBurstColor);
            g.drawRect(0, altura - initBurstHeight, width,
                    initBurstHeight);
        } else if (proceso.getEstado().equals("BLOQUEADO")) {
            g.setColor(Color.lightGray);
            g.drawRect(0, altura - initBurstHeight, width,
                    initBurstHeight);
            g.setColor(Color.lightGray);
            g.fillRect(1, altura - burstHeight + 1, width - 1,
                    burstHeight - 1);
        }
       
        else if (proceso.getEstado().equals("LISTO")) {
            g.setColor(Color.GREEN);
            g.drawRect(0, altura - initBurstHeight, width,
                    initBurstHeight);
            g.setColor(Color.GREEN);
            g.fillRect(1, altura - burstHeight + 1, width - 1,
                    burstHeight - 1);
        }
        else if (proceso.getEstado().equals("PREPARADO")) {
            g.setColor(Color.GREEN);
            g.drawRect(0, altura - initBurstHeight, width,
                    initBurstHeight);
            g.setColor(Color.GREEN);
            g.fillRect(1, altura - burstHeight + 1, width - 1,
                    burstHeight - 1);
        }

    }
}
