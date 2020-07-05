package org.techtown.cryptoculus.coinInfo;

import org.techtown.cryptoculus.ticker.TickerHuobi;
import java.io.Serializable;

public class CoinInfoHuobi implements Serializable {
        private TickerHuobi coinData;
        private String coinName;
        private int coinImageIndex;
        private boolean coinViewCheck = true;

    public CoinInfoHuobi(TickerHuobi coinData, String coinName, int coinImageIndex) {
        this.coinData = coinData;
        this.coinName = coinName;
        this.coinImageIndex = coinImageIndex;
    }

        public void setCoinData(TickerHuobi coinData) {
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

        public TickerHuobi getCoinData() {
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
