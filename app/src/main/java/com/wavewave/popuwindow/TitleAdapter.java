package com.wavewave.popuwindow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author wavewave
 * @CreateDate: 2020/10/23 2:22 PM
 * @Description: 下拉列表
 * @Version: 1.0
 */
public class TitleAdapter extends RecyclerView.Adapter<TitleAdapter.TitleHolder> {
    @NonNull
    @Override
    public TitleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TitleHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_title_adapter, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TitleHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 100;
    }

    class TitleHolder extends RecyclerView.ViewHolder {

        private final TextView mTextView;

        public TitleHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.mTextView);
        }
    }
}
