package com.example.cyedemo.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.contentcapture.ContentCaptureCondition;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cyedemo.R;
import com.example.cyedemo.databinding.ImageLayoutBinding;
import com.example.cyedemo.model.Image;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;

/**
 * Created by Anik Roy on 2/26/2021.
 */
public class ImageAdapter extends PagedListAdapter<Image,ImageAdapter.ViewHolder> {

    private Context context;

    private static DiffUtil.ItemCallback<Image> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Image>() {
                @Override
                public boolean areItemsTheSame(Image oldItem, Image newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(Image oldItem, Image newItem) {
                    return oldItem.equals(newItem);
                }

            };

    @Inject
    public ImageAdapter(@ApplicationContext Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ImageLayoutBinding binding = ImageLayoutBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Image image = getItem(position);
        Glide.with(context).load(image.getLargeImageURL()).placeholder(R.drawable.images).into(holder.binding.image);
        Glide.with(context).load(image.getUserImageURL()).placeholder(R.drawable.images).into(holder.binding.userImage);
        holder.binding.user.setText(image.getUser());
        holder.binding.like.setText(String.valueOf(image.getLikes()));
        holder.binding.comments.setText(String.valueOf(image.getComments()));

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageLayoutBinding binding;
        public ViewHolder(ImageLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
