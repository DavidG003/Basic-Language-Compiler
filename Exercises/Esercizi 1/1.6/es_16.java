// Esercizio 1.6. Modificare l’automa dell’esercizio precedente in modo che riconosca il linguaggio
// di stringhe (sull alfabeto {/, *, a}) che contengono commenti delimitati da /* e */, ma con
// la possibilita di avere stringhe prima e dopo come specificato qui di seguito. L’idea ` e che sia `
// possibile avere eventualmente commenti (anche multipli) immersi in una sequenza di simboli
// dell’alfabeto. Quindi l’unico vincolo e che l’automa deve accettare le stringhe in cui un’occorren- `
// za della sequenza /* deve essere seguita (anche non immediatamente) da un’occorrenza della
// sequenza */. Le stringhe del linguaggio possono non avere nessuna occorrenza della sequenza
// /* (caso della sequenza di simboli senza commenti). Implementare l’automa seguendo la costruzione vista in Listing 1.

public class es_16 {
    public static void main(String[] args) {
        System.out.println("Stringa letta da terminale: " + args[0]);
        System.out.println(scan(args[0]) ? "0K" : "NOPE");
    }

    public static boolean scan(String s){
        int state = 0;
        int i = 0;

        while (state >= 0 && i < s.length()) {
            final char ch = s.charAt(i);

            switch (state) {
                case 0:
                    if(ch == 'a' || ch == '*')
                        state = 0;
                    else if(ch == '/')
                        state = 1;
                    else
                        state = -1;
                    break;
                case 1:
                    if(ch == 'a')
                        state = 0;
                    else if(ch == '/')
                        state = 1;
                    else if(ch == '*')
                        state = 2;
                    else
                        state = -1;
                    break;
                case 2:
                    if(ch == 'a' || ch == '/')
                        state = 2;
                    else if(ch == '*')
                        state = 3;
                    else
                        state = -1;
                    break;
                case 3:
                    if(ch == 'a')
                        state = 2;
                    else if(ch == '/')
                        state = 0;
                    else if(ch == '*')
                        state = 3;
                    else
                        state = -1;
                    break;
            }
            i++;
        }

        return state == 0 || state == 1; 
    }
}
