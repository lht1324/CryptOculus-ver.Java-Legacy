package org.techtown.cryptoculus;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class OptionActivity extends AppCompatActivity implements DataTransferOption, TextWatcher {

    OptionAdapter optionAdapter = new OptionAdapter(this);
    RecyclerView recyclerView;
    CheckedTextView checkedTextView;
    EditText editText;

    ItemTouchHelper.Callback callback;
    ItemTouchHelper itemTouchHelper;

    ArrayList<Object> coinInfos = new ArrayList<Object>();

    String coinoneAddress = "https://api.coinone.co.kr/ticker?currency=all";
    String bithumbAddress = "https://api.bithumb.com/public/ticker/ALL_KRW";
    String huobiAddress = "https://api-cloud.huobi.co.kr/market/tickers";
    String URL = coinoneAddress; // 코인원인지 빗썸 상태인지 구분하는데 사용

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
        Intent intent = getIntent();

        URL = intent.getExtras().getString("URL");

        coinInfos = new ArrayList<Object>();

        if (URL.equals(coinoneAddress)) {
            ArrayList<CoinInfoCoinone> temp = (ArrayList<CoinInfoCoinone>) intent.getSerializableExtra("coinInfosCoinone");

            for (int i = 0; i < temp.size(); i++)
                coinInfos.add(temp.get(i));
        }

        if (URL.equals(bithumbAddress)) {
            ArrayList<CoinInfoBithumb> temp = (ArrayList<CoinInfoBithumb>) intent.getSerializableExtra("coinInfosBithumb");

            for (int i = 0; i < temp.size(); i++)
                coinInfos.add(temp.get(i));
        }

        if (URL.equals(huobiAddress)) {
            ArrayList<CoinInfoHuobi> temp = (ArrayList<CoinInfoHuobi>) intent.getSerializableExtra("coinInfosHuobi");

            for (int i = 0; i < temp.size(); i++)
                coinInfos.add(temp.get(i));
        }

        editText = findViewById(R.id.editText);
        editText.addTextChangedListener(this);

        checkedTextView = findViewById(R.id.checkedTextView);

        checkedTextView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                checkedTextView.toggle();
                optionAdapter.selectAll(checkedTextView.isChecked());
                optionAdapter.notifyDataSetChanged();
            }
        });

        setData();
        init();
    }

    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("URL", URL);

        if (URL.equals(coinoneAddress))
            intent.putExtra("coinInfosCoinone", optionAdapter.getSortedCoinInfos());

        if (URL.equals(bithumbAddress))
            intent.putExtra("coinInfosBithumb", optionAdapter.getSortedCoinInfos());

        if (URL.equals(huobiAddress))
            intent.putExtra("coinInfosHuobi", optionAdapter.getSortedCoinInfos());

        setResult(2, intent);
        finish();
        super.onBackPressed();
    }

    public void setData() {
        callback =  new ItemTouchHelperCallback(optionAdapter);
        itemTouchHelper = new ItemTouchHelper(callback);

        optionAdapter.setURL(URL); // 어댑터에 URL을 전달

        int count = 0;

        if (URL.equals(coinoneAddress)) {
            for(int i = 0; i < coinInfos.size(); i++) {
                if (((CoinInfoCoinone) coinInfos.get(i)).getCoinViewCheck() && count != i) {
                    CoinInfoCoinone temp = (CoinInfoCoinone) coinInfos.get(i);
                    coinInfos.set(i, null); // remove()는 자동으로 지운 위치 자체를 없애고 배열 크기를 줄여버린다

                    for (int j = i - 1; j >= count; j--)
                        Collections.swap(coinInfos, j, j + 1);

                    coinInfos.set(count, temp);

                    count++;
                }
                if (((CoinInfoCoinone) coinInfos.get(i)).getCoinViewCheck() && count == i)
                    count++;
            }

            for (int i = 0; i < coinInfos.size(); i++) {
                if (!((CoinInfoCoinone) coinInfos.get(i)).getCoinViewCheck())
                    checkedTextView.setChecked(false);
                if (i == coinInfos.size() - 1 && ((CoinInfoCoinone) coinInfos.get(i)).getCoinViewCheck())
                    checkedTextView.setChecked(true);

                optionAdapter.addItem(coinInfos.get(i));
            }
        }

        if (URL.equals(bithumbAddress)) {
            for(int i = 0; i < coinInfos.size(); i++) {
                if (((CoinInfoBithumb) coinInfos.get(i)).getCoinViewCheck() && count != i) {
                    CoinInfoBithumb temp = (CoinInfoBithumb) coinInfos.get(i);
                    coinInfos.set(i, null);

                    for (int j = i - 1; j >= count; j--)
                        Collections.swap(coinInfos, j, j + 1);

                    coinInfos.set(count, temp);

                    count++;
                }
                if (((CoinInfoBithumb) coinInfos.get(i)).getCoinViewCheck() && count == i)
                    count++;
            }

            for (int i = 0; i < coinInfos.size(); i++) {

                if (!((CoinInfoBithumb) coinInfos.get(i)).getCoinViewCheck())
                    checkedTextView.setChecked(false);
                if (i == coinInfos.size() - 1 && ((CoinInfoBithumb) coinInfos.get(i)).getCoinViewCheck())
                    checkedTextView.setChecked(true);

                optionAdapter.addItem(coinInfos.get(i));
            }
        }

        if (URL.equals(huobiAddress)) {
            for(int i = 0; i < coinInfos.size(); i++) {
                if (((CoinInfoHuobi) coinInfos.get(i)).getCoinViewCheck() && count != i) {
                    CoinInfoHuobi temp = (CoinInfoHuobi) coinInfos.get(i);
                    coinInfos.set(i, null);

                    for (int j = i - 1; j >= count; j--)
                        Collections.swap(coinInfos, j, j + 1);

                    coinInfos.set(count, temp);

                    count++;
                }
                if (((CoinInfoHuobi) coinInfos.get(i)).getCoinViewCheck() && count == i)
                    count++;
            }

            for (int i = 0; i < coinInfos.size(); i++) {

                if (!((CoinInfoHuobi) coinInfos.get(i)).getCoinViewCheck())
                    checkedTextView.setChecked(false);
                if (i == coinInfos.size() - 1 && ((CoinInfoHuobi) coinInfos.get(i)).getCoinViewCheck())
                    checkedTextView.setChecked(true);

                optionAdapter.addItem(coinInfos.get(i));
            }
        }

        optionAdapter.notifyDataSetChanged();
    }

    public void init() {
        recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        if (URL.equals(coinoneAddress))
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF0079FE));

        if (URL.equals(bithumbAddress))
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFFF37321));

        if (URL.equals(huobiAddress))
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF1C2143));

        itemTouchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(optionAdapter);
    }

    public void changeSelectAll(boolean selectAll) {
        this.checkedTextView.setChecked(selectAll);
    }

    public boolean getEditTextIsEmpty() {
        String temp = editText.getText().toString();

        return temp.isEmpty();
    }

    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        optionAdapter.getFilter().filter(charSequence);

        if (charSequence == "") {
            setData();
            init();
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

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
        Log.d("OptionActivity", data);
    }
}