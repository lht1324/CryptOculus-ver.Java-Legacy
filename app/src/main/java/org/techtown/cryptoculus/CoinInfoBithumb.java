package org.techtown.cryptoculus;

import java.io.Serializable;

public class CoinInfoBithumb implements Serializable {
    private TickerBithumb coinData;
    private String coinName;
    private int coinImageIndex;
    private boolean coinViewCheck = true;

    public CoinInfoBithumb(TickerBithumb coinData, String coinName, int coinImageIndex) {
        this.coinData = coinData;
        this.coinName = coinName;
        this.coinImageIndex = coinImageIndex;
    }

    public void setCoinData(TickerBithumb coinData) {
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

    public TickerBithumb getCoinData() {
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
