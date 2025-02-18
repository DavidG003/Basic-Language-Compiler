
// Esercizio 1.5. Progettare e implementare un DFA con alfabeto {/, *, a} che riconosca il linguaggio di "commenti" delimitati da /* (all’inizio) e */
// (alla fine): cioe l’automa deve accettare le ` stringhe che contengono almeno 4 caratteri che iniziano con 
// /*, che finiscono con */, e che contengono una sola occorrenza della sequenza */, quella finale e (dove l’asterisco della sequenza */
// non deve essere in comune con quello della sequenza /* all’inizio).

public class es_15 {
    public static void main(String[] args) {
        System.out.println("Stringa letta da terminale: " + args[0]);
        System.out.println(scan(args[0]) ? "0K" : "NOPE");
    }

    public static boolean scan(String s){
        int state = 0;
        int i = 0;

        while (state >= 0 && i < s.length()) {
            final char ch = s.charAt(i);
            i++;
            switch (state) {
                case 0:
                    if(ch == '/')
                        state = 1;
                    else if(ch == 'a' || ch == '*')
                        state = 5;
                    else
                        state = -1;
                    break;
                case 1:
                    if(ch == '*')
                        state = 2;
                    else if(ch == 'a' || ch == '/')
                        state = 5;
                    else 
                        state = -1;

                    break;
                case 2:
                    if(ch == '*')
                        state = 3;
                    else if(ch == 'a' || ch == '/')
                        state = 2;
                    else 
                        state = -1;
                    break;
                case 3:
                    if(ch == '*')
                        state = 3;
                    else if(ch == 'a')
                        state = 2;
                    else if(ch == '/')
                        state = 4;
                    else 
                        state = -1;
                    break;
                case 4:
                //stato finale
                    if(ch == 'a' || ch == '/' || ch == '*')
                        state = 5;
                    else 
                        state = -1;
                    break;
                case 5:
                //stato pozzo
                    if(ch == 'a' || ch == '/' || ch == '*')
                        state = 5;
                    else 
                        state = -1;
                    break;
            
            }
        }
       
        return state == 4;
    }
}
