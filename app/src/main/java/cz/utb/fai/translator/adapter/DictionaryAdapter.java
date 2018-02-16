package cz.utb.fai.translator.adapter;

/**
 * Created by krieg on 5.1.2018.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.apache.commons.collections4.MultiValuedMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.utb.fai.translator.R;

/**
 * Created by student on 09.12.2017.
 */

public class DictionaryAdapter extends RecyclerView.Adapter<DictionaryAdapter.ViewHolder> {
    private List<String> mDataSet;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dictionary_row, parent, false);

        ViewHolder vh = new ViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextRow.setText(mDataSet.get(position));

    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    //reference to row template
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextRow;

        public ViewHolder(View view){
            super(view);
            mTextRow = (TextView)view.findViewById(R.id.textView1);
        };

    };

    public DictionaryAdapter(List<String> myDataSet) {
        mDataSet = myDataSet;

    };

}
