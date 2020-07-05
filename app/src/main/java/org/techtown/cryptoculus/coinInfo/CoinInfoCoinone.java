package org.techtown.cryptoculus.coinInfo;

import org.techtown.cryptoculus.ticker.TickerCoinone;

import java.io.Serializable;

public class CoinInfoCoinone implements Serializable {
    private TickerCoinone coinData;
    private String coinName;
    private int coinImageIndex;
    private boolean coinViewCheck = true;

    public CoinInfoCoinone(TickerCoinone coinData, String coinName, int coinImageIndex) {
        this.coinData = coinData;
        this.coinName = coinName;
        this.coinImageIndex = coinImageIndex;
    }

    public void setCoinData(TickerCoinone coinData) {
        this.coinData = coinData;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public void setCoinImageIndex(int coinImageIndex) {
        this.coinImageIndex = coinImageIndex;
    }

    public void setCoinViewCheck(boolean coinViewCheck) {
        this.coinViewCheck = coinViewCheck;
    }

    public TickerCoinone getCoinData() {
        return coinData;
    }

    public String getCoinName() {
        return coinName;
    }

    public int getCoinImageIndex() {
        return coinImageIndex;
    }

    public boolean getCoinViewCheck() {
        return coinViewCheck;
    }
}
