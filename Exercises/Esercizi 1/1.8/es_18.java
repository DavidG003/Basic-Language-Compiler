/*
 *Progettare e implementare un DFA che, come in Esercizio 1.3 (esercizi obbligatori su DFA), riconosca il linguaggio di stringhe che contengono matricola e cognome di studenti del turno 2
  o del turno 3 del laboratorio, ma in cui il cognome precede il numero di matricola (in altre parole, le posizioni del cognome e matricola sono scambiate rispetto all’Esercizio 1.3).
 */


public class es_18 {
    public static void main(String[] args) {
        System.out.println(scan(args[0]) ? "OK" : "NOPE");
    }
    public static boolean scan(String s){
        int state = 0;
        int i = 0;

        while(state >= 0 && i < s.length()){
            final char ch = s.charAt(i);
            
            /* Scelta implementativa 1: un cognome che inizia per lettera minuscola non fa parte delle stringhe riconosciute dall'automa*/
            /* Scelta implementativa 2: un cognome che contiene maiuscole (tranne la iniziale) non fa parte delle stringhe riconosciute dall'automa*/

            boolean pari = (ch == '0' || ch == '2' || ch == '4' || ch == '6' || ch == '8');
            boolean dispari = (ch == '1' || ch == '3' || ch == '5' || ch == '7' || ch == '9');
            
            switch (state) {
                case 0:
                    if(ch >= 'A' && ch <= 'K')
                        state = 2;
                    else if(ch >= 'L' && ch <= 'Z')
                        state = 1;
                    else if(dispari || pari || (ch >= 'a' && ch <= 'z'))
                        state = 7;
                    else 
                        state = -1;
                    break;

                case 1:
                //iniziale da L a Z
                    if(ch >= 'a' && ch <='z')
                        state = 1;
                    else if(pari)
                        state = 5;
                    else if(dispari)
                        state = 3;
                    else if((ch >= 'A' && ch <= 'Z'))
                        state = 7;
                    else 
                        state = -1;
                    break;

                case 2:
                //iniziale da A a K
                    if(ch >= 'a' && ch <='z')
                        state = 2;
                    else if(pari)
                        state = 4;
                    else if(dispari)
                        state = 6;
                    else if((ch >= 'A' && ch <= 'Z'))
                        state = 7;
                    else 
                        state = -1;
                    break;
            
                case 3:
                //stato finale per iniziale da L a Z
                    if(pari)
                        state = 5;
                    else if(dispari)
                        state = 3;
                    else if((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z'))
                        state = 7;
                    else 
                        state = -1;
                    break;

                case 4:
                //stato finale per iniziale da A a K
                    if(pari)
                        state = 4;
                    else if(dispari)
                        state = 6;
                    else if((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z'))
                        state = 7;
                    else 
                        state = -1;
                    break;

                case 5:
                //ultimo numero pari per iniziale da L a Z
                    if(pari)
                        state = 5;
                    else if(dispari)
                        state = 3;
                    else if((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z'))
                        state = 7;
                    else 
                        state = -1;
                    break;
                case 6:
                //ultimo numero dispari per iniziale da A a K
                    if(pari)
                        state = 4;
                    else if(dispari)
                        state = 6;
                    else if((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z'))
                        state = 7;
                    else 
                        state = -1;
                    break;
                
                case 7:
                //stato pozzo
                    if((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z') || pari || dispari)
                        state = 7;
                    else 
                        state = -1;
            }

            i++;
        }
        return state == 3 || state == 4;
    }
}