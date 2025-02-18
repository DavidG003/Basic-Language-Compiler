/*
Esercizio 1.3. Progettare e implementare un DFA che riconosca il linguaggio di stringhe che
contengono un numero di matricola seguito (subito) da un cognome, dove la combinazione di
matricola e cognome corrisponde a studenti del turno 2 o del turno 3 del laboratorio di Linguaggi
Formali e Traduttori. Si ricorda le regole per suddivisione di studenti in turni:
• Turno T1: cognomi la cui iniziale e compresa tra A e K, e il numero di matricola ` e dispari; `
• Turno T2: cognomi la cui iniziale e compresa tra A e K, e il numero di matricola ` e pari; `
• Turno T3: cognomi la cui iniziale e compresa tra L e Z, e il numero di matricola ` e dispari; `
• Turno T4: cognomi la cui iniziale e compresa tra L e Z, e il numero di matricola ` e pari. `
Per esempio, 123456Bianchi e 654321Rossi sono stringhe del linguaggio, mentre
654321Bianchi e 123456Rossi no. Nel contesto di questo esercizio, un numero di matricola non ha un numero prestabilito di cifre (ma deve essere composto di almeno una cifra). Un
cognome corrisponde a una sequenza di lettere, e deve essere composto di almeno una lettera.
Quindi l’automa deve accettare le stringhe 2Bianchi e 122B ma non 654322 e Rossi.
 */

public class es_13 {
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
                    if((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z'))
                        state = 4;
                    else if(pari)
                        state = 2;
                    else if(dispari)
                        state = 1;
                    else 
                        state = -1;
                    break;

                case 1:
                //ultimo num letto dispari
                    if(ch >= 'L' && ch <= 'Z')
                        state = 3;
                    else if(pari)
                        state = 2;
                    else if(dispari)
                        state = 1;
                    else if((ch >= 'a' && ch <='z') || (ch >= 'A' && ch <= 'K'))
                        state = 4;
                    else 
                        state = -1;
                    break;

                case 2:
                //ultimo num letto pari
                    if(ch >= 'A' && ch <= 'K')
                        state = 3;
                    else if(pari)
                        state = 2;
                    else if(dispari)
                        state = 1;
                    else if((ch >= 'a' && ch <='z') || (ch >= 'L' && ch <= 'Z'))
                        state = 4;
                    else 
                        state = -1;
                    break;
            
                case 3:
                //stato finale
                    if(ch >= 'a' && ch <= 'z')
                        state = 3;
                    else if((ch >= '0' && ch <= '9') || (ch>= 'A' && ch <= 'Z'))
                        state = 4;
                    else
                        state = -1;
                    break;

                case 4:
                //stato pozzo
                    if((ch >= '0' && ch <= '9') || (ch>= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z') )
                        state = 4;
                    else
                        state = -1;
                    break;
            }

            i++;
        }
        return state == 3;
    }
}
