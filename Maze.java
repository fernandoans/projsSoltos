import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

/**
 * Maze - Labirinto.
 * 
 * Esta é uma classe para simular a construção de um Labirinto.
 */
public class Maze extends JFrame {
    
    private int n = 25;            // Dimensão do Labirinto
    private int size = 2;          // Pos Esq/Top  
    private int width = 500;
    private int height = 500;
    private int min = 0;
    private int max = n+size;
    // Controle das Paredes
    private boolean[][] north;
    private boolean[][] east;
    private boolean[][] south;
    private boolean[][] west;
    private boolean[][] visited;
    private boolean done = false;

    /**
     * Construtor da Classe.
     * 
     * É criado para montar a janela, criar um primeiro labirinto e armar um evento
     * para o botão OK de modo a refazer o labirinto criado.
     */
    public Maze() {
        super("Labirinto");
        refazer();
        this.setSize(width, height+30);
        JButton btRefazer = new JButton("Refazer");
        btRefazer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                refazer();
            }
        });
        JPanel pn1 = new JPanel();
        pn1.add(btRefazer);
        this.add(pn1, BorderLayout.SOUTH);
        this.setVisible(true);
    }
    
    public void paint(Graphics g) {
        super.paint(g);
        int m = n+1;
        for (int x = 1; x < m; x++) {
            for (int y = 1; y < m; y++) {
                if (south[x][y]) {
                    g.drawLine(scaleX(x), scaleY(y), scaleX(x+1), scaleY(y));
                }
                if (north[x][y]) {
                    g.drawLine(scaleX(x), scaleY(y+1), scaleX(x+1), scaleY(y+1));
                }
                if (west[x][y] && !(x == 1 && y == 1)) {
                    g.drawLine(scaleX(x), scaleY(y), scaleX(x), scaleY(y+1));
                }
                if (east[x][y] && !(x == (m-1) && y == (m-1))) {
                    g.drawLine(scaleX(x+1), scaleY(y), scaleX(x+1), scaleY(y+1));
                }
            }
        }
    }

    private void refazer() {
        iniciar();
        gerar();
        repaint();
    }        

    private int scaleX(int x) {
        double ret = width  * (x - min) / (double)(max - min); 
        return (int)ret;
    }
    private int scaleY(int y) { 
        double ret = height * (max - y) / (double)(max - min); 
        return (int)ret;
    }
    
    private void iniciar() {
        // Celulas ja visitadas
        visited = new boolean[n+2][n+2];
        for (byte x = 0; x < n+2; x++) {
            visited[x][0] = true;
            visited[x][n+1] = true;
        }
        for (byte y = 0; y < n+2; y++) {
            visited[0][y] = true;
            visited[n+1][y] = true;
        }
        // Inicializar todas as outras celulas
        north = new boolean[n+2][n+2];
        east  = new boolean[n+2][n+2];
        south = new boolean[n+2][n+2];
        west  = new boolean[n+2][n+2];
        for (byte i = 0; i < n+2; i++) {
            for (byte j = 0; j < n+2; j++) {
                north[i][j] = true;
                east[i][j] = true;
                south[i][j] = true;
                west[i][j] = true;
            }
        }
    }

    private void gerar(int x, int y) {
        visited[x][y] = true;
        // Enquanto for alguem nao visitado
        while (!visited[x][y+1] || !visited[x+1][y] || !visited[x][y-1] || !visited[x-1][y]) {
            // Gera um vizinho aleatorio (Truque de Knuth's)
            int r = 0;
            while (true) {
                r = (int)(Math.random()*4);
                if (r == 0 && !visited[x][y+1]) {
                    north[x][y] = false;
                    south[x][y+1] = false;
                    gerar(x, y + 1);
                    break;
                } else if (r == 1 && !visited[x+1][y]) {
                    east[x][y] = false;
                    west[x+1][y] = false;
                    gerar(x+1, y);
                    break;
                } else if (r == 2 && !visited[x][y-1]) {
                    south[x][y] = false;
                    north[x][y-1] = false;
                    gerar(x, y-1);
                    break;
                } else if (r == 3 && !visited[x-1][y]) {
                    west[x][y] = false;
                    east[x-1][y] = false;
                    gerar(x-1, y);
                    break;
                }
            }
        }
    }

    private void gerar() {
        gerar(1, 1);
    }
    
    public static void main(String[] args) {
        new Maze();
    }
}