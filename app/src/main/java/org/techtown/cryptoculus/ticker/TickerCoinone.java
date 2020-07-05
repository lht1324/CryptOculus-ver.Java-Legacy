package org.techtown.cryptoculus.ticker;

import java.io.Serializable;
import java.util.ArrayList;

public class TickerCoinone implements Serializable {
    public String yesterday_last; // 요청 24시간 전 가격 (double)
    public String yesterday_low; // 24 ~ 48 시간 내 최저가 (double)
    public String high; // 24시간 내 최고가 (double)
    public String currency; // 통화 (String)
    public String yesterday_high; // 24 ~ 48시간 내 최고가 (double)
    public String volume; // 24시간 내 거래량 (double)
    public String yesterday_first; // 24 ~ 48시간 내 시가 (double)
    public String last; // 요청 시 가격 (double)
    public String yesterday_volume; // 24 ~ 48시간 내 거래량 (double)
    public String low; // 24시간 내 최저가 (double)
    public String first; // 시가 00시 기준 시가 (double)
}
