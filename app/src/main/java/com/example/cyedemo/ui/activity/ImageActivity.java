
package com.example.cyedemo.ui.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.cyedemo.databinding.ActivityImageBinding;
import com.example.cyedemo.model.Image;
import com.example.cyedemo.ui.adapter.ImageAdapter;
import com.example.cyedemo.viewmodel.ImageViewModel;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ImageActivity extends AppCompatActivity {

    ActivityImageBinding binding;
    ImageViewModel imageViewModel;
    @Inject ImageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityImageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        imageViewModel = new ViewModelProvider(this).get(ImageViewModel.class);

        imageViewModel.getPagedListLiveData().observe(this, new Observer<PagedList<Image>>() {
            @Override
            public void onChanged(PagedList<Image> images) {

                    initRecyclerView(images);
            }
        });
    }

    private void initRecyclerView(PagedList<Image> images) {
        binding.progressBar.setVisibility(View.GONE);
        binding.imageRv.setVisibility(View.VISIBLE);
        adapter.submitList(images);
        binding.imageRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.imageRv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}