/**
 * Simulador de Figuras para o Teste de Rorschach
 * 
 * @author Fernando Anselmo
 * @version 1.0
 */

import javax.swing.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Rorschach extends JDialog {

    private JButton btMudar = new JButton("Mudar");
    private JButton btSalvar = new JButton("Salvar");
    private Tableta tableta = new Tableta();
    
    public Rorschach() {
        this.setTitle("Teste de Rorschach - O que você vê?");
        this.setSize(640, 375);
        this.setResizable(false);
        JPanel pn = new JPanel();
        pn.add(btMudar);
        pn.add(btSalvar);
        btMudar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                mudarDesenho();
            }
        });
        btSalvar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                salvarDesenho();
            }
        });
        this.add(pn, BorderLayout.SOUTH);
        this.add(tableta);
        this.setVisible(true);
    }
    
    public void mudarDesenho() {
        tableta.mudarImagem();
    }
    
    public void salvarDesenho() {
        String nomeImg = JOptionPane.showInputDialog("Informe o nome da Imagem:", "Rorschach.png");
        try {
           ImageIO.write(tableta.getImage(), "png", new File(nomeImg));
        } catch (IOException ex) {
           JOptionPane.showMessageDialog(this, "Ocorreu um erro ao salvar " + ex.getMessage(), 
               "Teste de Rorschach", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void main(String [] args) {
        new Rorschach().setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

}

class Tableta extends JPanel {
    
    private BufferedImage image = new BufferedImage(640, 335, BufferedImage.TYPE_INT_ARGB);
    private Graphics graph = image.getGraphics();

    public Tableta() {
        this.setBackground(Color.WHITE);
        desenhar(this.graph);
        this.repaint();
    }

    public void mudarImagem() {
        desenhar(this.graph);
        this.repaint();
    }
    
    public BufferedImage getImage() {
        return image;
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(image, 0, 0, null);
    }
    
    public void desenhar(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 640, 335);
        short pEsq, pTop;
        byte larg, alt, rgb;
        for (short i = 1; i < 250; i += 50) {
            g.setColor(Color.BLACK);
            for (short j = 0; j < 150; j++) {
                rgb = (byte)(Math.random()*110);
                if (j < 145) {
                    g.setColor(new Color(rgb, rgb, rgb));
                } else {
                    g.setColor(new Color(255, rgb, rgb));
                }
                pEsq = (short)(Math.random()*(300-i)+i);
                pTop = (short)(Math.random()*295+5);
                larg = (byte)(Math.random()*30+1);
                alt = (byte)(Math.random()*20+1);
                g.fillOval(pEsq, pTop, larg, alt);
                g.fillOval(630-pEsq-larg, pTop, larg, alt);
            }
        }
    }
}