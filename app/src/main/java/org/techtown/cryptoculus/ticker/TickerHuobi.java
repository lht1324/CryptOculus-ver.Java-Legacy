package org.techtown.cryptoculus.ticker;

import java.io.Serializable;

public class TickerHuobi implements Serializable {
    public String symbol;
    public String open;
    public String high;
    public String low;
    public String close;
    public String amount;
    public String vol;
    public String count;
    public String bid;
    public String bidSize;
    public String ask;
    public String askSize;
}
