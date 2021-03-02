package com.example.cyedemo.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cyedemo.database.table.QrCodeData;
import com.example.cyedemo.databinding.ItemDataBinding;
import com.example.cyedemo.ui.activity.ImageActivity;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;

/**
 * Created by Anik Roy on 2/25/2021.
 */
public class ShowQrCodeDataAdapter extends PagedListAdapter<QrCodeData,ShowQrCodeDataAdapter.ViewHolder> {

    private Context context;

    private static DiffUtil.ItemCallback<QrCodeData> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<QrCodeData>() {
                @Override
                public boolean areItemsTheSame(QrCodeData oldItem, QrCodeData newItem) {

                    return oldItem.getId() == newItem.getId();
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(QrCodeData oldItem, QrCodeData newItem) {
                    return oldItem.equals(newItem);
                }
            };

    @Inject
    public ShowQrCodeDataAdapter(@ApplicationContext Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDataBinding binding = ItemDataBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        QrCodeData qrCodeData =getItem(position);
        if (qrCodeData != null){
            holder.binding.itemTv.setText(qrCodeData.getData());

        }
        holder.itemView.setOnClickListener(v -> {
            context.startActivity(new Intent(context, ImageActivity.class));
        });
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemDataBinding binding;
        public ViewHolder(ItemDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
