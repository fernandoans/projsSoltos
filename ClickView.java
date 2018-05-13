import java.io.*;
import java.util.StringTokenizer;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JProgressBar;

public class ClickView {

    private Connection con;
    private String [] linForm = new String[38];
    private java.text.DateFormat df = new java.text.SimpleDateFormat("dd/MM/yyyy");
    private String periodo;
    
    public static void main(String [] args) {
        new ClickView().executar();
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
        
    public void incluir(JProgressBar progressBar) {
        conexao();
        int total = 0;
        try {
            PreparedStatement pstm = con.prepareStatement(
                "INSERT INTO QLK_VIEW (" +
                " DOSSIER, LINHADOSSIER, PNR, BILHETE, PRODUTO, CODIGOPRODUTO, SISTEMA, FILIAL, DATACRIACAO, DATAEMISSAO, " +
                " DATAINICIO, ORIGEM, DATAFIM, DESTINO, CIA, CLASSE, CODIGOCLIENTE, CODIGOFORNECEDOR, OFFICEID, " +
                " CODIGOEMISSOR, TIPODOC, NUMDOC, TIPODOCFAT, DOCEMISSAO, DOCVENCIMENTO, TIPOCC, TARIFA, TAXAS, " +
                " OUTRASTAXAS, TOTAL, TIPOSEG, TIPOVOO, USRCRIACAO, FEEDOSS, FEELINHA, QTDNOITES, VOIDR, CATEG) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");  
                
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
                }
            }
            br.close();
            con.close();
        } catch (Exception e) {
            System.out.println("Erro 3:" + e.getMessage());
        }
    }
}