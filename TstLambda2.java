import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

interface Nova1 {
    void metodo(String arg1);
}
interface Nova2 {
    void metodo(int arg2);
}

public class TstLambda2 {

    public static void main(String [] args) {
        new TstLambda2().executar();
    }
    
    public void executar() {
        exComListas();
        exComArray();
        exComInter();
    }
    public void exComListas() {
        List<String> frutas = new ArrayList<String>();
        frutas.add("Tomate Cereja");
        frutas.add("Morango");
        frutas.add("Maçã");
        
        frutas.forEach(f -> System.out.println("Minha fruta é " + f));
    }
    public void exComArray() {
        Integer [] nums = {13, 23, 45, 32, 12, 10, 7, 8};
        Arrays.sort(nums, (pri, seg) -> pri.compareTo(seg));
        for (int i = 0; i < nums.length; i++) {
            System.out.print(nums[i] + " ");
        }
        System.out.println();
    }
    public void exComInter() {
        class Teste {
            public void novinha(Nova1 n) {
                n.metodo("Minha mensagem");
            }
            public void novinha(Nova2 n) {
                n.metodo(10);
            }
        }
        /*
        new Teste().novinha(new Nova() {
            public void metodo(String arg) {
                System.out.println(arg);
            }
        });
        */
        new Teste().novinha((String arg1) -> System.out.println(arg1));
        new Teste().novinha((int arg2) -> System.out.println(arg2));
    }
                
    
}
