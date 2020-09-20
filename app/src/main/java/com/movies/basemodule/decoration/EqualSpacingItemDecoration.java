package com.movies.basemodule.decoration;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class EqualSpacingItemDecoration extends RecyclerView.ItemDecoration {
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    public static final int GRID = 2;

    private int spacing;
    private int displayMode = -1;

    public EqualSpacingItemDecoration(int spacing) {
        this.spacing = spacing;
    }

    public EqualSpacingItemDecoration(int spacing, int displayMode) {
        this.spacing = spacing;
        this.displayMode = displayMode;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildViewHolder(view).getAbsoluteAdapterPosition();
        int itemCount = state.getItemCount();
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        setSpacingForDirection(outRect, layoutManager, position, itemCount);
    }

    private void setSpacingForDirection(Rect outRect, RecyclerView.LayoutManager layoutManager, int position, int itemCount) {
        // Resolve display mode automatically
        if (displayMode == -1) {
            displayMode = resolveDisplayMode(layoutManager);
        }
        if (displayMode == HORIZONTAL) {
            outRect.left = spacing;
            outRect.right = position == itemCount - 1 ? spacing : 0;
            outRect.top = spacing;
            outRect.bottom = spacing;
        }
        if (displayMode == VERTICAL) {
            outRect.left = spacing;
            outRect.right = spacing;
            outRect.top = spacing;
            outRect.bottom = position == itemCount - 1 ? spacing : 0;
        }
        if (displayMode == GRID) {
            if (layoutManager instanceof GridLayoutManager) {
                int cols = ((GridLayoutManager) layoutManager).getSpanCount();
                int rows = itemCount / cols + 1;
                outRect.left = spacing;
                outRect.right = position % cols == cols - 1 ? spacing : 0;
                outRect.top = spacing;
                outRect.bottom = position / cols == rows - 1 ? spacing : 0;
            }
        }
    }

    private int resolveDisplayMode(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager instanceof GridLayoutManager) {
            return GRID;
        }
        return layoutManager.canScrollHorizontally() ? HORIZONTAL : VERTICAL;
    }
}