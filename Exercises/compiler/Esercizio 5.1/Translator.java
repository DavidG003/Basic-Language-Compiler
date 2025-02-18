import java.io.*;

public class Translator {
    private Lexer lex;
    private BufferedReader pbr;
    private Token look;
    
    SymbolTable st = new SymbolTable();
    CodeGenerator code = new CodeGenerator();
    int count=0;

    public Translator(Lexer l, BufferedReader br) {
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

    public void prog() {
        switch (look.tag) {
            case Tag.ASSIGN:
            case Tag.PRINT:
            case Tag.READ:
            case Tag.FOR:
            case Tag.IF:
            case '{':
                int lnext_prog = code.newLabel();
                statlist();
                code.emitLabel(lnext_prog);
                match(Tag.EOF);
                try {
                    code.toJasmin();
                }
                catch(java.io.IOException e) {
                    System.err.println("IO error\n");
                };
                break;
            default:
                error("Errore in prog");
                break;
        }
    }

    public void statlist() {
        switch (look.tag) {
            case Tag.ASSIGN:
            case Tag.PRINT:
            case Tag.READ:
            case Tag.FOR:
            case Tag.IF:
            case '{':
                stat();
                statlistp();
                break;
            default:
                error("Errore in statlist");
                break;
        }
    }

    public void statlistp() {
        switch (look.tag) {
            case ';':
                match(';');
                stat();
                statlistp();
                break;
            case Tag.EOF:
            case '}':
                break;
            default:
                error("Errore in statlist");
                break;
        }
    }

    public void stat() {
        switch (look.tag) {
            case Tag.ASSIGN:
                match(Tag.ASSIGN);
                assignlist();
                break;
            case Tag.PRINT:
                
                match(Tag.PRINT);
                match('(');
                exprlist("print");
                match(')');
                break;
            case Tag.READ:
                match(Tag.READ);
                match('(');
                idlist("read");
                match(')');
                break;
            case Tag.FOR:
                match(Tag.FOR);
                match('(');
                statfor();
                break;
            case Tag.IF:
                
                int if_continue = code.newLabel();
                int if_jump = code.newLabel();
                match(Tag.IF);
                match('(');
                bexpr(if_continue);
                match(')');
                
                code.emit(OpCode.GOto, if_jump);
                code.emitLabel(if_continue); 
                stat(); 

                statif(if_jump);
                break;
            case '{':
                match('{');
                statlist();
                match('}');
                break;
            default:
                error("Errore in stat");
                break;
        }
    }

    
    public void statfor() {

        int start_for = code.newLabel();       
        int jump = code.newLabel();           
        int end_for = code.newLabel();       

        switch (look.tag) {
            case Tag.ID:
                
                int id_addr = st.lookupAddress(((Word)look).lexeme);
                if (id_addr==-1) {
                    id_addr = count;
                    st.insert(((Word)look).lexeme,count++);
                }
                match(Tag.ID);
                match(Tag.INIT);
                expr();
                
                code.emit(OpCode.istore, id_addr);
                match(';');

                code.emitLabel(start_for); 
                bexpr(jump);
                match(')');
                match(Tag.DO);
                
                code.emit(OpCode.GOto, end_for);
                
                code.emitLabel(jump);
                stat();
                
                code.emit(OpCode.GOto, start_for);
                code.emitLabel(end_for);
                break;

            case Tag.RELOP:
                
                code.emitLabel(start_for); 
                bexpr(jump);
                match(')');
                match(Tag.DO);
                code.emit(OpCode.GOto, end_for);
                code.emitLabel(jump);
                stat();
                code.emit(OpCode.GOto, start_for);
                code.emitLabel(end_for); 
                break;
            default:
                error("Errore in statfor");
                break;
        }
    }

    public void statif(int if_jump) {
        switch (look.tag) {
            case Tag.ELSE:
                int end_else = code.newLabel();
                
                
                code.emit(OpCode.GOto, end_else);
                code.emitLabel(if_jump); 
                match(Tag.ELSE);
                stat();
                match(Tag.END);
                code.emitLabel(end_else);
                break;
            case Tag.END:
                match(Tag.END);
                
                code.emitLabel(if_jump);
                break;
            default:
                error("Errore in statif");
                break;
        }
    }

    public void assignlist() {
        switch (look.tag) {
            case '[':
                match('[');
                expr();
                
                match(Tag.TO);
                idlist("asg_exp");
                match(']');
                assignlistp();
                break;
            default:
                error("Errore in assignlist");
                break;
        }
    }

    public void assignlistp() {
        switch (look.tag) {
            case '[':
                match('[');
                expr();
                match(Tag.TO);
                idlist("asg_exp"); 
                match(']');
                assignlistp();
                break;
            case ';':
            case Tag.ELSE:
            case Tag.END:
            case Tag.EOF:
            case '}':
                break;
            default:
                error("Errore in assignlistp");
                break;
        }
    }

    
    
    public void idlist(String opt) {
        switch (look.tag) {
            case Tag.ID:
        	    int id_addr = st.lookupAddress(((Word)look).lexeme);
                if (id_addr==-1) {
                    id_addr = count;
                    st.insert(((Word)look).lexeme,count++);
                }
                match(Tag.ID);
                
                
                
                
                
                if(opt == "asg_exp"){
                    
                    code.emit(OpCode.dup);
                    code.emit(OpCode.istore, id_addr); 

                }
                else if(opt == "read"){
                    code.emit(OpCode.invokestatic, 0);
                    code.emit(OpCode.istore, id_addr); 
                }
                idlistp(opt);
                break;
            default:
                error("Errore in idlist");
                break;
        }
    }

    public void idlistp(String opt){
        switch (look.tag) {
            case ',':
                match(',');
                int id_addr = st.lookupAddress(((Word)look).lexeme);
                if (id_addr==-1) {
                    id_addr = count;
                    st.insert(((Word)look).lexeme,count++);
                }
                match(Tag.ID);

                if(opt == "asg_exp"){
                    
                    code.emit(OpCode.dup);
                    code.emit(OpCode.istore, id_addr); 

                }
                else if(opt == "read"){
                    code.emit(OpCode.invokestatic, 0);
                    code.emit(OpCode.istore, id_addr); 
                }

                idlistp(opt);
                break;
            case ')':
            case ']':
                
                if(opt == "asg_exp"){
                    code.emit(OpCode.pop);
                }
                break;
            default:
                error("Errore in idlistp");
                break;
        }    
   }

   
    public void bexpr(int jump_label){
        switch (look.tag) {
            case Tag.RELOP:
                String cond = (((Word)look).lexeme);
                match(Tag.RELOP);
                expr();
                expr();
                
                switch (cond) {
                    case "<":
                        code.emit(OpCode.if_icmplt, jump_label);
                        break;
                    case ">":
                        code.emit(OpCode.if_icmpgt, jump_label);
                        break;
                    case "==":
                        code.emit(OpCode.if_icmpeq, jump_label);
                        break;
                    case "<=":
                        code.emit(OpCode.if_icmple, jump_label);
                        break;
                    case ">=":
                        code.emit(OpCode.if_icmpge, jump_label);
                        break;
                    case "<>":
                        code.emit(OpCode.if_icmpne, jump_label);
                        break;
                }
                break;
            default:
                error("Errore in bexpr");
                break;
        }    
   }

    public void expr(){
        switch (look.tag) {
            case '+':
                match('+');
                match('(');
                exprlist("sum");
                match(')');
                break;
            case '-':
                match('-');
                expr();
                expr();
                code.emit(OpCode.isub);
                break;
            case '*':
                match('*');
                match('(');
                exprlist("mult");
                match(')');
                break;
            case '/':
                match('/');
                expr();
                expr();
                code.emit(OpCode.idiv);
                break;
            case Tag.NUM:
                
                code.emit(OpCode.ldc, ((NumberTok)look).value);
                match(Tag.NUM);
                break;
            case Tag.ID:
                int id_addr = st.lookupAddress(((Word)look).lexeme);
                if (id_addr==-1) {
                    
                    error("Translation failed: Accessing value from uninitialized register");
                }
                
                code.emit(OpCode.iload, id_addr);
                match(Tag.ID);
                break;
            default:
                error("Errore in expr");
                break;
        }    
   }

       public void exprlist(String opt){
         switch (look.tag) {
            case '+':
            case '-':
            case '*':
            case '/':
            case Tag.NUM:
            case Tag.ID:
                expr(); 
                if(opt == "print"){
                    
                    code.emit(OpCode.invokestatic, 1);
                }
                exprlistp(opt); 
                break;
            default:
                error("Errore in exprlist");
                break;
        }   
   }

   
       public void exprlistp(String opt){
         switch (look.tag) {
            case ',':
                match(',');
                expr();
                
                
                if(opt == "sum"){
                    code.emit(OpCode.iadd); 
                }
                else if(opt == "mult"){
                    code.emit(OpCode.imul);
                }
                else if(opt == "print"){
                    code.emit(OpCode.invokestatic, 1);
                }
                exprlistp(opt);
                break;
            case ')':
                break;
            default:
                error("Errore in exprlistp");
                break;
        }   
   }
   


public static void main(String[] args) {
    Lexer lex = new Lexer();
    String path = ".\\test.txt"; 
                                                                                            
    try {
        BufferedReader br = new BufferedReader(new FileReader(path));
        Translator translator = new Translator(lex, br);
        translator.prog();
        System.out.println("Input OK");
        br.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
}
}