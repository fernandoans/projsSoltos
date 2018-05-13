import java.util.Date; 
import java.util.Calendar; 

public class Token {

    public static void main(String [] args) {
        new Token().executar();
    }
    
    private void executar() {
        for (int i = 0; i < 20; i++) {
            System.out.println(gerar());
        }
    }
    
    public String gerar() {
        String fator = "";
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        fator += regra((int)(Math.random()*100+1), (byte)3);
        fator += regra(c.get(Calendar.SECOND), (byte)3);
        fator += regra(c.get(Calendar.MINUTE), (byte)3);
        fator += regra(c.get(Calendar.HOUR), (byte)3);
        fator += regra(c.get(Calendar.DATE), (byte)4);
        fator += regra(c.get(Calendar.MONTH), (byte)3);
        fator += regra(c.get(Calendar.YEAR), (byte)6);
        fator += regra((int)(Math.random()*100+1), (byte)3);
        return fator;
    }
    
    private String regra(int valor, byte tam) {
        valor = valor * (int)(Math.random()*10+1);
        valor = valor + (int)(Math.random()*10+1);
        String numGer = "" + valor;
        while (numGer.length() < tam) {
            numGer = "0" + numGer;
        }
        return numGer;
    }
}
