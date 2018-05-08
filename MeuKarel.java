import stanford.karel.*;

public class MeuKarel extends Karel {

    public static void main(String [] args) {
        MeuKarel mk = new MeuKarel();
        mk.start(new String[]{"/home/fernando/Aplicativos/mapa01.w"});
    }
    
    public void run() {
        move();
        move();
        move();
    }
}