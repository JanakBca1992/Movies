package com.movies.utils;

import androidx.annotation.DimenRes;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.movies.R;
import com.movies.basemodule.decoration.EqualSpacingItemDecoration;
import com.movies.basemodule.decoration.ItemOffsetDecoration;

public class DataBinding {


    @BindingAdapter("app:loadImage")
    public static void loadImage(AppCompatImageView view, String imageUrl) {
        Glide.with(view.getContext()).load(imageUrl)
                .placeholder(R.drawable.ic_place_holder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(true).into(view);
    }

    @BindingAdapter("app:itemOffsetDecoration")
    public static void itemOffsetDecoration(RecyclerView view, Boolean isDivider) {
        view.addItemDecoration(new ItemOffsetDecoration(0, 0, 0, 1));
    }

    @BindingAdapter("app:itemDecoration")
    public static void itemDecoration(RecyclerView view, @DimenRes int itemSpace) {
        if (view.getTag() == null) {
            view.setTag(itemSpace);
            view.addItemDecoration(new EqualSpacingItemDecoration(itemSpace));
        }
    }
}
