import java.io.*;
import java.util.*;

public class Lexer {

    public static int line = 1;
    private char peek = ' ';

    private void readch(BufferedReader br) {
        try {
            peek = (char) br.read();
        } catch (IOException exc) {
            peek = (char) -1; // ERROR
        }
    }

    public Token lexical_scan(BufferedReader br) {
        int open_line;
        long sum;
        boolean end_comm, only_underscores;
        String s;
        // se trovo uno spazio vuoto oppure un a capo leggo il prossimo carattere
        while (peek == ' ' || peek == '\t' || peek == '\n' || peek == '\r') {
            if (peek == '\n')
                line++;
            readch(br);
        }

        // vedo cosa ho letto
        switch (peek) {
            case '!':
                peek = ' ';
                return Token.not;

            // ... gestire i casi di ( ) [ ] { } + - * / ; , ... //
            case '(':
                peek = ' ';
                return Token.lpt;
            case ')':
                peek = ' ';
                return Token.rpt;

            case '[':
                peek = ' ';
                return Token.lpq;
            case ']':
                peek = ' ';
                return Token.rpq;

            case '{':
                peek = ' ';
                return Token.lpg;
            case '}':
                peek = ' ';
                return Token.rpg;

            case '+':
                peek = ' ';
                return Token.plus;
            case '-':
                peek = ' ';
                return Token.minus;

            case '*':
                peek = ' ';
                return Token.mult;

            // gestione commenti
            case '/':
                readch(br);
                // commenti multi linea
                if (peek == '*') {
                    end_comm = false;
                    open_line = line;
                    readch(br);
                    while (peek != (char) -1 && !end_comm) {
                        if (peek == '*') {
                            readch(br);
                            if (peek == '/') {
                                end_comm = true;
                                readch(br);
                            } else if(peek == '\n') line++;
                        } else {
                            if(peek == '\n') line++;
                            readch(br);
                        }
                    }
                    if (peek == (char) -1 && !end_comm) {
                        System.err.println("Comment opened at line: " + open_line + " left unclosed");
                        return null;
                    }
                    return lexical_scan(br);
                }
                // commenti singola linea
                else if (peek == '/') {
                    readch(br);
                    while (peek != (char) -1 && peek != '\n') {
                        readch(br);
                    }

                    return lexical_scan(br);
                }

                // divisione
                else {
                    return Token.div;
                }

            case ';':
                peek = ' ';
                return Token.semicolon;
            case ',':
                peek = ' ';
                return Token.comma;

            case '&':
                readch(br);
                if (peek == '&') {
                    peek = ' ';
                    return Word.and;
                } else {
                    System.err.println("Erroneous character" + " after & : " + peek + " at line: " + line);
                    return null;
                }

                // ... gestire i casi di || < > <= >= == <> ... //
            case '|':
                readch(br);
                if (peek == '|') {
                    peek = ' ';
                    return Word.or;
                } else {
                    System.err.println("Erroneous character" + " after | : " + peek + " at line: " + line);
                    return null;
                }

            case '>':
                readch(br);
                if (peek == '=') {
                    peek = ' ';
                    return Word.ge;
                } else {
                    return Word.gt;
                }

            case '<':
                readch(br);
                if (peek == '=') {
                    peek = ' ';
                    return Word.le;
                } else if (peek == '>') {
                    peek = ' ';
                    return Word.ne;
                } else {
                    return Word.lt;
                }

            case '=':
                readch(br);
                if (peek == '=') {
                    peek = ' ';
                    return Word.eq;
                } else {
                    System.err.println("Erroneous character" + " after = : " + peek + " at line: " + line);
                    return null;
                }
            case ':':
                readch(br);
                if (peek == '=') {
                    peek = ' ';
                    return Word.init;
                } else {
                    System.err.println("Erroneous character" + " after : is " + peek + " at line: " + line);
                    return null;
                }

            case (char) -1:
                return new Token(Tag.EOF);

            default:
                if (Character.isLetter(peek) || peek == '_') {
                    // ... gestire il caso degli identificatori e delle parole chiave //
                    s = "";
                    only_underscores = peek == '_';
                    while (Character.isLetter(peek) || Character.isDigit(peek) || peek == '_') {
                        s = s + peek;
                        if (only_underscores && peek != '_') {
                            only_underscores = false;
                        }
                        readch(br);
                    }

                    if (only_underscores) {
                        System.err.println("Unvalid identifier: " + s + " at line: " + line);
                        return null;
                    }

                    switch (s) {
                        case "assign":
                            return Word.assign;
                        case "to":
                            return Word.to;
                        case "if":
                            return Word.iftok;
                        case "else":
                            return Word.elsetok;
                        case "do":
                            return Word.dotok;
                        case "for":
                            return Word.fortok;
                        case "begin":
                            return Word.begin;
                        case "end":
                            return Word.end;
                        case "print":
                            return Word.print;
                        case "read":
                            return Word.read;
                        default:
                            return new Word(Tag.ID, s);
                    }

                } else if (Character.isDigit(peek)) {
                    // ... gestire il caso dei numeri ... //
                    sum = 0;
                    // lo zero può essere solo scritto con '0', i zeri multipli o se viene seguito
                    // da un carattere/numero invoca un error
                    if (peek == '0') {
                        readch(br);
                        if (Character.isDigit(peek) || Character.isLetter(peek)) {
                            System.err.println("Invalid integer: 0 followed by " + peek + " at line: " + line);
                            return null;
                        }
                        return new NumberTok(0);
                    }

                    while (Character.isDigit(peek)) {
                        sum = (sum * 10) + (peek - '0');
                        readch(br);
                    }

                    if (Character.isLetter(peek)) {
                        System.err.println("Invalid integer: " + sum + " followed by: " + peek + " at line: " + line);
                        return null;
                    }
                    return new NumberTok(sum);
                } else {
                    System.err.println("Erroneous character: " + peek + " at line: " + line);
                    return null;
                }
        }
    }

    public static void main(String[] args) {
        Lexer lex = new Lexer();
        String path = "test.txt"; // il percorso del file da leggere
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Token tok;
            do {
                tok = lex.lexical_scan(br);
                System.out.println("Scan: " + tok);
            } while (tok.tag != Tag.EOF);
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
