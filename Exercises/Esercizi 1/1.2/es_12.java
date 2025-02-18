/*
 Esercizio 1.2. Progettare e implementare un DFA che riconosca il linguaggio degli identificatori
in un linguaggio in stile Java: un identificatore e una sequenza non vuota di lettere, numeri, ed il `
simbolo di "underscore" _ che non comincia con un numero e che non puo essere composto solo `
dal simbolo _. Compilare e testare il suo funzionamento su un insieme significativo di esempi.
Esempi di stringhe accettate: "x", "flag1", "x2y2", "x_1", "lft_lab", " temp", "x_1_y_2",
"x_", "_5"
Esempi di stringhe non accettate: "5", "221B", "123", "9 to 5", "_"
 */

public class es_12 {
    public static void main(String[] args) {
        System.out.println(scan(args[0]) ? "OK" : "NOPE");
    }

    public static boolean scan(String s){
        int state = 0;
        int i = 0;
        /* stato pozzo == 3 */
        while (state >= 0 && i < s.length()) {
            final char ch = s.charAt(i);
            boolean numero = ch >= '0' && ch <= '9';
            boolean lettera = (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z');
            switch (state) {
                case 0:
                    if(numero)    
                        state = 3;
                    else if(lettera)
                        state = 2;
                    else if(ch == '_')
                        state = 1;
                    else
                        state = -1;
                    break;
                case 1:
                    if(numero || lettera)
                        state = 2;
                    else if(ch == '_')
                        state = 1;
                    else
                        state = -1;
                    break;
                case 2:
                //stato finale
                    if(numero || lettera || ch == '_')
                        state = 2;
                    else 
                        state = -1;
                    break;
                case 3:
                //stato pozzo
                    if(numero || lettera || ch == '_')
                        state = 3;
                    else 
                        state = -1;
                    break;
            }

            i++;
        }
        return state == 2;

    }
}
