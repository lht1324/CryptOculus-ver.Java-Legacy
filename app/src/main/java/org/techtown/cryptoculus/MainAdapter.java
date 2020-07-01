package org.techtown.cryptoculus;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> implements ItemTouchHelperCallback.ItemTouchHelperAdapter {
    ArrayList<Object> coinInfos = new ArrayList<Object>();

    String coinoneAddress = "https://api.coinone.co.kr/ticker?currency=all";
    String bithumbAddress = "https://api.bithumb.com/public/ticker/ALL_KRW";
    String huobiAddress = "https://api-cloud.huobi.co.kr/market/tickers";
    String URL;
    // String URL = coinoneAddress; // 코인원인지 빗썸 상태인지 구분하는데 사용

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        TextView textView2;
        TextView textView3;
        TextView textView4;
        TextView textView5;
        TextView textView6;
        TextView textView7;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView);
            textView2 = itemView.findViewById(R.id.textView2);
            textView3 = itemView.findViewById(R.id.textView3);
            textView4 = itemView.findViewById(R.id.textView4);
            textView5 = itemView.findViewById(R.id.textView5);
            textView6 = itemView.findViewById(R.id.textView6);
            textView7 = itemView.findViewById(R.id.textView7);
        }
        // 변수 하나 정해주고 그거에 따라서 코인원이냐 빗썸이냐 결정
        // 그냥 코인원이랑 빗썸 어댑터 하나씩 만들자
        public void setItemCoinone(CoinInfoCoinone coinInfo) {
            imageView.setImageResource(coinInfo.getCoinImageIndex()); // 배열 인덱싱할 인덱스 필요
            textView.setText(coinInfo.getCoinData().currency.toUpperCase() + " / " + coinInfo.getCoinName());

            // 배열에 이미지 화폐 순서대로 넣어놓고 꺼내서 삽입
            try {
                DecimalFormat formatter = new DecimalFormat("###,###.##");
                textView2.setText("현재가 : " + formatter.format(Double.parseDouble(coinInfo.getCoinData().last)) + "원");

                String result = Double.toString(Double.parseDouble(coinInfo.getCoinData().last) - Double.parseDouble(coinInfo.getCoinData().yesterday_last));

                if (Double.parseDouble(result) < 0) {
                    result = Double.toString(-1 * Double.parseDouble(result));
                    textView4.setText("-" + formatter.format(Double.parseDouble(result)) + "원");
                    textView4.setTextColor(Color.parseColor("#FF0000"));
                }

                else {
                    textView4.setText("+" + formatter.format(Double.parseDouble(result)) + "원");
                    textView4.setTextColor(Color.parseColor("#0000FF"));
                }

            }
            catch (NumberFormatException e) {
            }
        }
        public void setItemBithumb(CoinInfoBithumb coinInfo) {
            imageView.setImageResource(coinInfo.getCoinImageIndex()); // 배열 인덱싱할 인덱스 필요
            textView.setText(coinInfo.getCoinName());

            // 배열에 이미지 화폐 순서대로 넣어놓고 꺼내서 삽입
            try {
                DecimalFormat formatter = new DecimalFormat("###,###.##");
                textView2.setText(formatter.format(Double.parseDouble(coinInfo.getCoinData().closing_price)) + "원");

                String result = Double.toString(Double.parseDouble(coinInfo.getCoinData().closing_price) - Double.parseDouble(coinInfo.getCoinData().prev_closing_price));

                if (Double.parseDouble(result) < 0) {
                    textView3.setText(formatter.format(Double.parseDouble(result)) + "원");
                    textView3.setTextColor(Color.parseColor("#FF0000"));
                }

                else {
                    textView3.setText("+" + formatter.format(Double.parseDouble(result)) + "원");
                    textView3.setTextColor(Color.parseColor("#0000FF"));
                }

                if (Double.parseDouble(coinInfo.getCoinData().fluctate_rate_24H) < 0) {
                    textView4.setText(formatter.format(Double.parseDouble(coinInfo.getCoinData().fluctate_rate_24H)) + "%");
                    textView4.setTextColor(Color.parseColor("#FF0000"));
                }
                else {
                    textView4.setText("+" + formatter.format(Double.parseDouble(coinInfo.getCoinData().fluctate_rate_24H)) + "%");
                    textView4.setTextColor(Color.parseColor("#0000FF"));
                }
            }
            catch (NumberFormatException e) {
            }
        }

        public void setItemHuobi(CoinInfoHuobi coinInfo) {
            imageView.setImageResource(coinInfo.getCoinImageIndex()); // 배열 인덱싱할 인덱스 필요
            textView.setText(coinInfo.getCoinName());

            // 배열에 이미지 화폐 순서대로 넣어놓고 꺼내서 삽입
            try {
                DecimalFormat formatter = new DecimalFormat("###,###.##");

                textView2.setText("현재가 : " + formatter.format(Double.parseDouble(coinInfo.getCoinData().close)) + "원");
                textView3.setText("거래량 : " + formatter.format(Double.parseDouble(coinInfo.getCoinData().amount)));

                textView4.setText("시가 : " + formatter.format(Double.parseDouble(coinInfo.getCoinData().open)) + "원");
                textView5.setText("종가 : " + formatter.format(Double.parseDouble(coinInfo.getCoinData().close)) + "원");
                textView6.setText("고가 : " + formatter.format(Double.parseDouble(coinInfo.getCoinData().high)) + "원");
                textView7.setText("저가 : " + formatter.format(Double.parseDouble(coinInfo.getCoinData().low)) + "원");
            }
            catch (NumberFormatException e) {
            }
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.coin_item_coinone, viewGroup, false);

        if (URL.equals(coinoneAddress))
            itemView = inflater.inflate(R.layout.coin_item_coinone, viewGroup, false);
        if (URL.equals(bithumbAddress))
            itemView = inflater.inflate(R.layout.coin_item_bithumb, viewGroup, false);
        if (URL.equals(huobiAddress))
            itemView = inflater.inflate(R.layout.coin_item_huobi, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        if (URL.equals(coinoneAddress))
            viewHolder.setItemCoinone((CoinInfoCoinone) coinInfos.get(position));

        if (URL.equals(bithumbAddress))
            viewHolder.setItemBithumb((CoinInfoBithumb) coinInfos.get(position));

        if (URL.equals(huobiAddress))
            viewHolder.setItemHuobi((CoinInfoHuobi) coinInfos.get(position));
    }

    public int getItemCount() {
        return coinInfos.size();
    }

    public void addItem(Object coinInfo) {
        coinInfos.add(coinInfo);
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(coinInfos, i, i + 1);
            }
        }
        else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(coinInfos, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    public void println(String data) {
        Log.d("MainAdapter", data);
    }
}