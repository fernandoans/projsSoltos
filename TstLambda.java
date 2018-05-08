import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class TstLambda extends JFrame {

    private JButton bt = new JButton("OK");
    
    public TstLambda() {
        super("Teste de Lambda");
        this.setSize(300,300);
        this.setLayout(new FlowLayout());
        this.add(bt);
/*        bt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Clicou");
            }
        });
*/        
        bt.addActionListener(e -> {
            LocalDate dt = LocalDate.now().minusDays(3);
            String dtF = dt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            System.out.println("Clicou Lambda: " + dtF);
        });
            
        this.setVisible(true);
    }
    
    
    public static void main(String [] args) {
        new TstLambda();
    }

}
