package ch02;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RequestIDGenerator implements  CircularSeqGenerator {
    private final static RequestIDGenerator INSTANCE = new RequestIDGenerator();
    private final static short SEQ_UPPER_LIMIT = 999;
    private short sequence = -1;

    private RequestIDGenerator(){

    }

    public short nextSequence(){
        if(sequence >= SEQ_UPPER_LIMIT){
            sequence = 0;
        } else{
            sequence++;
        }
        return sequence;
    }

    public String nextID(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
        String timestamp = sdf.format(new Date());
        DecimalFormat df = new DecimalFormat("000");

        short sequneceNo = nextSequence();
        return "0049" + timestamp + df.format(sequneceNo);
    }

    public static RequestIDGenerator getInstance(){
        return INSTANCE;
    }
}
