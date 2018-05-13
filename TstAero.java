public class TstAero {
    public static void main(String [] args) {
        new TstAero().testar();
    }
    public void testar() {
        String a = "SDU";
        String b = "GRU;SDU;GIG";
        String c = "SDU";
        if (a.length() > b.length()) {
            System.out.println(a.indexOf(b) > 0);
        } else {
            System.out.println(b.indexOf(a) > 0);
        }
        if (b.length() > c.length()) {
            System.out.println(b.indexOf(c) > 0);
        } else {
            System.out.println(c.indexOf(b) > 0);
        }
    }    
}
