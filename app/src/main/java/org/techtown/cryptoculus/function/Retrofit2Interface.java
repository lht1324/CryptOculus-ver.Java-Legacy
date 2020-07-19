package org.techtown.cryptoculus.function;

import org.techtown.cryptoculus.currencys.CurrencysCoinone;
import org.techtown.cryptoculus.ticker.TickerFormatBithumb;
import org.techtown.cryptoculus.ticker.TickerFormatHuobi;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Retrofit2Interface {
    @GET("ticker?")
    Call<Object> getCoinone(@Query("currency") String currency);
    @GET("public/ticker/ALL_KRW")
    Call<Object> getBithumb();
    @GET("market/tickers")
    Call<Object> getHuobi();
}