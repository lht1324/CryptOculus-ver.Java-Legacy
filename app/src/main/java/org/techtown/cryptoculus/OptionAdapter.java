package org.techtown.cryptoculus;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.Filter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OptionAdapter extends RecyclerView.Adapter<OptionAdapter.ViewHolder> implements ItemTouchHelperCallback.ItemTouchHelperAdapter {
    ArrayList<Object> coinInfos = new ArrayList<Object>(); // editText가 비었을 때 리사이클러뷰에 들어가는 배열, 최종 데이터 저장용
    ArrayList<Object> filteredCoinInfos = new ArrayList<Object>(); // 검색했을 때 리사이클러뷰에 들어가는 배열
    ArrayList<Object> unFilteredCoinInfos = new ArrayList<Object>();

    String coinoneAddress = "https://api.coinone.co.kr/ticker?currency=all";
    String bithumbAddress = "https://api.bithumb.com/public/ticker/ALL_KRW";
    String huobiAddress = "https://api-cloud.huobi.co.kr/market/tickers";
    String URL = coinoneAddress; // 코인원인지 빗썸 상태인지 구분하는데 사용

    private DataTransferOption mCallback;

    OptionAdapter(DataTransferOption listener) {
        this.mCallback = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        CheckedTextView checkedTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            checkedTextView = itemView.findViewById(R.id.checkedTextView);
        }

        public void setItemCoinone(CoinInfoCoinone coinInfo) {
            imageView.setImageResource(coinInfo.getCoinImageIndex());
            checkedTextView.setText(coinInfo.getCoinData().currency.toUpperCase() + " / " + coinInfo.getCoinName());
            checkedTextView.setChecked(coinInfo.getCoinViewCheck());
        }

        public void setItemBithumb(CoinInfoBithumb coinInfo) {
            imageView.setImageResource(coinInfo.getCoinImageIndex());
            checkedTextView.setText(coinInfo.getCoinName());
            checkedTextView.setChecked(coinInfo.getCoinViewCheck());
        }

        public void setItemHuobi(CoinInfoHuobi coinInfo) {
            imageView.setImageResource(coinInfo.getCoinImageIndex());
            checkedTextView.setText(coinInfo.getCoinName());
            checkedTextView.setChecked(coinInfo.getCoinViewCheck());
            Log.d("OptionAdapter", coinInfo.getCoinName() + " in coinInfosHuobi");
        }
    }

    public OptionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.option_item, viewGroup, false);

        return new OptionAdapter.ViewHolder(itemView);
    }

    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final int finalPosition = position;

        if (URL.equals(coinoneAddress)) {
            viewHolder.setItemCoinone((CoinInfoCoinone) filteredCoinInfos.get(position));

            if (!((CoinInfoCoinone) filteredCoinInfos.get(position)).getCoinViewCheck())
                mCallback.changeSelectAll(false);

            viewHolder.checkedTextView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    CheckedTextView checkedTextView = (CheckedTextView) view;
                    checkedTextView.toggle();

                    ((CoinInfoCoinone) filteredCoinInfos.get(finalPosition)).setCoinViewCheck(checkedTextView.isChecked());

                    for (int i = 0; i < coinInfos.size(); i++) {
                        String temp1 = ((CoinInfoCoinone) filteredCoinInfos.get(finalPosition)).getCoinName();
                        String temp2 = ((CoinInfoCoinone) coinInfos.get(i)).getCoinName();
                        if (temp1.equals(temp2))
                            ((CoinInfoCoinone) coinInfos.get(i)).setCoinViewCheck(checkedTextView.isChecked());
                    }

                    if (checkedTextView.isChecked()) { // checkedTextView가 체크됐을 때
                        for (int i = 0; i < filteredCoinInfos.size(); i++) {
                            if (!((CoinInfoCoinone) filteredCoinInfos.get(i)).getCoinViewCheck())
                                break;
                            if (i == filteredCoinInfos.size() - 1 && ((CoinInfoCoinone) filteredCoinInfos.get(i)).getCoinViewCheck())
                                mCallback.changeSelectAll(true);
                        }
                    }

                    if (!checkedTextView.isChecked()) { // checkedTextView의 체크가 풀렸을 때
                        mCallback.changeSelectAll(false);
                    }
                }
            });
        }

        if (URL.equals(bithumbAddress)) {
            viewHolder.setItemBithumb((CoinInfoBithumb) filteredCoinInfos.get(position));

            if (!((CoinInfoBithumb) filteredCoinInfos.get(position)).getCoinViewCheck())
                mCallback.changeSelectAll(false);

            viewHolder.checkedTextView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    CheckedTextView checkedTextView = (CheckedTextView) view;
                    checkedTextView.toggle();

                    ((CoinInfoBithumb) filteredCoinInfos.get(finalPosition)).setCoinViewCheck(checkedTextView.isChecked());

                    for (int i = 0; i < coinInfos.size(); i++) {
                        String temp1 = ((CoinInfoBithumb) filteredCoinInfos.get(finalPosition)).getCoinName();
                        String temp2 = ((CoinInfoBithumb) coinInfos.get(i)).getCoinName();
                        if (temp1.equals(temp2))
                            ((CoinInfoBithumb) coinInfos.get(i)).setCoinViewCheck(checkedTextView.isChecked());
                    }

                    if (checkedTextView.isChecked()) {
                        for (int i = 0; i < filteredCoinInfos.size(); i++) {
                            if (!((CoinInfoBithumb) filteredCoinInfos.get(i)).getCoinViewCheck())
                                break;
                            if (i == filteredCoinInfos.size() - 1 && ((CoinInfoBithumb) filteredCoinInfos.get(i)).getCoinViewCheck())
                                mCallback.changeSelectAll(true);
                        }
                    }

                    if (!checkedTextView.isChecked()) {
                        mCallback.changeSelectAll(false);
                    }
                }
            });
        }

        if (URL.equals(huobiAddress)) {
            viewHolder.setItemHuobi((CoinInfoHuobi) filteredCoinInfos.get(position));

            if (!((CoinInfoHuobi) filteredCoinInfos.get(position)).getCoinViewCheck())
                mCallback.changeSelectAll(false);

            viewHolder.checkedTextView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    CheckedTextView checkedTextView = (CheckedTextView) view;
                    checkedTextView.toggle();

                    ((CoinInfoHuobi) filteredCoinInfos.get(finalPosition)).setCoinViewCheck(checkedTextView.isChecked());

                    for (int i = 0; i < coinInfos.size(); i++) {
                        String temp1 = ((CoinInfoHuobi) filteredCoinInfos.get(finalPosition)).getCoinName();
                        String temp2 = ((CoinInfoHuobi) coinInfos.get(i)).getCoinName();
                        if (temp1.equals(temp2))
                            ((CoinInfoHuobi) coinInfos.get(i)).setCoinViewCheck(checkedTextView.isChecked());
                    }

                    if (checkedTextView.isChecked()) { // checkedTextView가 체크됐을 때
                        for (int i = 0; i < filteredCoinInfos.size(); i++) {
                            if (!((CoinInfoHuobi) filteredCoinInfos.get(i)).getCoinViewCheck())
                                break;
                            if (i == filteredCoinInfos.size() - 1 && ((CoinInfoHuobi) filteredCoinInfos.get(i)).getCoinViewCheck())
                                mCallback.changeSelectAll(true);
                        }
                    }

                    if (!checkedTextView.isChecked()) { // checkedTextView의 체크가 풀렸을 때
                        mCallback.changeSelectAll(false);
                    }
                }
            });
        }
    }

    public void addItem(Object coinInfo) {
        coinInfos.add(coinInfo); // 데이터 저장용
        filteredCoinInfos.add(coinInfo); // 검색 시 보여주는 배열
        unFilteredCoinInfos.add(coinInfo); // Filter용 배열
    }

    public int getItemCount() {
            return filteredCoinInfos.size();
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public void selectAll(boolean checkAll) { // filteredCoinInfos랑 coinInfos를 조건부로 체크박아야 한다 (isEmpty(constraint))
        if (URL.equals(coinoneAddress)) {
            if (checkAll) {
                for (int i = 0; i < filteredCoinInfos.size(); i++) {
                    ((CoinInfoCoinone) filteredCoinInfos.get(i)).setCoinViewCheck(true);

                    if (mCallback.getEditTextIsEmpty())
                        ((CoinInfoCoinone) coinInfos.get(i)).setCoinViewCheck(true);
                }
            }
            else {
                for (int i = 0; i < filteredCoinInfos.size(); i++) {
                    ((CoinInfoCoinone) filteredCoinInfos.get(i)).setCoinViewCheck(false);

                    if (mCallback.getEditTextIsEmpty())
                        ((CoinInfoCoinone) coinInfos.get(i)).setCoinViewCheck(false);
                }
            }
        }
        if (URL.equals(bithumbAddress)) {
            if (checkAll) {
                for (int i = 0; i < filteredCoinInfos.size(); i++) {
                    ((CoinInfoBithumb) filteredCoinInfos.get(i)).setCoinViewCheck(true);

                    if (mCallback.getEditTextIsEmpty())
                        ((CoinInfoBithumb) coinInfos.get(i)).setCoinViewCheck(true);
                }
            }
            else {
                for (int i = 0; i < filteredCoinInfos.size(); i++) {
                    ((CoinInfoBithumb) filteredCoinInfos.get(i)).setCoinViewCheck(false);

                    if (mCallback.getEditTextIsEmpty())
                        ((CoinInfoBithumb) coinInfos.get(i)).setCoinViewCheck(false);
                }
            }
        }

        if (URL.equals(huobiAddress)) {
            if (checkAll) {
                for (int i = 0; i < filteredCoinInfos.size(); i++) {
                    ((CoinInfoHuobi) filteredCoinInfos.get(i)).setCoinViewCheck(true);

                    if (mCallback.getEditTextIsEmpty())
                        ((CoinInfoHuobi) coinInfos.get(i)).setCoinViewCheck(true);
                }
            }
            else {
                for (int i = 0; i < filteredCoinInfos.size(); i++) {
                    ((CoinInfoHuobi) filteredCoinInfos.get(i)).setCoinViewCheck(false);

                    if (mCallback.getEditTextIsEmpty())
                        ((CoinInfoHuobi) coinInfos.get(i)).setCoinViewCheck(false);
                }
            }
        }

        this.notifyDataSetChanged();
    }

    public ArrayList<Object> getCoinInfos() {
        if (URL.equals(coinoneAddress)) {
            for (int i = 0; i < filteredCoinInfos.size(); i++) {
                String temp1 = ((CoinInfoCoinone) filteredCoinInfos.get(i)).getCoinName();
                String temp2 = ((CoinInfoCoinone) coinInfos.get(i)).getCoinName();

                if (!(temp1.equals(temp2))) { // 순서가 바뀐 것
                    for (int j = 0; j < coinInfos.size(); j++) {
                        if (temp1.equals(((CoinInfoCoinone) coinInfos.get(j)).getCoinName()))
                            Collections.swap(coinInfos, i, j);
                    }
                }
            }
        }

        if (URL.equals(bithumbAddress)) {
            for (int i = 0; i < filteredCoinInfos.size(); i++) {
                String temp1 = ((CoinInfoBithumb) filteredCoinInfos.get(i)).getCoinName();
                String temp2 = ((CoinInfoBithumb) coinInfos.get(i)).getCoinName();

                if (!(temp1.equals(temp2))) { // 순서가 바뀐 것
                    for (int j = 0; j < coinInfos.size(); j++) {
                        if (temp1.equals(((CoinInfoBithumb) coinInfos.get(j)).getCoinName()))
                            Collections.swap(coinInfos, i, j);
                    }
                }
            }
        }

        if (URL.equals(huobiAddress)) {
            for (int i = 0; i < filteredCoinInfos.size(); i++) {
                String temp1 = ((CoinInfoHuobi) filteredCoinInfos.get(i)).getCoinName(); // temp1을 기준으로 비교하니 temp1에 coinInfos가 들어오면 기준이 섞여버린다
                String temp2 = ((CoinInfoHuobi) coinInfos.get(i)).getCoinName();

                if (!(temp1.equals(temp2))) { // 순서가 바뀐 것
                    for (int j = 0; j < coinInfos.size(); j++) {
                        if (temp1.equals(((CoinInfoHuobi) coinInfos.get(j)).getCoinName()))
                            Collections.swap(coinInfos, i, j);
                    }
                }
            }
        }

        return coinInfos;
    }
    
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();

                if(charString.isEmpty()) {
                    filteredCoinInfos = unFilteredCoinInfos;
                    // 문제 해결됐다... filteredCoinInfos 들어갈 자리에 coinInfos 들어가 있던 걸 안 바꿔놨네 시발
                }

                else {
                    ArrayList<Object> filteringList = new ArrayList<>();

                    int count = 0;

                    if (URL.equals(coinoneAddress)) {
                        for (int i = 0; i < unFilteredCoinInfos.size(); i++) {
                            CoinInfoCoinone coinInfo = (CoinInfoCoinone) unFilteredCoinInfos.get(i);
                            String temp = coinInfo.getCoinData().currency.toUpperCase() + " / " + coinInfo.getCoinName();

                            if (temp.toLowerCase().contains(charString.toLowerCase())) {
                                filteringList.add(unFilteredCoinInfos.get(i));
                            }
                        }

                        for(int i = 0; i < filteringList.size(); i++) {
                            if (((CoinInfoCoinone) filteringList.get(i)).getCoinViewCheck() && count != i) {
                                CoinInfoCoinone temp = (CoinInfoCoinone) filteringList.get(i);
                                filteringList.set(i, null);
                                // remove()는 자동으로 배열 크기를 줄이고 지운 위치 자체를 없애버린다

                                for (int j = i - 1; j >= count; j--)
                                    Collections.swap(filteringList, j, j + 1);

                                filteringList.set(count, temp);

                                count++;
                            }
                            if (((CoinInfoCoinone) filteringList.get(i)).getCoinViewCheck() && count == i)
                                count++;
                        }
                    }

                    if (URL.equals(bithumbAddress)) {
                        for (int i = 0; i < unFilteredCoinInfos.size(); i++) {
                            CoinInfoBithumb coinInfo = (CoinInfoBithumb) unFilteredCoinInfos.get(i);
                            String temp = coinInfo.getCoinName();

                            if (temp.toLowerCase().contains(charString.toLowerCase())) {
                                filteringList.add(unFilteredCoinInfos.get(i));
                            }
                        }

                        for(int i = 0; i < filteringList.size(); i++) {
                            if (((CoinInfoBithumb) filteringList.get(i)).getCoinViewCheck() && count != i) {
                                CoinInfoBithumb temp = (CoinInfoBithumb) filteringList.get(i);
                                filteringList.set(i, null);
                                // remove()는 자동으로 배열 크기를 줄이고 지운 위치 자체를 없애버린다

                                for (int j = i - 1; j >= count; j--)
                                    Collections.swap(filteringList, j, j + 1);

                                filteringList.set(count, temp);

                                count++;
                            }
                            if (((CoinInfoBithumb) filteringList.get(i)).getCoinViewCheck() && count == i)
                                count++;
                        }
                    }

                    if (URL.equals(huobiAddress)) {
                        for (int i = 0; i < unFilteredCoinInfos.size(); i++) {
                            CoinInfoHuobi coinInfo = (CoinInfoHuobi) unFilteredCoinInfos.get(i);
                            String temp = coinInfo.getCoinName();

                            if (temp.toLowerCase().contains(charString.toLowerCase())) {
                                filteringList.add(unFilteredCoinInfos.get(i));
                            }
                        }

                        for(int i = 0; i < filteringList.size(); i++) {
                            if (((CoinInfoHuobi) filteringList.get(i)).getCoinViewCheck() && count != i) {
                                CoinInfoHuobi temp = (CoinInfoHuobi) filteringList.get(i);
                                filteringList.set(i, null);
                                // remove()는 자동으로 배열 크기를 줄이고 지운 위치 자체를 없애버린다

                                for (int j = i - 1; j >= count; j--)
                                    Collections.swap(filteringList, j, j + 1);

                                filteringList.set(count, temp);

                                count++;
                            }
                            if (((CoinInfoHuobi) filteringList.get(i)).getCoinViewCheck() && count == i)
                                count++;
                        }
                    }

                    filteredCoinInfos = filteringList;
                }

                FilterResults filterResults = new FilterResults();

                if (coinInfos.size() != filteredCoinInfos.size()) // editText가 비었을 때
                    filterResults.values = coinInfos;
                else
                    filterResults.values = filteredCoinInfos;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                coinInfos = (ArrayList<Object>) results.values; // 필터링 된 리스트
                notifyDataSetChanged();
            }
        };
    }

    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(filteredCoinInfos, i, i + 1);
                Collections.swap(coinInfos, i, i + 1);
            }
        }
        else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(filteredCoinInfos, i, i - 1);
                Collections.swap(coinInfos, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        return true;
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
        Log.d("OptionAdapter", data);
    }
}
