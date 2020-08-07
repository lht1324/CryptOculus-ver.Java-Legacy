package org.techtown.cryptoculus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.gson.Gson;

import org.techtown.cryptoculus.coinInfo.CoinInfoBithumb;
import org.techtown.cryptoculus.coinInfo.CoinInfoCoinone;
import org.techtown.cryptoculus.coinInfo.CoinInfoHuobi;
import org.techtown.cryptoculus.currencys.CurrencysCoinone;
import org.techtown.cryptoculus.function.ArrayMaker;
import org.techtown.cryptoculus.function.Retrofit2Interface;
import org.techtown.cryptoculus.ticker.TickerFormatBithumb;
import org.techtown.cryptoculus.ticker.TickerFormatHuobi;
import org.techtown.cryptoculus.ticker.TickerHuobi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MainAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;
    Spinner spinner;
    Button button;
    ArrayAdapter<String> spinnerAdapter;
    boolean refreshedCoinone = false;
    boolean refreshedBithumb = false;
    boolean refreshedHuobi = false;
    boolean restartApp = false;
    private int sendIntent = 1;
    private int getIntent = 2;

    ArrayList<String> spinnerAdapterItems = new ArrayList<String>();
    ArrayList<CoinInfoCoinone> coinInfosCoinone;
    ArrayList<CoinInfoBithumb> coinInfosBithumb;
    ArrayList<CoinInfoHuobi> coinInfosHuobi;

    String coinoneAddress = "https://api.coinone.co.kr/";
    String bithumbAddress = "https://api.bithumb.com/";
    String huobiAddress = "https://api-cloud.huobi.co.kr/";
    String URL = coinoneAddress; // 어떤 거래소인지 구분하는데 사용
    String chartName = "BTC";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        spinner = findViewById(R.id.spinner);
        button = findViewById(R.id.button);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                StringBuffer result = new StringBuffer();
                String temp = (String) spinner.getItemAtPosition(position);

                for (int i = 0; i < temp.length(); i++) {
                    if (temp.charAt(i) >= 65 && temp.charAt(i) <= 122)
                        result.append(temp.charAt(i));
                }

                chartName = result.toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent();

                if (URL.equals(coinoneAddress))
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://coinone.co.kr/chart?site=coinone" + chartName.toLowerCase() + "&unit_time=15m"));
                if (URL.equals(bithumbAddress))
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://m.bithumb.com/trade/chart/" + chartName + "_KRW"));
                if (URL.equals(huobiAddress))
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.huobi.co.kr/ko-kr/exchange/" + chartName.toLowerCase() + "_krw/"));

                startActivity(intent);
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        SharedPreferences pref = getSharedPreferences("restartCheck", MODE_PRIVATE);
        restartApp = pref.getBoolean("restartApp", false);

        if (restartApp) {
            pref = getSharedPreferences("URL", MODE_PRIVATE);
            URL = pref.getString("URL", coinoneAddress);
        }

        getData();

        init();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (URL.equals(coinoneAddress))
                    refreshedCoinone = true;

                if (URL.equals(bithumbAddress))
                    refreshedBithumb = true;

                if (URL.equals(huobiAddress))
                    refreshedHuobi = true;

                getData();

                init();

                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public void onBackPressed() {
        SharedPreferences pref;
        SharedPreferences.Editor editor;

        if (!isEmpty(coinInfosCoinone)) {
            pref = getSharedPreferences("saveCoinone", Activity.MODE_PRIVATE);
            editor = pref.edit();
            editor.putBoolean("isEmptyCoinone", false);

            for (int i = 0; i < coinInfosCoinone.size(); i++) {
                editor.putInt(coinInfosCoinone.get(i).getCoinName() + "position", i);
                editor.putBoolean(coinInfosCoinone.get(i).getCoinName(), coinInfosCoinone.get(i).getCoinViewCheck());
            }

            editor.commit();
        }

        if (!isEmpty(coinInfosBithumb)) {
            pref = getSharedPreferences("saveBithumb", Activity.MODE_PRIVATE);
            editor = pref.edit();
            editor.putBoolean("isEmptyBithumb", false);

            for (int i = 0; i < coinInfosBithumb.size(); i++) {
                editor.putInt(coinInfosBithumb.get(i).getCoinName() + "position", i);
                editor.putBoolean(coinInfosBithumb.get(i).getCoinName(), coinInfosBithumb.get(i).getCoinViewCheck());
            }

            editor.commit();
        }

        if (!isEmpty(coinInfosHuobi)) {
            pref = getSharedPreferences("saveHuobi", Activity.MODE_PRIVATE);
            editor = pref.edit();
            editor.putBoolean("isEmptyHuobi", false);

            for (int i = 0; i < coinInfosHuobi.size(); i++) {
                editor.putInt(coinInfosHuobi.get(i).getCoinName() + "position", i);
                editor.putBoolean(coinInfosHuobi.get(i).getCoinName(), coinInfosHuobi.get(i).getCoinViewCheck());
            }

            editor.commit();
        }

        pref = getSharedPreferences("URL", Activity.MODE_PRIVATE);
        editor = pref.edit();
        editor.putString("URL", URL);
        editor.commit();

        pref = getSharedPreferences("restartCheck", Activity.MODE_PRIVATE);
        editor = pref.edit();

        editor.putBoolean("restartApp", true);
        editor.commit();

        restartApp = true;

        finish();
        super.onBackPressed();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == sendIntent) {
            if (resultCode == getIntent) {
                println("Data transfer successed.");

                URL = intent.getExtras().getString("URL");

                if (URL.equals(coinoneAddress)) {
                    coinInfosCoinone = (ArrayList<CoinInfoCoinone>) intent.getSerializableExtra("coinInfosCoinone");
                    refreshedCoinone = true;
                }

                if (URL.equals(bithumbAddress)) {
                    coinInfosBithumb = (ArrayList<CoinInfoBithumb>) intent.getSerializableExtra("coinInfosBithumb");
                    refreshedBithumb = true;
                }

                if (URL.equals(huobiAddress)) {
                    coinInfosHuobi = (ArrayList<CoinInfoHuobi>) intent.getSerializableExtra("coinInfosHuobi");
                    refreshedHuobi = true;
                }

                getData();

                init();
            }

            else {
                println("Data transfer failed.");
            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;
    }

    //액션버튼을 클릭했을때의 동작
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id == R.id.coinone || id == R.id.bithumb || id == R.id.huobi) {
            if (id == R.id.coinone)
                URL = coinoneAddress;

            if (id == R.id.bithumb)
                URL = bithumbAddress;

            if (id == R.id.huobi)
                URL = huobiAddress;

            getData();

            init();

            return true;
        }

        if (id == R.id.option) {
            Intent intent = new Intent(getApplicationContext(), OptionActivity.class);
            intent.putExtra("URL", URL);

            if (URL.equals(coinoneAddress))
                intent.putExtra("coinInfosCoinone", coinInfosCoinone);

            if (URL.equals(bithumbAddress))
                intent.putExtra("coinInfosBithumb", coinInfosBithumb);

            if (URL.equals(huobiAddress))
                intent.putExtra("coinInfosHuobi", coinInfosHuobi);

            startActivityForResult(intent, sendIntent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void getData() { // 여기 한 곳에서 퉁칠 수 있겠는데?
        spinnerAdapterItems = new ArrayList<String>(); // 스피너 내부에 삽입되는 mObjects를 초기화
        adapter = new MainAdapter(); // 뷰가 뒤섞이는 걸 방지해준다
        spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, spinnerAdapterItems); // 초기화된 Array를 삽입해 mObjects를 초기화

        adapter.setURL(URL);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        Retrofit2Interface client = retrofit.create(Retrofit2Interface.class);

        Call<Object> call = client.getCoinone("all");

        if (URL.equals(coinoneAddress))
            call = client.getCoinone("all");

        if (URL.equals(bithumbAddress))
            call = client.getBithumb();

        if (URL.equals(huobiAddress))
            call = client.getHuobi();

        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                responseProcess(response.body().toString());
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                println("RetrofitCall Failed.");
            }
        });
    }

    public void responseProcess(String response) {
        Gson gson = new Gson();
        ArrayMaker arrayMaker = new ArrayMaker(restartApp, refreshedCoinone, refreshedBithumb, refreshedHuobi, coinInfosCoinone, coinInfosBithumb, coinInfosHuobi, this); // coinInfosHuobi 수정

        if (URL.equals(coinoneAddress)) {
            CurrencysCoinone currencyListCoinone = gson.fromJson(response, CurrencysCoinone.class);

            coinInfosCoinone = arrayMaker.makeArrayCoinone(currencyListCoinone);

            for (int i = 0; i < coinInfosCoinone.size(); i++) {
                if (coinInfosCoinone.get(i).getCoinData() != null && coinInfosCoinone.get(i).getCoinViewCheck()) {
                    adapter.addItem(coinInfosCoinone.get(i));
                    // println(coinInfosCoinone.get(i).getCoinData() + " is added to adapter."); // 상장 폐지 확인 용도

                    String temp = coinInfosCoinone.get(i).getCoinData().currency.toUpperCase() + " / " + coinInfosCoinone.get(i).getCoinName();
                    spinnerAdapter.add(temp);
                }
            }
        }

        if (URL.equals(bithumbAddress)) {
            TickerFormatBithumb tickerFormatBithumb = gson.fromJson(response, TickerFormatBithumb.class);

            coinInfosBithumb = arrayMaker.makeArrayBithumb(tickerFormatBithumb.data);

            for (int i = 0; i < coinInfosBithumb.size(); i++) {
                if (coinInfosBithumb.get(i).getCoinData() != null && coinInfosBithumb.get(i).getCoinViewCheck()) {
                    adapter.addItem(coinInfosBithumb.get(i));
                    // println(coinInfosBithumb.get(i).getCoinData() + " is added to adapter."); // 상장 폐지 확인 용도

                    spinnerAdapter.add(coinInfosBithumb.get(i).getCoinName());
                }
            }
        }

        if (URL.equals(huobiAddress)) {
            TickerFormatHuobi tickerFormatHuobi = gson.fromJson(response, TickerFormatHuobi.class);
            ArrayList<TickerHuobi> tickersHuobi = new ArrayList<TickerHuobi>();

            for (int i = 0; i < tickerFormatHuobi.data.size(); i++) {
                TickerHuobi ticker = tickerFormatHuobi.data.get(i);

                if (ticker.symbol.contains("krw"))
                    tickersHuobi.add(ticker);
            }

            coinInfosHuobi = arrayMaker.makeArrayHuobi(tickersHuobi);

            for (int i = 0; i < coinInfosHuobi.size(); i++) {
                if (coinInfosHuobi.get(i).getCoinData() != null && coinInfosHuobi.get(i).getCoinViewCheck()) {
                    adapter.addItem(coinInfosHuobi.get(i));
                    // println(coinInfosHuobi.get(i).getCoinData() + " is added to adapter."); // 상장 폐지 확인 용도

                    spinnerAdapter.add(coinInfosHuobi.get(i).getCoinName());
                }

            }
        }
        adapter.notifyDataSetChanged();
        spinnerAdapter.notifyDataSetChanged();
    }

    public void init() {
        adapter.setURL(URL);

        if (URL.equals(coinoneAddress)) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF0079FE));
            getSupportActionBar().setTitle("Coinone");
        }

        if (URL.equals(bithumbAddress)) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFFF37321));
            getSupportActionBar().setTitle("Bithumb");
        }

        if (URL.equals(huobiAddress)) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF1C2143));
            getSupportActionBar().setTitle("Huobi Korea");
        }

        spinner.setAdapter(spinnerAdapter);
        recyclerView.setAdapter(adapter);
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

        if (object instanceof Object[])
            return (((Object[]) object).length == 0);

        return false;
    }

    public void println(String data) {
        Log.d("MainActivity", data);
    }
}
