import java.io.*;

public class Parser {
    private Lexer lex;
    private BufferedReader pbr;
    private Token look;

    public Parser(Lexer l, BufferedReader br) {
        lex = l;
        pbr = br;
        move();
    }

    void move() {
        look = lex.lexical_scan(pbr);
        System.out.println("token = " + look);
    }

    void error(String s) {
        throw new Error("near line " + lex.line + ": " + s);
    }

    void match(int t) {
        if (look.tag == t) {
            if (look.tag != Tag.EOF)
                move();
        } else
            error("syntax error");
    }

    public void start() {
        // ... completare ...
        if (look.tag == '(' || look.tag == Tag.NUM) {
            expr();
            match(Tag.EOF);
        } else
            error("error in start");
        // ... completare ...
    }

    private void expr() {
        // ... completare ...
        if (look.tag == '(' || look.tag == Tag.NUM) {
            term();
            exprp();
        } else
            error("error in expr");
        // ... completare ...

    }

    private void exprp() {
        switch (look.tag) {
            case '+':
                // ... completare ...
                match('+');
                term();
                exprp();
                break;
            case '-':
                match('-');
                term();
                exprp();
                break;
            case ')':
            case Tag.EOF:
                break;
            default:
                error("error in exprp");
                break;
        }
    }

    private void term() {
        // ... completare ...
        if (look.tag == '(' || look.tag == Tag.NUM) {
            fact();
            termp();
        } else
            error("error in term");

    }

    private void termp() {
        // ... completare ...
        switch (look.tag) {
            case '*':
                // ... completare ...
                match('*');
                fact();
                termp();
                break;
            case '/':
                match('/');
                fact();
                termp();
                break;
            case '+':
            case '-':
            case ')':
            case Tag.EOF:
                break;
            default:
                error("error in termp");
                break;
        }
    }

    private void fact() {
        // ... completare ...
        switch (look.tag) {
            case '(':
                // ... completare ...
                match('(');
                expr();
                match(')');
                break;
            case Tag.NUM:
                match(Tag.NUM);
                break;
            default:
                error("Error in fact");
                break;
        }
    }

    public static void main(String[] args) {
        Lexer lex = new Lexer();
        String path = "test.txt"; // il percorso del file da leggere
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Parser parser = new Parser(lex, br);
            parser.start();
            System.out.println("Input OK");
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}