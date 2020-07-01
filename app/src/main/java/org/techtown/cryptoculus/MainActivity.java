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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    static RequestQueue requestQueue;

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

    String coinoneAddress = "https://api.coinone.co.kr/ticker?currency=all";
    String bithumbAddress = "https://api.bithumb.com/public/ticker/ALL_KRW";
    String huobiAddress = "https://api-cloud.huobi.co.kr/market/tickers";
    String URL = coinoneAddress; // 코인원인지 빗썸 상태인지 구분하는데 사용
    String chartName = "BTC";


    // 추가해야 하는 것
    // 정렬된 순서 저장 후 불러오기(마무리)
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

        pref = getSharedPreferences("saveCoinone", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        if (!isEmpty(coinInfosCoinone)) {
            for (int i = 0; i < coinInfosCoinone.size(); i++) {
                editor.putInt(coinInfosCoinone.get(i).getCoinName() + "position", i);
                editor.putBoolean(coinInfosCoinone.get(i).getCoinName(), coinInfosCoinone.get(i).getCoinViewCheck());
            }
            // i를 저장한다
            // key는 coinName으로
            // 다시 시작했을 때 ArrayMaker에서 저장한 걸 불러와서 배열에 저장한다
            // 그 배열의 순서대로 정렬한다

            editor.commit();
        }

        pref = getSharedPreferences("saveBithumb", Activity.MODE_PRIVATE);
        editor = pref.edit();

        if (!isEmpty(coinInfosBithumb)) {
            for (int i = 0; i < coinInfosBithumb.size(); i++) {
                editor.putInt(coinInfosBithumb.get(i).getCoinName() + "position", i);
                editor.putBoolean(coinInfosBithumb.get(i).getCoinName(), coinInfosBithumb.get(i).getCoinViewCheck());
            }

            editor.commit();
        }

        pref = getSharedPreferences("saveHuobi", Activity.MODE_PRIVATE);
        editor = pref.edit();

        if (!isEmpty(coinInfosHuobi)) {
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

    public void getData() {
        spinnerAdapterItems = new ArrayList<String>(); // 스피너 내부에 삽입되는 mObjects를 초기화
        adapter = new MainAdapter(); // 뷰가 뒤섞이는 걸 방지해준다
        spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, spinnerAdapterItems); // 초기화된 Array를 삽입해 mObjects를 초기화

        adapter.setURL(URL);

        if (URL.equals(coinoneAddress) || URL.equals(bithumbAddress) || URL.equals(huobiAddress)) {

            StringRequest request = makeRequest(URL);
            request.setShouldCache(false);

            if (requestQueue == null) {
                requestQueue = Volley.newRequestQueue(getApplicationContext());
            }

            requestQueue.add(request);
            println("요청 보냄.");
        }
    }

    public StringRequest makeRequest(String url) {
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        println("응답 -> " + response);

                        responseProcess(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        println("에러 -> " + error.getMessage());
                    }
                }
        ) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String> ();
                return params;
            }
        };

        return request;
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
            println("response => " + response);
            TickerFormatHuobi tickerFormatHuobi = gson.fromJson(response, TickerFormatHuobi.class);
            // Expected BEGIN_OBJECT but was BEGIN_ARRAY (Object를 예상했는데 Array가 왔다)
            // symbol을 simbol로 오타를 쳤으니 안 들어오지...
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

        if (URL.equals(coinoneAddress))
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF0079FE));

        if (URL.equals(bithumbAddress))
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFFF37321));

        if (URL.equals(huobiAddress))
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF1C2143));

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

        if (object instanceof Object[]) {
            return (((Object[]) object).length == 0);
        }
        return false;
    }

    public void println(String data) {
        Log.d("MainActivity", data);
    }
}