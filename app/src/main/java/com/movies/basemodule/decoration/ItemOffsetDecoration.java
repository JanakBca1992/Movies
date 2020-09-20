package com.movies.basemodule.decoration;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemOffsetDecoration extends RecyclerView.ItemDecoration {
    private int offsetLeft;
    private int offsetTop;
    private int offsetRight;
    private int offsetBottom;

    public ItemOffsetDecoration(int offsetLeft, int offsetTop, int offsetRight, int offsetBottom) {
        this.offsetLeft = offsetLeft;
        this.offsetTop = offsetTop;
        this.offsetRight = offsetRight;
        this.offsetBottom = offsetBottom;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(offsetLeft, offsetTop, offsetRight, offsetBottom);
    }
}
