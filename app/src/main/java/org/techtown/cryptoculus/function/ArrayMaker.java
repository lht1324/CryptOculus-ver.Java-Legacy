package org.techtown.cryptoculus.function;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.techtown.cryptoculus.R;
import org.techtown.cryptoculus.coinInfo.CoinInfoBithumb;
import org.techtown.cryptoculus.coinInfo.CoinInfoCoinone;
import org.techtown.cryptoculus.coinInfo.CoinInfoHuobi;
import org.techtown.cryptoculus.currencys.CurrencysBithumb;
import org.techtown.cryptoculus.currencys.CurrencysCoinone;
import org.techtown.cryptoculus.currencys.CurrencysHuobi;
import org.techtown.cryptoculus.ticker.TickerHuobi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class ArrayMaker {
    private boolean restartApp;
    private boolean refreshedCoinone;
    private boolean refreshedBithumb;
    private boolean refreshedHuobi;
    private ArrayList<CoinInfoCoinone> coinInfosCoinone;
    private ArrayList<CoinInfoBithumb> coinInfosBithumb;
    private ArrayList<CoinInfoHuobi> coinInfosHuobi;
    private Context mContext;

    public ArrayMaker(boolean restartApp, boolean refreshedCoinone, boolean refreshedBithumb, boolean refreshedHuobi, ArrayList<CoinInfoCoinone> coinInfosCoinone, ArrayList<CoinInfoBithumb> coinInfosBithumb, ArrayList<CoinInfoHuobi> coinInfosHuobi, Context mContext) {
        this.restartApp = restartApp;
        this.refreshedCoinone = refreshedCoinone;
        this.refreshedBithumb = refreshedBithumb;
        this.refreshedHuobi = refreshedHuobi;
        this.coinInfosCoinone = coinInfosCoinone;
        this.coinInfosBithumb = coinInfosBithumb;
        this.coinInfosHuobi = coinInfosHuobi;
        this.mContext = mContext;
    }

    public ArrayList<CoinInfoCoinone> makeArrayCoinone(CurrencysCoinone currencyList) {
        ArrayList<CoinInfoCoinone> coinInfos = new ArrayList<CoinInfoCoinone>();
        SharedPreferences pref = mContext.getSharedPreferences("saveCoinone", MODE_PRIVATE);

        coinInfos.add(new CoinInfoCoinone(currencyList.btc, "비트코인", R.drawable.btc));
        coinInfos.add(new CoinInfoCoinone(currencyList.eth, "이더리움", R.drawable.eth));
        coinInfos.add(new CoinInfoCoinone(currencyList.xrp, "리플", R.drawable.xrp));
        coinInfos.add(new CoinInfoCoinone(currencyList.bch, "비트코인 캐시", R.drawable.bch));
        coinInfos.add(new CoinInfoCoinone(currencyList.eos, "이오스", R.drawable.eos));
        coinInfos.add(new CoinInfoCoinone(currencyList.bsv, "비트코인사토시비전", R.drawable.bsv));
        coinInfos.add(new CoinInfoCoinone(currencyList.etc, "이더리움 클래식", R.drawable.etc));
        coinInfos.add(new CoinInfoCoinone(currencyList.ltc, "라이트코인", R.drawable.ltc));
        coinInfos.add(new CoinInfoCoinone(currencyList.prom, "프로메테우스", R.drawable.prom));
        coinInfos.add(new CoinInfoCoinone(currencyList.atom, "코스모스아톰", R.drawable.atom));
        coinInfos.add(new CoinInfoCoinone(currencyList.xtz, "테조스", R.drawable.xtz));
        coinInfos.add(new CoinInfoCoinone(currencyList.psc, "폴스타코인", R.drawable.basic)); //
        coinInfos.add(new CoinInfoCoinone(currencyList.pci, "페이코인", R.drawable.pci));
        coinInfos.add(new CoinInfoCoinone(currencyList.trx, "트론", R.drawable.trx));
        coinInfos.add(new CoinInfoCoinone(currencyList.fleta, "플레타", R.drawable.fleta));
        coinInfos.add(new CoinInfoCoinone(currencyList.qtum, "퀀텀", R.drawable.qtum));
        coinInfos.add(new CoinInfoCoinone(currencyList.luna, "루나", R.drawable.luna));
        coinInfos.add(new CoinInfoCoinone(currencyList.knc, "카이버", R.drawable.knc));
        coinInfos.add(new CoinInfoCoinone(currencyList.kvi, "케이브이아이", R.drawable.basic)); //
        coinInfos.add(new CoinInfoCoinone(currencyList.egg, "네스트리", R.drawable.egg));
        coinInfos.add(new CoinInfoCoinone(currencyList.bna, "바나나톡", R.drawable.bna));
        coinInfos.add(new CoinInfoCoinone(currencyList.xlm, "스텔라루멘", R.drawable.xlm));
        coinInfos.add(new CoinInfoCoinone(currencyList.iota, "아이오타", R.drawable.iota));
        coinInfos.add(new CoinInfoCoinone(currencyList.xpn, "판테온X", R.drawable.xpn));
        coinInfos.add(new CoinInfoCoinone(currencyList.gas, "가스", R.drawable.gas));
        coinInfos.add(new CoinInfoCoinone(currencyList.ogn, "오리진 프로토콜", R.drawable.ogn));
        coinInfos.add(new CoinInfoCoinone(currencyList.ong, "온돌로지가스", R.drawable.ong));
        coinInfos.add(new CoinInfoCoinone(currencyList.chz, "칠리즈", R.drawable.chz));
        coinInfos.add(new CoinInfoCoinone(currencyList.data, "스트리머", R.drawable.data));
        coinInfos.add(new CoinInfoCoinone(currencyList.soc, "소다코인", R.drawable.soc));
        coinInfos.add(new CoinInfoCoinone(currencyList.zil, "질리카", R.drawable.zil));
        coinInfos.add(new CoinInfoCoinone(currencyList.bat, "베이직어텐션토큰", R.drawable.bat));
        coinInfos.add(new CoinInfoCoinone(currencyList.zrx, "제로엑스", R.drawable.zrx));
        coinInfos.add(new CoinInfoCoinone(currencyList.pxl, "픽션네트워크", R.drawable.pxl));
        coinInfos.add(new CoinInfoCoinone(currencyList.isr, "인슈리움", R.drawable.isr));
        coinInfos.add(new CoinInfoCoinone(currencyList.neo, "네오", R.drawable.neo));
        coinInfos.add(new CoinInfoCoinone(currencyList.redi, "레디", R.drawable.redi));
        coinInfos.add(new CoinInfoCoinone(currencyList.mbl, "무비블록", R.drawable.mbl));
        coinInfos.add(new CoinInfoCoinone(currencyList.omg, "오미세고", R.drawable.omg));
        coinInfos.add(new CoinInfoCoinone(currencyList.btt, "비트토렌트", R.drawable.btt));
        coinInfos.add(new CoinInfoCoinone(currencyList.drm, "두드림체인", R.drawable.basic)); //
        coinInfos.add(new CoinInfoCoinone(currencyList.spin, "스핀프로토콜", R.drawable.spin));
        coinInfos.add(new CoinInfoCoinone(currencyList.ankr, "앵커 네크워크", R.drawable.ankr));
        coinInfos.add(new CoinInfoCoinone(currencyList.stpt, "에스티피", R.drawable.stpt));
        coinInfos.add(new CoinInfoCoinone(currencyList.ont, "온톨로지", R.drawable.ont));
        coinInfos.add(new CoinInfoCoinone(currencyList.matic, "매틱 네트워크", R.drawable.matic));
        coinInfos.add(new CoinInfoCoinone(currencyList.temco, "템코", R.drawable.temco));
        coinInfos.add(new CoinInfoCoinone(currencyList.ftm, "팬텀", R.drawable.ftm));
        coinInfos.add(new CoinInfoCoinone(currencyList.iotx, "아이오텍스", R.drawable.iotx));
        coinInfos.add(new CoinInfoCoinone(currencyList.abl, "에어블록", R.drawable.abl));
        coinInfos.add(new CoinInfoCoinone(currencyList.pib, "피블", R.drawable.pib));
        coinInfos.add(new CoinInfoCoinone(currencyList.amo, "아모코인", R.drawable.amo));
        coinInfos.add(new CoinInfoCoinone(currencyList.troy, "트로이", R.drawable.troy));
        coinInfos.add(new CoinInfoCoinone(currencyList.clb, "클라우드브릭", R.drawable.clb));
        coinInfos.add(new CoinInfoCoinone(currencyList.orbs, "오브스", R.drawable.orbs));
        coinInfos.add(new CoinInfoCoinone(currencyList.baas, "바스아이디", R.drawable.baas));
        coinInfos.add(new CoinInfoCoinone(currencyList.hint, "힌트체인", R.drawable.hint));
        coinInfos.add(new CoinInfoCoinone(currencyList.hibs, "하이블럭스", R.drawable.hibs));
        coinInfos.add(new CoinInfoCoinone(currencyList.dad, "다드", R.drawable.dad));
        coinInfos.add(new CoinInfoCoinone(currencyList.uos, "울트라", R.drawable.uos));
        coinInfos.add(new CoinInfoCoinone(currencyList.btg, "비트코인 골드", R.drawable.btg));
        coinInfos.add(new CoinInfoCoinone(currencyList.arpa, "알파 체인", R.drawable.arpa));
        coinInfos.add(new CoinInfoCoinone(currencyList.axl, "엑시얼", R.drawable.axl));
        coinInfos.add(new CoinInfoCoinone(currencyList.hum, "휴먼스케이프", R.drawable.hum));
        coinInfos.add(new CoinInfoCoinone(currencyList.ksc, "케이스타라이브", R.drawable.ksc));
        coinInfos.add(new CoinInfoCoinone(currencyList.wiken, "위드", R.drawable.wiken));

        if (restartApp && !pref.getBoolean("isEmptyCoinone", true)) {
            int[] getPositions = new int[coinInfos.size()];
            ArrayList<CoinInfoCoinone> temp = new ArrayList<CoinInfoCoinone>();

            for (int i = 0; i < coinInfos.size(); i++) {
                getPositions[i] = pref.getInt(coinInfos.get(i).getCoinName() + "position", 0);
                temp.add(null);
            }

            for (int i = 0; i < coinInfos.size(); i++)
                temp.set(getPositions[i], coinInfos.get(i));

            coinInfos = temp;

            for (int i = 0; i < coinInfos.size(); i++)
                coinInfos.get(i).setCoinViewCheck(pref.getBoolean(coinInfos.get(i).getCoinName(), true));

            if (!isEmpty(coinInfosCoinone) && !isEmpty(coinInfosBithumb) && !isEmpty(coinInfosHuobi))
                restartApp = false;
        }

        if (refreshedCoinone) {
            for (int i = 0; i < coinInfos.size(); i++) {
                String temp1 = coinInfosCoinone.get(i).getCoinName(); // 이거 응용하면 될 거 같은데?
                // int로 position 배열을 만든 다음에 그걸로 position을 저장해서 이걸로 바꿔준다
                String temp2 = coinInfos.get(i).getCoinName();

                if (!(temp1.equals(temp2))) { // 순서가 바뀐 것
                    for (int j = 0; j < coinInfos.size(); j++) {
                        if (temp1.equals(coinInfos.get(j).getCoinName())) {
                            Collections.swap(coinInfos, i, j);
                        }
                    }
                }
            }
            for (int i = 0; i < coinInfos.size(); i++)
                coinInfos.get(i).setCoinViewCheck(coinInfosCoinone.get(i).getCoinViewCheck());
        }

        return coinInfos;
    }

    public ArrayList<CoinInfoBithumb> makeArrayBithumb(CurrencysBithumb currencyList) {
        ArrayList<CoinInfoBithumb> coinInfos = new ArrayList<CoinInfoBithumb>();
        SharedPreferences pref = mContext.getSharedPreferences("saveBithumb", MODE_PRIVATE);

        coinInfos.add(new CoinInfoBithumb(currencyList.XRP, "XRP / 리플", R.drawable.xrp));
        coinInfos.add(new CoinInfoBithumb(currencyList.BTC, "BTC / 비트코인", R.drawable.btc));
        coinInfos.add(new CoinInfoBithumb(currencyList.ETH, "ETH / 이더리움", R.drawable.eth));
        coinInfos.add(new CoinInfoBithumb(currencyList.XLM, "XLM / 스텔라루멘", R.drawable.xlm));
        coinInfos.add(new CoinInfoBithumb(currencyList.EOS, "EOS / 이오스", R.drawable.eos));
        coinInfos.add(new CoinInfoBithumb(currencyList.MBL, "MBL / 무비블록", R.drawable.mbl));
        coinInfos.add(new CoinInfoBithumb(currencyList.BNP, "BNP / 베네핏", R.drawable.bnp)); // 상장 폐지
        coinInfos.add(new CoinInfoBithumb(currencyList.BSV, "BSV / 비트코인에스브이", R.drawable.bsv));
        coinInfos.add(new CoinInfoBithumb(currencyList.XSR, "XSR / 젠서", R.drawable.xsr));
        coinInfos.add(new CoinInfoBithumb(currencyList.WICC, "WICC / 웨이키체인", R.drawable.wicc));
        coinInfos.add(new CoinInfoBithumb(currencyList.BCH, "BCH / 비트코인 캐시", R.drawable.bch));
        coinInfos.add(new CoinInfoBithumb(currencyList.MCO, "MCO / 크립토닷컴", R.drawable.mco));
        coinInfos.add(new CoinInfoBithumb(currencyList.REP, "REP / 어거", R.drawable.rep));
        coinInfos.add(new CoinInfoBithumb(currencyList.LTC, "LTC / 라이트코인", R.drawable.ltc));
        coinInfos.add(new CoinInfoBithumb(currencyList.TRX, "TRX / 트론", R.drawable.trx));
        coinInfos.add(new CoinInfoBithumb(currencyList.POWR, "POWR / 파워렛저", R.drawable.powr));
        coinInfos.add(new CoinInfoBithumb(currencyList.WAVES, "WAVES / 웨이브", R.drawable.waves));
        coinInfos.add(new CoinInfoBithumb(currencyList.AMO, "AMO / 아모코인", R.drawable.amo));
        coinInfos.add(new CoinInfoBithumb(currencyList.SOC, "SOC / 소다코인", R.drawable.soc));
        coinInfos.add(new CoinInfoBithumb(currencyList.AE, "AE / 애터니티", R.drawable.ae));
        coinInfos.add(new CoinInfoBithumb(currencyList.FLETA, "FLETA / 플레타", R.drawable.fleta));
        coinInfos.add(new CoinInfoBithumb(currencyList.APIX, "APIX / 아픽스", R.drawable.apix)); // 폐지
        coinInfos.add(new CoinInfoBithumb(currencyList.BCD, "BCD / 비트코인 다이아몬드", R.drawable.bcd));
        coinInfos.add(new CoinInfoBithumb(currencyList.STRAT, "STRAT / 스트라티스", R.drawable.strat));
        coinInfos.add(new CoinInfoBithumb(currencyList.ZEC, "ZEC / 제트캐시", R.drawable.zec));
        coinInfos.add(new CoinInfoBithumb(currencyList.ANKR, "ANKR / 앵커", R.drawable.ankr));
        coinInfos.add(new CoinInfoBithumb(currencyList.BAT, "BAT / 베이직어텐션토큰", R.drawable.bat));
        coinInfos.add(new CoinInfoBithumb(currencyList.GXC, "GXC / 지엑스체인", R.drawable.gxc));
        coinInfos.add(new CoinInfoBithumb(currencyList.WPX, "WPX / 더블유플러스", R.drawable.wpx));
        coinInfos.add(new CoinInfoBithumb(currencyList.ETC, "ETC / 이더리움 클래식", R.drawable.etc));
        coinInfos.add(new CoinInfoBithumb(currencyList.HDAC, "HDAC / 에이치닥", R.drawable.hdac));
        coinInfos.add(new CoinInfoBithumb(currencyList.THETA, "THETA / 쎄타토큰", R.drawable.theta));
        coinInfos.add(new CoinInfoBithumb(currencyList.SXP, "SXP / 스와이프", R.drawable.sxp));
        coinInfos.add(new CoinInfoBithumb(currencyList.ADA, "ADA / 에이다", R.drawable.ada));
        coinInfos.add(new CoinInfoBithumb(currencyList.DASH, "DASH / 대시", R.drawable.dash));
        coinInfos.add(new CoinInfoBithumb(currencyList.XMR, "XMR / 모네로", R.drawable.xmr));
        coinInfos.add(new CoinInfoBithumb(currencyList.LINK, "LINK / 체인링크", R.drawable.link));
        coinInfos.add(new CoinInfoBithumb(currencyList.WAXP, "WAXP / 왁스", R.drawable.waxp));
        coinInfos.add(new CoinInfoBithumb(currencyList.KNC, "KNC / 카이버 네트워크", R.drawable.knc));
        coinInfos.add(new CoinInfoBithumb(currencyList.VET, "VET / 비체인", R.drawable.vet));
        coinInfos.add(new CoinInfoBithumb(currencyList.BTT, "BTT / 비트토렌트", R.drawable.btt));
        coinInfos.add(new CoinInfoBithumb(currencyList.ZIL, "ZIL / 질리카", R.drawable.zil));
        coinInfos.add(new CoinInfoBithumb(currencyList.AOA, "AOA / 오로라", R.drawable.aoa));
        coinInfos.add(new CoinInfoBithumb(currencyList.ITC, "ITC / 아이오티체인", R.drawable.itc));
        coinInfos.add(new CoinInfoBithumb(currencyList.LUNA, "LUNA / 루나", R.drawable.luna));
        coinInfos.add(new CoinInfoBithumb(currencyList.QTUM, "QTUM / 퀀텀", R.drawable.qtum));
        coinInfos.add(new CoinInfoBithumb(currencyList.MTL, "MTL / 메탈", R.drawable.mtl));
        coinInfos.add(new CoinInfoBithumb(currencyList.ORBS, "ORBS / 오브스", R.drawable.orbs));
        coinInfos.add(new CoinInfoBithumb(currencyList.FAB, "FAB / 패블릭", R.drawable.fab));
        coinInfos.add(new CoinInfoBithumb(currencyList.BTG, "BTG / 비트코인 골드", R.drawable.btg));
        coinInfos.add(new CoinInfoBithumb(currencyList.TMTG, "TMTG / 더마이다스터치골드", R.drawable.tmtg));
        coinInfos.add(new CoinInfoBithumb(currencyList.FCT, "FCT / 피르마체인", R.drawable.fct));
        coinInfos.add(new CoinInfoBithumb(currencyList.FNB, "FNB / 에프앤비프로토콜", R.drawable.fnb));
        coinInfos.add(new CoinInfoBithumb(currencyList.ICX, "ICX / 아이콘", R.drawable.icx));
        coinInfos.add(new CoinInfoBithumb(currencyList.LRC, "LRC / 루프링", R.drawable.lrc));
        coinInfos.add(new CoinInfoBithumb(currencyList.LOOM, "LOOM / 룸네트워크", R.drawable.loom));
        coinInfos.add(new CoinInfoBithumb(currencyList.IPX, "IPX / 타키온프로토콜", R.drawable.ipx));
        coinInfos.add(new CoinInfoBithumb(currencyList.NPXS, "NPXS / 펀디엑스", R.drawable.npxs));
        coinInfos.add(new CoinInfoBithumb(currencyList.IOST, "IOST / 이오스트", R.drawable.iost));
        coinInfos.add(new CoinInfoBithumb(currencyList.FZZ, "FZZ / 피즈토큰", R.drawable.fzz));
        coinInfos.add(new CoinInfoBithumb(currencyList.DAD, "DAD / 다드", R.drawable.dad));
        coinInfos.add(new CoinInfoBithumb(currencyList.CON, "CON / 코넌", R.drawable.conun));
        coinInfos.add(new CoinInfoBithumb(currencyList.BZNT, "BZNT / 베잔트", R.drawable.bznt));
        coinInfos.add(new CoinInfoBithumb(currencyList.TRUE, "TRUE / 트루체인", R.drawable.truechain));
        coinInfos.add(new CoinInfoBithumb(currencyList.EM, "EM / 이마이너", R.drawable.em));
        coinInfos.add(new CoinInfoBithumb(currencyList.ENJ, "ENJ / 엔진코인", R.drawable.enj));
        coinInfos.add(new CoinInfoBithumb(currencyList.ELF, "ELF / 엘프", R.drawable.elf));
        coinInfos.add(new CoinInfoBithumb(currencyList.FX, "FX / 펑션엑스", R.drawable.fx));
        coinInfos.add(new CoinInfoBithumb(currencyList.WET, "WET / 위쇼토큰", R.drawable.wet));
        coinInfos.add(new CoinInfoBithumb(currencyList.PCM, "PCM / 프레시움", R.drawable.pcm));
        coinInfos.add(new CoinInfoBithumb(currencyList.DVP, "DVP / 디브이피", R.drawable.dvp));
        coinInfos.add(new CoinInfoBithumb(currencyList.SNT, "SNT / 스테이터스네트워크토큰", R.drawable.snt));
        coinInfos.add(new CoinInfoBithumb(currencyList.CTXC, "CTXC / 코르텍스", R.drawable.ctxc));
        coinInfos.add(new CoinInfoBithumb(currencyList.HYC, "HYC / 하이콘", R.drawable.hyc));
        coinInfos.add(new CoinInfoBithumb(currencyList.MIX, "MIX / 믹스마블", R.drawable.mix));
        coinInfos.add(new CoinInfoBithumb(currencyList.MXC, "MXC / 머신익스체인지코인", R.drawable.mxc));
        coinInfos.add(new CoinInfoBithumb(currencyList.CRO, "CRO / 크립토닷컴체인", R.drawable.cro));
        coinInfos.add(new CoinInfoBithumb(currencyList.WOM, "WOM / 왐토큰", R.drawable.wom));
        coinInfos.add(new CoinInfoBithumb(currencyList.PIVX, "PIVX / 피벡스", R.drawable.pivx));
        coinInfos.add(new CoinInfoBithumb(currencyList.INS, "INS / 아이앤에스", R.drawable.ins));
        coinInfos.add(new CoinInfoBithumb(currencyList.OMG, "OMG / 오미세고", R.drawable.omg));
        coinInfos.add(new CoinInfoBithumb(currencyList.QKC, "QKC / 쿼크체인", R.drawable.qkc));
        coinInfos.add(new CoinInfoBithumb(currencyList.OGO, "OGO / 오리고", R.drawable.ogo));
        coinInfos.add(new CoinInfoBithumb(currencyList.CHR, "CHR / 크로미아", R.drawable.chr));
        coinInfos.add(new CoinInfoBithumb(currencyList.DAC, "DAC / 다빈치", R.drawable.dac));
        coinInfos.add(new CoinInfoBithumb(currencyList.STEEM, "STEEM / 스팀", R.drawable.steem));
        coinInfos.add(new CoinInfoBithumb(currencyList.VALOR, "VALOR / 밸러토큰", R.drawable.valor));
        coinInfos.add(new CoinInfoBithumb(currencyList.LBA, "LBA / 크레드", R.drawable.lba));
        coinInfos.add(new CoinInfoBithumb(currencyList.TRV, "TRV / 트러스트버스", R.drawable.trv));
        coinInfos.add(new CoinInfoBithumb(currencyList.XVG, "XVG / 버지", R.drawable.xvg));
        coinInfos.add(new CoinInfoBithumb(currencyList.GNT, "GNT / 골렘", R.drawable.gnt));
        coinInfos.add(new CoinInfoBithumb(currencyList.LAMB, "LAMB / 람다", R.drawable.lamb));
        coinInfos.add(new CoinInfoBithumb(currencyList.RNT, "RNT / 원루트 네트워크", R.drawable.rnt));
        coinInfos.add(new CoinInfoBithumb(currencyList.ZRX, "ZRX / 제로엑스", R.drawable.zrx));
        coinInfos.add(new CoinInfoBithumb(currencyList.WTC, "WTC / 월튼체인", R.drawable.wtc));
        coinInfos.add(new CoinInfoBithumb(currencyList.XEM, "XEM / 넴", R.drawable.xem));
        coinInfos.add(new CoinInfoBithumb(currencyList.BHP, "BHP / 비에이치피", R.drawable.bhp));

        if (restartApp && !pref.getBoolean("isEmptyBithumb", true)) {
            int[] getPositions = new int[coinInfos.size()];
            ArrayList<CoinInfoBithumb> temp = new ArrayList<CoinInfoBithumb>();

            for (int i = 0; i < coinInfos.size(); i++) {
                getPositions[i] = pref.getInt(coinInfos.get(i).getCoinName() + "position", 0);
                temp.add(null);
            }

            for (int i = 0; i < coinInfos.size(); i++)
                temp.set(getPositions[i], coinInfos.get(i));

            coinInfos = temp;

            for (int i = 0; i < coinInfos.size(); i++)
                coinInfos.get(i).setCoinViewCheck(pref.getBoolean(coinInfos.get(i).getCoinName(), true));

            if (!isEmpty(coinInfosCoinone) && !isEmpty(coinInfosBithumb) && !isEmpty(coinInfosHuobi))
                restartApp = false;
        }

        if (refreshedBithumb) {
            for (int i = 0; i < coinInfos.size(); i++) {
                String temp1 = coinInfosBithumb.get(i).getCoinName();
                String temp2 = coinInfos.get(i).getCoinName();

                if (!(temp1.equals(temp2))) { // 순서가 바뀐 것
                    for (int j = i; j < coinInfos.size(); j++) {
                        if (temp1.equals(coinInfos.get(j).getCoinName()))
                            Collections.swap(coinInfos, i, j);
                    }
                }
            }

            for (int i = 0; i < coinInfos.size(); i++)
                coinInfos.get(i).setCoinViewCheck(coinInfosBithumb.get(i).getCoinViewCheck());
        }

        return coinInfos;
    }

    public ArrayList<CoinInfoHuobi> makeArrayHuobi(ArrayList<TickerHuobi> tickersHuobi) {
        ArrayList<CoinInfoHuobi> coinInfos = new ArrayList<CoinInfoHuobi>();
        CurrencysHuobi currencysHuobi = new CurrencysHuobi();
        SharedPreferences pref = mContext.getSharedPreferences("saveHuobi", MODE_PRIVATE);

        for (int i = 0; i < tickersHuobi.size(); i++) {
            TickerHuobi ticker = tickersHuobi.get(i);

            if (ticker.symbol.contains("grs"))
                currencysHuobi.grs = ticker;
            if (ticker.symbol.contains("xrp"))
                currencysHuobi.xrp = ticker;
            if (ticker.symbol.contains("eth"))
                currencysHuobi.eth = ticker;
            if (ticker.symbol.contains("mvl"))
                currencysHuobi.mvl = ticker;
            if (ticker.symbol.contains("ada"))
                currencysHuobi.ada = ticker;
            if (ticker.symbol.contains("fit"))
                currencysHuobi.fit = ticker;
            if (ticker.symbol.contains("bsv"))
                currencysHuobi.bsv = ticker;
            if (ticker.symbol.contains("btm"))
                currencysHuobi.btm = ticker;
            if (ticker.symbol.contains("ht"))
                currencysHuobi.ht = ticker;
            if (ticker.symbol.contains("usdt"))
                currencysHuobi.usdt = ticker;
            if (ticker.symbol.contains("iost"))
                currencysHuobi.iost = ticker;
            if (ticker.symbol.contains("ont"))
                currencysHuobi.ont = ticker;
            if (ticker.symbol.contains("pci"))
                currencysHuobi.pci = ticker;
            if (ticker.symbol.contains("solve"))
                currencysHuobi.solve = ticker;
            if (ticker.symbol.contains("uip"))
                currencysHuobi.uip = ticker;
            if (ticker.symbol.contains("xlm"))
                currencysHuobi.xlm = ticker;
            if (ticker.symbol.contains("ltc"))
                currencysHuobi.ltc = ticker;
            if (ticker.symbol.contains("eos"))
                currencysHuobi.eos = ticker;
            if (ticker.symbol.contains("skm"))
                currencysHuobi.skm = ticker;
            if (ticker.symbol.contains("btc"))
                currencysHuobi.btc = ticker;
            if (ticker.symbol.contains("bch"))
                currencysHuobi.bch = ticker;
            if (ticker.symbol.contains("trx"))
                currencysHuobi.trx = ticker;
        }

        coinInfos.add(new CoinInfoHuobi(currencysHuobi.btc, "BTC / 비트코인", R.drawable.btc));
        coinInfos.add(new CoinInfoHuobi(currencysHuobi.usdt, "USDT / 테더", R.drawable.usdt));
        coinInfos.add(new CoinInfoHuobi(currencysHuobi.eth, "ETH / 이더리움", R.drawable.eth));
        coinInfos.add(new CoinInfoHuobi(currencysHuobi.eos, "EOS / 이오스", R.drawable.eos));
        coinInfos.add(new CoinInfoHuobi(currencysHuobi.xrp, "XRP / 리플", R.drawable.xrp));
        coinInfos.add(new CoinInfoHuobi(currencysHuobi.pci, "PCI / 페이코인", R.drawable.pci));
        coinInfos.add(new CoinInfoHuobi(currencysHuobi.bch, "BCH / 비트코인캐시", R.drawable.bch));
        coinInfos.add(new CoinInfoHuobi(currencysHuobi.bsv, "BSV / 비트코인SV", R.drawable.bsv));
        coinInfos.add(new CoinInfoHuobi(currencysHuobi.ada, "ADA / 카르다노", R.drawable.ada));
        coinInfos.add(new CoinInfoHuobi(currencysHuobi.ht, "HT / 후오비토큰", R.drawable.ht));
        coinInfos.add(new CoinInfoHuobi(currencysHuobi.trx, "TRX / 트론", R.drawable.trx));
        coinInfos.add(new CoinInfoHuobi(currencysHuobi.xlm, "XLM / 스텔라루멘", R.drawable.xlm));
        coinInfos.add(new CoinInfoHuobi(currencysHuobi.ltc, "LTC / 라이트코인", R.drawable.ltc));
        coinInfos.add(new CoinInfoHuobi(currencysHuobi.iost, "IOST / 아이오에스토큰", R.drawable.iost));
        coinInfos.add(new CoinInfoHuobi(currencysHuobi.ont, "ONT / 온톨로지", R.drawable.ont));
        coinInfos.add(new CoinInfoHuobi(currencysHuobi.grs, "GRS / 그로스톨코인", R.drawable.grs));
        coinInfos.add(new CoinInfoHuobi(currencysHuobi.btm, "BTM / 바이텀", R.drawable.btm));
        coinInfos.add(new CoinInfoHuobi(currencysHuobi.solve, "SOLVE / 솔브케어", R.drawable.solve));
        coinInfos.add(new CoinInfoHuobi(currencysHuobi.uip, "UIP / 언리미티드IP", R.drawable.uip));
        coinInfos.add(new CoinInfoHuobi(currencysHuobi.mvl, "MVL / 엠블", R.drawable.mvl));
        coinInfos.add(new CoinInfoHuobi(currencysHuobi.fit, "FIT / FIT 토큰", R.drawable.fit));
        coinInfos.add(new CoinInfoHuobi(currencysHuobi.skm, "SKM / 스크럼블 네트워크", R.drawable.skm));

        for (int i = 0; i < coinInfos.size(); i++) { // Huobi API 독자 규격에서 오는 에러 제거 (E7 = * 10000000)
            TickerHuobi ticker = coinInfos.get(i).getCoinData();

            if (ticker.open.contains("E7"))
                ticker.open = Double.toString((Double.parseDouble(ticker.open.replace("E7", "")) * 10000000));
            if (ticker.close.contains("E7"))
                ticker.close = Double.toString((Double.parseDouble(ticker.close.replace("E7", "")) * 10000000));
            if (ticker.high.contains("E7"))
                ticker.high = Double.toString((Double.parseDouble(ticker.high.replace("E7", "")) * 10000000));
            if (ticker.low.contains("E7"))
                ticker.low = Double.toString((Double.parseDouble(ticker.low.replace("E7", "")) * 10000000));

            coinInfos.get(i).setCoinData(ticker);
        }

        if (restartApp && !pref.getBoolean("isEmptyHuobi", true)) {
            int[] getPositions = new int[coinInfos.size()];
            ArrayList<CoinInfoHuobi> temp = new ArrayList<CoinInfoHuobi>();

            for (int i = 0; i < coinInfos.size(); i++) {
                getPositions[i] = pref.getInt(coinInfos.get(i).getCoinName() + "position", 0);
                println("getPositions[" + i + "] = " + getPositions[i]);
                temp.add(null);
            }

            for (int i = 0; i < coinInfos.size(); i++)
                temp.set(getPositions[i], coinInfos.get(i));

            coinInfos = temp;

            for (int i = 0; i < coinInfos.size(); i++)
                coinInfos.get(i).setCoinViewCheck(pref.getBoolean(coinInfos.get(i).getCoinName(), true)); // 에러 위치

            if (!isEmpty(coinInfosCoinone) && !isEmpty(coinInfosBithumb) && !isEmpty(coinInfosHuobi))
                restartApp = false;
        }

        if (refreshedHuobi) {
            for (int i = 0; i < coinInfos.size(); i++) {
                String temp1 = coinInfosHuobi.get(i).getCoinName();
                String temp2 = coinInfos.get(i).getCoinName();

                if (!(temp1.equals(temp2))) { // 순서가 바뀐 것
                    for (int j = i; j < coinInfos.size(); j++) {
                        if (temp1.equals(coinInfos.get(j).getCoinName()))
                            Collections.swap(coinInfos, i, j);
                    }
                }
            }

            for (int i = 0; i < coinInfos.size(); i++)
                coinInfos.get(i).setCoinViewCheck(coinInfosHuobi.get(i).getCoinViewCheck());
        }

        return coinInfos;
    }

    public static boolean isEmpty(Object object) {
        if (object == null)
            return true;

        if ((object instanceof String) && (((String) object).trim().length() == 0))
            return true;

        if (object instanceof Map)
            return ((Map<?, ?>) object).isEmpty();

        if (object instanceof List)
            return ((List<?>) object).isEmpty();

        if (object instanceof Object[]) {
            return (((Object[]) object).length == 0);
        }
        return false;
    }

    public void println(String data) {
        Log.d("MainActivity", data);
    }
}
