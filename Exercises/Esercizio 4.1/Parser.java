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
        int expr_val;
        if (look.tag == '(' || look.tag == Tag.NUM) {
            expr_val = expr();
            match(Tag.EOF);
            System.out.println(expr_val);
        } else
            error("error in start");
        // ... completare ...
    }

    private int expr() {
        // ... completare ...
        int term_val, exprp_val;
        if (look.tag == '(' || look.tag == Tag.NUM) {
            term_val = term();
            exprp_val = exprp(term_val);
            return exprp_val;
        } else{
            error("error in expr");
            return -1;
        }
        // ... completare ...

    }

    private int exprp(int exprp_i){
        int term_val, exprp_val;
        switch (look.tag) {
            case '+':
                // ... completare ...
                match('+');
                term_val = term();
                exprp_val = exprp(exprp_i + term_val);
                return exprp_val;
            case '-':
                match('-');
                term_val = term();
                exprp_val = exprp(exprp_i - term_val);
                return exprp_val;
            case ')':
            case Tag.EOF:
                return exprp_i;
            default:
                error("error in exprp");
                return -1;
        }
    }

    private int term() {
        // ... completare ...
        int fact_val, termp_val;
        if (look.tag == '(' || look.tag == Tag.NUM) {
            fact_val = fact();
            termp_val = termp(fact_val);
            return termp_val;
        } else
            error("error in term");
            return -1;

    }

    private int termp(int termp_i) {
        // ... completare ...
        int fact_val, termp_val;
        switch (look.tag) {
            case '*':
                // ... completare ...
                match('*');
                fact_val = fact();
                termp_val = termp(termp_i * fact_val);
                return termp_val;
            case '/':
                match('/');
                fact_val = fact();
                termp_val = termp(termp_i / fact_val);
                return termp_val;
            case '+':
            case '-':
            case ')':
            case Tag.EOF:
                return termp_i;
            default:
                error("error in termp");
                return -1;
        }
    }

    private int fact() {
        // ... completare ...
        int fact_val;
        switch (look.tag) {
            case '(':
                // ... completare ...
                match('(');
                fact_val = expr();
                match(')');
                return fact_val;
            case Tag.NUM:
                fact_val = ((NumberTok)look).value;
                match(Tag.NUM);
                return fact_val;
            default:
                error("Error in fact");
                return -1;
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