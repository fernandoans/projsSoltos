public class UltimoDia {

    public static void main(String [] args) {
        new UltimoDia().executar("01");
        new UltimoDia().executar("02");
        new UltimoDia().executar("03");
        new UltimoDia().executar("11");
    }
    
    public void executar(String mesValidade) {
        String [] ultDiaMes = { "31", "28", "31", "30", "31", "30", "31", "31", "30", "31", "30", "31"};
        String diaValidade = ultDiaMes[Integer.parseInt(mesValidade)-1];         
        System.out.println(diaValidade);
    }


}
