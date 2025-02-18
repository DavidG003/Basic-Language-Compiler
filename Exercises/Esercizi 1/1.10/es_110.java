/*
 
Progettare e implementare un DFA che riconosca il linguaggio di stringhe che contengono il tuo nome e tutte le stringhe ottenute dopo la sostituzione di un carattere del nome con un altro qualsiasi.
 Ad esempio, nel caso di uno studente che si chiama Paolo, il DFA deve accettare la stringa "Paolo" (cioè il nome scritto correttamente), 
ma anche le stringhe "Pjolo", "caolo", "Pa%lo", "Paola" e "Parlo" (il nome dopo la sostituzione di un carattere), ma non "Eva", "Perro", "Pietro" oppure "P*o*o".
 */


public class es_110 {
    public static void main(String[] args) {
        System.out.println(scan(args[0]) ? "OK" : "NOPE");
    }

    public static boolean scan(String s){
        // Nome: David
        int state = 0;
        int i = 0;
        //siccome vengono accettati caretteri qualsiasi, -1 assume il significato di stato pozzo non di carattere letto non appartenente all'alfabeto

        //scelta implementativa: Siccome la consegna specifica "stringhe che contengono il tuo nome" però gli esempi proprosti suggeriscono che l'automa possa solo accettare una stringa di lunghezza 5
        //                       ho costruito un automa che dopo o prima la lettura del mio nome se trova altri caratteri non riconosce la stringa. (es: non riconosce 222David4 oppure Dav9d2)
        while (state >= 0 && i < s.length()) {
            final char ch = s.charAt(i);

            switch (state) {
                case 0:
                    if(ch == 'D')    
                        state = 1;
                    else
                        state = 6;
                    break;
                case 1:
                    if(ch == 'a')    
                        state = 2;
                    else
                        state = 7;
                    break;
                case 2:
                    if(ch == 'v')    
                        state = 3;
                    else
                        state = 8;
                    break;
                case 3:
                    if(ch == 'i')    
                        state = 4;
                    else
                        state = 9;
                    break;
                case 4:
                    state = 5;
                    break;
                case 5:
                    //stato finale
                    state = -1;
                    break;
                case 6:
                    if(ch == 'a')    
                        state = 7;
                    else
                        state = -1;
                    break;
                case 7:
                    if(ch == 'v')    
                        state = 8;
                    else
                        state = -1;
                    break;
                case 8:
                    if(ch == 'i')    
                        state = 9;
                    else
                        state = -1;
                    break;
                case 9:
                    if(ch == 'd')    
                        state = 5;
                    else
                        state = -1;
                    break;
            }

            i++;
        }
        return state == 5;

    }
}