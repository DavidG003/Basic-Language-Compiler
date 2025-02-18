/*
Modificare l’automa dell’esercizio 1.3 (esercizi obbligatori su DFA) in modo che riconosca le combinazioni di matricola e cognome di studenti del turno 2 o del turno 3 del laboratorio,
 dove il numero di matricola e il cognome possono essere separati da una sequenza di spazi, e possono essere precedute e/o seguite da sequenze eventualmente vuote di spazi. 
 Per esempio, l’automa deve accettare la stringa "654321 Rossi" e " 123456 Bianchi " (dove, nel secondo esempio, ci sono spazi prima del primo carattere e dopo l’ultimo carattere),
  ma non "1234 56Bianchi" e "123456Bia nchi".
Per questo esercizio, i cognomi composti (con un numero arbitrario di parti) possono essere accettati: per esempio, la stringa "123456De Gasperi" deve essere accettata.
 Modificare l’implementazione Java dell’automa di conseguenza. 
 */

public class es_17 {
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
                        state = 7;
                    else if(pari)
                        state = 2;
                    else if(dispari)
                        state = 1;
                    else if(ch == ' ')
                        state = 0;
                    else 
                        state = -1;
                    break;

                case 1:
                //ultimo num letto dispari
                    if(ch >= 'L' && ch <= 'Z')
                        state = 5;
                    else if(pari)
                        state = 2;
                    else if(dispari)
                        state = 1;
                    else if(ch == ' ')
                        state = 4;
                    else if((ch >= 'a' && ch <='z') || (ch >= 'A' && ch <= 'K'))
                        state = 7;
                    else 
                        state = -1;
                    break;

                case 2:
                //ultimo num letto pari
                    if(ch >= 'A' && ch <= 'K')
                        state = 5;
                    else if(pari)
                        state = 2;
                    else if(dispari)
                        state = 1;
                    else if(ch == ' ')
                        state = 3;
                    else if((ch >= 'a' && ch <='z') || (ch >= 'L' && ch <= 'Z'))
                        state = 7;
                    else 
                        state = -1;
                    break;
                
                case 3:
                //spazio vuoto dopo pari
                    if(ch >= 'A' && ch <= 'K')
                        state = 5;
                    else if(ch == ' ')
                        state = 3;
                    else if((ch >= 'a' && ch <='z') || (ch >= 'L' && ch <= 'Z') || pari || dispari)
                        state = 7;
                    else 
                        state = -1;
                    break;
                case 4:
                //spazio vuoto dopo dispari
                    if(ch >= 'L' && ch <= 'Z')
                        state = 5;
                    else if(ch == ' ')
                        state = 4;
                    else if((ch >= 'a' && ch <='z') || (ch >= 'A' && ch <= 'K') || pari || dispari)
                        state = 7;
                    else 
                        state = -1;
                    break;

                case 5:
                //stato finale
                    if(ch >= 'a' && ch <= 'z')
                        state = 5;
                    else if(ch == ' ')
                        state = 6;
                    else if((ch >= '0' && ch <= '9') || (ch>= 'A' && ch <= 'Z'))
                        state = 7;
                    else
                        state = -1;
                    break;
                
                case 6:
                //stato finale
                    if(ch >= 'A' && ch <= 'Z')
                        state = 5;
                    else if(ch == ' ')
                        state = 6;
                    else if((ch >= '0' && ch <= '9') || (ch>= 'a' && ch <= 'z'))
                        state = 7;
                    else
                        state = -1;
                    break;
                
                case 7:
                //stato pozzo
                    if((ch >= '0' && ch <= '9') || (ch>= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z') || ch == ' ' )
                        state = 7;
                    else
                        state = -1;
                    break;
            }

            i++;
        }

        return state == 5 || state == 6;
    }
}
