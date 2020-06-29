package org.techtown.cryptoculus;

import java.io.Serializable;
import java.util.ArrayList;

public class TickerCoinone implements Serializable {
    String yesterday_last; // 요청 24시간 전 가격 (double)
    String yesterday_low; // 24 ~ 48 시간 내 최저가 (double)
    String high; // 24시간 내 최고가 (double)
    String currency; // 통화 (String)
    String yesterday_high; // 24 ~ 48시간 내 최고가 (double)
    String volume; // Coin volume of completed orders in 24 hours (double)
    String yesterday_first; // 24 ~ 48시간 내 시가 (double)
    String last; // 요청 시 가격 (double)
    String yesterday_volume; // Coin volume of completed orders during for 24 ~ 48 hours (double)
    String low; // 24시간 내 최저가 (double)
    String first; // 시가 00시 기준 시가 (double)
}
