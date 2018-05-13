import java.io.*;
import java.util.StringTokenizer;
import java.util.*;

class Cliente {
  
    private int CODIGOCLIENTE;
    private String NOMECLIENTE;
    private String MODELOFEE;
    private int CODIGOGRUPOCIENTE;
    private String GRUPOCLIENTE;

    public int getCODIGOCLIENTE() {
		return CODIGOCLIENTE;
	}

	public void setCODIGOCLIENTE(int cODIGOCLIENTE) {
		CODIGOCLIENTE = cODIGOCLIENTE;
	}

	public String getNOMECLIENTE() {
		return NOMECLIENTE;
	}

	public void setNOMECLIENTE(String nOMECLIENTE) {
		NOMECLIENTE = nOMECLIENTE;
	}

	public String getMODELOFEE() {
		return MODELOFEE;
	}

	public void setMODELOFEE(String mODELOFEE) {
		MODELOFEE = mODELOFEE;
	}

	public int getCODIGOGRUPOCIENTE() {
		return CODIGOGRUPOCIENTE;
	}

	public void setCODIGOGRUPOCIENTE(int cODIGOGRUPOCIENTE) {
		CODIGOGRUPOCIENTE = cODIGOGRUPOCIENTE;
	}

	public String getGRUPOCLIENTE() {
		return GRUPOCLIENTE;
	}

	public void setGRUPOCLIENTE(String gRUPOCLIENTE) {
		GRUPOCLIENTE = gRUPOCLIENTE;
	}
    
    public String retNomeCliente(int CODIGOCLIENTE) {
        if (this.CODIGOCLIENTE == CODIGOCLIENTE) {
            return NOMECLIENTE;
        }
        return null;
    }
}

public class LeGrava {
    
    private List<Cliente> clientes = new ArrayList<Cliente>();

    public static void main(String [] args) {
        new LeGrava().executar();
    }
    
    public void executar() {
        //carregarCliente();
        ler();
    }

    public void ler() {
        try {
            BufferedReader br = new BufferedReader(
                new FileReader("D:/gefqlikview.txt"));
            String lin;
            int i = 0;
            while ((lin = br.readLine()) != null) {
                System.out.println(lin);
                StringTokenizer st2 = new StringTokenizer(lin, ";");
                while (st2.hasMoreElements()) {
                    System.out.println(st2.nextElement());
                }                
                i++;
                if (i > 2) break;
            }
            br.close();
        } catch (IOException e) {
            
        }
    }

    public void carregarCliente() {
        try {
            BufferedReader br = new BufferedReader(
                new FileReader("D:/CLIENTES.csv"));
            String lin;
            int i = 0;
            byte conta = 0;
            while ((lin = br.readLine()) != null) {
                if (i > 0) {
                    StringTokenizer st2 = new StringTokenizer(lin, ",");
                    Cliente cli = new Cliente();
                    while (st2.hasMoreElements()) {
                        switch (conta) {
                            case 0: cli.setCODIGOCLIENTE(Integer.parseInt(""+st2.nextElement())); conta++; break;
                            case 1: cli.setNOMECLIENTE(""+st2.nextElement()); conta++; break;
                            case 2: cli.setMODELOFEE(""+st2.nextElement()); conta++; break;
                            case 3: cli.setCODIGOGRUPOCIENTE(Integer.parseInt(""+st2.nextElement())); conta++; break;
                            case 4: cli.setGRUPOCLIENTE(""+st2.nextElement()); conta++; break;
                        }
                        if (conta == 5) {
                            clientes.add(cli);
                            conta = 0;
                        }
                    }                
                    i++;
                }
            }
            br.close();
        } catch (IOException e) {
            
        }
    }
}