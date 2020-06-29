package org.techtown.cryptoculus;

import java.io.Serializable;

public class TickerBithumb implements Serializable {
    String opening_price; // 시가 00시 기준, 12시 가격
    String closing_price; // 종가 00시 기준, 현재 가격
    String min_price; // 저가 00시 기준
    String max_price; // 고가 00시 기준
    String units_traded; // 거래량 00시 기준
    String acc_trade_value; // 거래금액 00시 기준
    String prev_closing_price; // 전일종가
    String units_traded_24H; // 최근 24시간 거래량
    String acc_trade_value_24H; // 최근 24시간 거래금액
    String fluctate_24H; // 최근 24시간 변동가
    String fluctate_rate_24H; // 최근 24시간 변동률
}
