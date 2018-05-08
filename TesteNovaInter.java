public class TesteNovaInter implements NovaInter {

    public static void main(String [] args) {
        new TesteNovaInter().executar();
    }

    public void executar() {
        novo();
    }
    
    public void novo() {
        System.out.println("Sou outro");
    }
}
