package org.techtown.cryptoculus.ticker;

import java.io.Serializable;

public class TickerBithumb implements Serializable {
    public String opening_price; // 시가 00시 기준, 12시 가격
    public String closing_price; // 종가 00시 기준, 현재 가격
    public String min_price; // 저가 00시 기준
    public String max_price; // 고가 00시 기준
    public String units_traded; // 거래량 00시 기준
    public String acc_trade_value; // 거래금액 00시 기준
    public String prev_closing_price; // 전일종가
    public String units_traded_24H; // 최근 24시간 거래량
    public String acc_trade_value_24H; // 최근 24시간 거래금액
    public String fluctate_24H; // 최근 24시간 변동가
    public String fluctate_rate_24H; // 최근 24시간 변동률
}
