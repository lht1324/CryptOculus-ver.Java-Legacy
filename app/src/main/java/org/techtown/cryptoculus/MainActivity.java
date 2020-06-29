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
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    static RequestQueue requestQueue;

    RecyclerView recyclerView;
    MainAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;
    Spinner spinner;
    Button button;
    int count = 0;
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
    // 정렬 기능(드래그든 뭐든), 옵션에서 체크된 것들 가장 위에 보이게 하는 기능
    // 그냥 버튼 넣어서 인터넷 접속하는 형식으로 만들까?
    // SQLite로 coinViewCheck값 저장 (onBackPressed 실행 시)
    // OnCreate에서 DB에 저장된 걸 읽어서 Array에 저장
    // 아마 boolean[]으로 저장해야 할 것 같다
    // 시작할 때 저장한 걸 읽은 뒤 for문으로 setCoinViewCheck() 실행
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

        // 거래소 별로 번호로 구분한 다음에 (URL로 구분해도 되고)
        // 번호로 구분하면 switch case 사용 가능
        // 꺼지기 전에 URL로 구분해서 sharedPreference에 Int로 저장해서 거래소 켰을 때 불러오는 기능 추가하자
        pref = getSharedPreferences("saveCoinone", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        if (!isEmpty(coinInfosCoinone)) {
            for (int i = 0; i < coinInfosCoinone.size(); i++) {
                editor.putBoolean(coinInfosCoinone.get(i).getCoinName(), coinInfosCoinone.get(i).getCoinViewCheck());
            }
            editor.commit();
        }

        pref = getSharedPreferences("saveBithumb", Activity.MODE_PRIVATE);
        editor = pref.edit();

        if (!isEmpty(coinInfosBithumb)) {
            for (int i = 0; i < coinInfosBithumb.size(); i++) {
                editor.putBoolean(coinInfosBithumb.get(i).getCoinName(), coinInfosBithumb.get(i).getCoinViewCheck());
            }
            editor.commit();
        }

        pref = getSharedPreferences("saveHuobi", Activity.MODE_PRIVATE);
        editor = pref.edit();

        if (!isEmpty(coinInfosHuobi)) {
            for (int i = 0; i < coinInfosHuobi.size(); i++) {
                editor.putBoolean(coinInfosHuobi.get(i).getCoinName(), coinInfosHuobi.get(i).getCoinViewCheck());
            }
            editor.commit();
        }

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
                        println("response = " + response);
                        println("응답 -> " + response);

                        responseProcess(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        println("에러 -> " + error.getMessage());
                        count++;
                    }
                }
        );

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
            TickerFormatHuobi tickerFormatHuobi = gson.fromJson(response, TickerFormatHuobi.class);
            JSONArray tempJArray = tickerFormatHuobi.data;
            /* Type type = new TypeToken<ArrayList<TickerHuobi>>(){}.getType();
            // List<TickerHuobi> tempTickersHuobi = gson.fromJson(response, type); */
            ArrayList<TickerHuobi> tickersHuobi = new ArrayList<TickerHuobi>();
            // tickersHuobi.addAll(tempTickersHuobi);
            for (int i = 0; i < tempJArray.length(); i++) {
                try {
                    JSONObject object = tempJArray.getJSONObject(i);

                    if (object.getString("simbol").contains("krw")) {
                        TickerHuobi temp = new TickerHuobi();

                        temp.simbol = object.getString("simbol");
                        temp.open = object.getString("open");
                        temp.high = object.getString("high");
                        temp.low = object.getString("low");
                        temp.close = object.getString("close");
                        temp.amount = object.getString("amount");
                        temp.vol = object.getString("vol");
                        temp.count = object.getString("count");
                        temp.bid = object.getString("bid");
                        temp.bidSize = object.getString("bidSize");
                        temp.ask = object.getString("ask");
                        temp.askSize = object.getString("askSize");

                        tickersHuobi.add(temp);
                    }
                } catch(JSONException e) {
                }
            }

            /* if (tickerFormatHuobi.data == null)
                println("tickerFormatHuobi's data is null");
            for (int i = 0; i < tickerFormatHuobi.data.size(); i++) {
                println("tickerFormatHuobi.data.get(" + i + ").simbol = " + tickerFormatHuobi.data.get(i).simbol);
            } // 데이터 갯수는 479개.
            // 전부 null이다.
            // 잘못 받았네
            // 형식 바꿔라
            // 어차피 String이잖아?
            // 그럼 싹 다 String으로 긁어와서 tickersHuobi.get(i)에다 박아버려 */

            coinInfosHuobi = arrayMaker.makeArrayHuobi(tickersHuobi);

            for (int i = 0; i < coinInfosHuobi.size(); i++) {
                if (coinInfosHuobi.get(i).getCoinData() != null && coinInfosHuobi.get(i).getCoinViewCheck()) {
                    adapter.addItem(coinInfosHuobi.get(i));
                    // println(coinInfosHuobi.get(i).getCoinData() + " is added to adapter."); // 상장 폐지 확인 용도

                    spinnerAdapter.add(coinInfosHuobi.get(i).getCoinName());
                }

            }
        }
        // 건드린 것도 없는데 어댑터가 맛이 가면 폐지 확인용 코드 주석 해제하고 실행해보기
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

    public void println(String data) {
        Log.d("MainActivity", data);
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
}