public class NumberTok extends Token {
	// completare
    public long value;

    public NumberTok(long val) { 
        //siccome il number token è caratterizzato dal tag 256 + un valore numerico
        super(Tag.NUM);
        value = val;
    }

    public String toString() { return "<" + tag + ", " + value + ">"; }
}