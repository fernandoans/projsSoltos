import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.StringTokenizer;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

class PnCentral extends JPanel {

    private JLabel lbTit01 = new JLabel("Tenha certeza que este programa está na");
    private JLabel lbTit02 = new JLabel("mesma Pasta do Arquivo 'gefqlikview.txt'");
    private JLabel lbTit03 = new JLabel("Mês/Ano (MM/AAAA) ou Ano (AAAA) para Importar:");
    private JButton btImportar = new JButton("Importar");
    private JTextField txAno = new JTextField("",10);

    public PnCentral() {
        JPanel pn1 = new JPanel();
        pn1.add(lbTit01);
        pn1.add(lbTit02);
        JPanel pn2 = new JPanel();
        pn2.add(lbTit03);
        pn2.add(txAno);
        JPanel pn3 = new JPanel();
        pn3.add(btImportar);
        JPanel pn4 = new JPanel();
        // Monta o Painel Central
        this.setLayout(new GridLayout(3,1));
        this.add(pn1);
        this.add(pn2);
        this.add(pn3);
    }
    
    public JButton obterBotao() {
        return btImportar;
    }
    
    public String obterValor() {
        return txAno.getText();
    }
}

public class ImpQlikView extends JFrame implements Runnable {

    private JProgressBar progressBar = new JProgressBar(0, 100);
    private JLabel lbStatus = new JLabel("Importação de Registros...");
    private Thread roda;
    private Connection con;
    private String [] linForm = new String[38];
    private java.text.DateFormat df = new java.text.SimpleDateFormat("dd/MM/yyyy");
    private String periodo;
    private PnCentral pnCentral = new PnCentral();
    
    public ImpQlikView() {
        super("Importar QlikView");
        this.setSize(350,280);
        pnCentral.obterBotao().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                importar();
            }
        });
        // Monta a Janela
        this.add(pnCentral);
        this.add(lbStatus, BorderLayout.SOUTH);
        this.add(progressBar, BorderLayout.NORTH);
        this.setVisible(true);
    }
    
    private void importar() {
        progressBar.setValue(0);
        this.lbStatus.setText(" Obtendo informações...");
        this.update(getGraphics());
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        this.setPeriodo(pnCentral.obterValor());
        int total = this.totRegistros();
        progressBar.setMaximum(total);
        this.lbStatus.setText(" São " + total + " registros a importar...");
        this.update(getGraphics());
        progressBar.setMaximum(total);
        if (roda == null) {
          roda = new Thread(this);
          roda.start(); 
        }
    }
    
    public void run() {
        conexao();
        int total = 0;
        try {
            PreparedStatement pstm = con.prepareStatement(
                "INSERT INTO QLK_VIEW (" +
                " DOSSIER, LINHADOSSIER, PNR, BILHETE, PRODUTO, CODIGOPRODUTO, SISTEMA, FILIAL, DATACRIACAO, DATAEMISSAO, " +
                " DATAINICIO, ORIGEM, DATAFIM, DESTINO, CIA, CLASSE, CODIGOCLIENTE, CODIGOFORNECEDOR, OFFICEID, " +
                " CODIGOEMISSOR, TIPODOC, NUMDOC, TIPODOCFAT, DOCEMISSAO, DOCVENCIMENTO, TIPOCC, TARIFA, TAXAS, " +
                " OUTRASTAXAS, TOTAL, TIPOSEG, TIPOVOO, USRCRIACAO, FEEDOSS, FEELINHA, QTDNOITES, VOIDR, CATEG) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                "?, ?, ?, ?, ?, ?, ?, ?, ?)");  
                
            BufferedReader br = new BufferedReader(
                new FileReader("D:/ARQS/gefqlikview.txt"));
            String lin;
            int [] tam = new int[38];
            double val;
            Date dtAtual = null;
            java.sql.Date sqlDate;
            while ((lin = br.readLine()) != null) {
                linForm = lin.split(";",-1);
                if (linForm[8].indexOf(periodo) > 0) {
                    for (byte i = 0; i < linForm.length; i++) {
                        if (i > 25 && i < 30) {
                            val = Double.parseDouble(linForm[i].substring(1).replace(',','.'));
                            pstm.setDouble(i+1, val);
                        } else {
                            if (i == 8 || i == 9 || i == 10 || i == 12 || i == 23 || i == 24) {
                                try {
                                    if (linForm[i].equals("00/00/0000")) {
                                        sqlDate = null;
                                    } else {
                                        dtAtual = (Date)df.parse(linForm[i]);
                                        sqlDate = new java.sql.Date(dtAtual.getTime());
                                    }
                                } catch (java.text.ParseException e) {    
                                    sqlDate = null;
                                }
                                pstm.setDate(i+1, sqlDate);
                            } else {
                                pstm.setString(i+1, linForm[i]);
                            }
                        }
                    }
                    pstm.executeUpdate();
                    total++;
                    progressBar.setValue(total);
                    //this.update(getGraphics());
                }
            }
            br.close();
            con.close();
        } catch (Exception e) {
            System.out.println("Erro 3:" + e.getMessage());
        }
        this.setCursor(Cursor.getDefaultCursor());
        lbStatus.setText(" Finalizado.");
        roda = null; 
        this.repaint();
    }     

    private void executar() {
        this.setPeriodo("01/2017");
        System.out.println(totRegistros());
    }
    
    public int totRegistros() {
        int conta = 0;
        try {
            BufferedReader br = new BufferedReader(
                new FileReader("D:/ARQS/gefqlikview.txt"));
            String lin;
            while ((lin = br.readLine()) != null) {
              linForm = lin.split(";",-1);
              if (linForm[8].indexOf(periodo) > 0) {
                  conta += 1;
              }
            }
            br.close();
        } catch (Exception e) {
            System.out.println("Erro 3:" + e.getMessage());
        }
        return conta;
    }
    
    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }
    
    private void conexao() {
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
        } catch (Exception e) {
            System.out.println("Erro 2:" + e.getMessage());
        }
    }
    
    public static void main(String [] args) {
        new ImpQlikView().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
