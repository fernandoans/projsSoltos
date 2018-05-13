import java.awt.Container;
import javax.swing.JPanel;
import javax.swing.JTextArea;

class updateThread extends Thread {
    textAreaTest aa;
    Integer i;
    public updateThread(textAreaTest abc)
       {
                aa = abc;
                i = 0;
       }
    
    @Override
   public void run() {
        while(true) {
                try {
                      sleep(1000);
                } catch (InterruptedException e) {
                      //e.printStackTrace();
                  }
              aa.setText(i.toString());
              i++;
            }
   }

}

public class textAreaTest extends javax.swing.JFrame {
    JTextArea area = new JTextArea();
    
    public static void main(String[] args)
    {
        new textAreaTest();
    }

    public textAreaTest()
    {
        updateThread thread = new updateThread(this);
        JPanel panel = new JPanel();
        panel.add(area);
        this.setSize(100, 100);
        Container c = this.getContentPane();
        c.add(area);
        this.pack();
        this.setVisible(true);
        thread.start();
    }

    public void setText(String text)
    {
        area.setText(text);
    }
}