/*
 Esercizio 1.4. Progettare e implementare un DFA che riconosca il linguaggio delle costanti numeriche in virgola mobile
utilizzando la notazione scientifica dove il simbolo e indica la funzione esponenziale con base 10.
 L’alfabeto del DFA contiene i seguenti elementi: le cifre numeriche
0, 1, . . . , 9, il segno . (punto) che precede una eventuale parte decimale, i segni + (piu) e ` - (meno)
per indicare positivita o negativit ` a, e il simbolo ` e.
Le stringhe accettate devono seguire le solite regole per la scrittura delle costanti numeriche.
In particolare, una costante numerica consiste di due segmenti, il secondo dei quali e opzionale: `
il primo segmento e una sequenza di cifre numeriche che (1) pu ` o essere preceduta da un segno `
+ o meno -, (2) puo essere seguita da un segno punto ` ., che a sua volta deve essere seguito da
una sequenza non vuota di cifre numeriche; il secondo segmento inizia con il simbolo e, che a
sua volta e seguito da una sequenza di cifre numeriche che soddisfa i punti (1) e (2) scritti per il `
primo segmento. Si nota che, sia nel primo segmento, sia in un eventuale secondo segmento, un
segno punto . non deve essere preceduto per forza da una cifra numerica.
Esempi di stringhe accettate: "123", "123.5", ".567", "+7.5", "-.7", "67e10", "1e-2",
"-.7e2", "1e2.3"
Esempi di stringhe non accettate: ".", "e3", "123.", "+e6", "1.2.3", "4e5e6", "++3"

 */

public class es_14 {
    public static void main(String[] args) {
        System.out.println(scan(args[0]) ? "0K" : "NOPE");
    }

    public static boolean scan(String s){
        int state = 0;
        int i = 0;

        while(state >= 0 && i < s.length()){
            final char ch = s.charAt(i);
            
            switch (state) {
                case 0:
                    if(ch == '+' || ch == '-')
                        state = 1;
                    else if(ch == '.')
                        state = 2;
                    else if(ch >= '0' && ch <='9')
                        state = 4;
                    else if(ch == 'e')
                        state = 5;
                    else 
                        state = -1;
                    break;

                case 1:
                    if(ch == '+' || ch == '-' || ch == 'e')
                        state = 5;
                    else if(ch == '.')
                        state = 2;
                    else if(ch >= '0' && ch <='9')
                        state = 4;
                    else 
                        state = -1;
                    break;

                case 2:
                    if(ch == '+' || ch == '-' || ch =='.' || ch == 'e')
                        state = 5;
                    else if(ch >= '0' && ch <='9')
                        state = 3;
                    else 
                        state = -1;
                    break;

                case 3:
                //stato finale
                    if(ch == '+' || ch == '-' || ch =='.')
                        state = 5;
                    else if(ch >= '0' && ch <='9')
                        state = 3;
                    else if(ch == 'e')
                        state = 6;
                    else 
                        state = -1;
                    break;

                case 4:
                //stato finale
                    if(ch == '+' || ch == '-')
                        state = 5;
                    else if(ch == '.')
                        state = 2;
                    else if(ch >= '0' && ch <='9')
                        state = 4;
                    else if(ch == 'e')
                        state = 6;
                    else 
                        state = -1;
                    break;
                
                case 5:
                //stato pozzo
                    if(ch == '+' || ch == '-' || ch =='.' || ch == 'e' || (ch >= '0' && ch <= '9'))
                        state = 5;
                    else 
                        state = -1;
                    break;
                //specchio della prima parte dallo stato 6 in poi solo per gestire il caso in cui e non si ripeta due volte la e
                case 6:
                    if(ch == '+' || ch == '-')
                        state = 7;
                    else if(ch == '.')
                        state = 8;
                    else if(ch >= '0' && ch <='9')
                        state = 10;
                    else if(ch == 'e')
                        state = 5;
                    else 
                        state = -1;
                    break;

                case 7:
                    if(ch == '+' || ch == '-' || ch == 'e')
                        state = 5;
                    else if(ch == '.')
                        state = 8;
                    else if(ch >= '0' && ch <='9')
                        state = 10;
                    else 
                        state = -1;
                    break;

                case 8:
                    if(ch == '+' || ch == '-' || ch =='.' || ch == 'e')
                        state = 5;
                    else if(ch >= '0' && ch <='9')
                        state = 9;
                    else 
                        state = -1;
                    break;

                case 9:
                //stato finale
                    if(ch == '+' || ch == '-' || ch =='.' || ch == 'e')
                        state = 5;
                    else if(ch >= '0' && ch <='9')
                        state = 9;
                    else 
                        state = -1;
                    break;

                case 10:
                //stato finale
                    if(ch == '+' || ch == '-' || ch =='e')
                        state = 5;
                    else if(ch == '.')
                        state = 8;
                    else if(ch >= '0' && ch <='9')
                        state = 10;
                    else 
                        state = -1;
                    break;
            }
            i++;
        }
        
        return state == 3 || state == 4 || state == 9 || state == 10;
    }
}
