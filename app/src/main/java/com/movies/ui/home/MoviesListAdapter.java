package com.movies.ui.home;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.movies.BR;
import com.movies.R;
import com.movies.data.model.movielist.Movie;
import com.movies.data.model.state.NetworkState;
import com.movies.databinding.ViewMovieItemBinding;
import com.movies.databinding.ViewNetworkStateItemBinding;

import org.jetbrains.annotations.NotNull;

@SuppressWarnings("ALL")
public class MoviesListAdapter extends PagedListAdapter<Movie, RecyclerView.ViewHolder> {

    private NetworkState networkState;

    public MoviesListAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == R.layout.view_movie_item) {
            return new ItemViewHolder(ViewMovieItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        } else if (viewType == R.layout.view_network_state_item) {
            return new StateViewHolder(ViewNetworkStateItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        } else {
            throw new IllegalArgumentException("Unknown View Type");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == R.layout.view_movie_item) {
            ((ItemViewHolder) holder).onBind(getItem(position));
        } else if (getItemViewType(position) == R.layout.view_network_state_item) {
            ((StateViewHolder) holder).onBind(networkState);
        }
    }

    private static DiffUtil.ItemCallback<Movie> DIFF_CALLBACK = new DiffUtil.ItemCallback<Movie>() {
        @Override
        public boolean areItemsTheSame(Movie oldItem, Movie newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(Movie oldItem, @NotNull Movie newItem) {
            return oldItem.equals(newItem);
        }
    };

    private boolean hasExtraRow() {
        if (networkState != null && networkState != NetworkState.LOADED) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (hasExtraRow() && position == getItemCount() - 1) {
            return R.layout.view_network_state_item;
        } else {
            return R.layout.view_movie_item;
        }
    }

    public void setNetworkState(NetworkState newNetworkState) {
        NetworkState previousState = this.networkState;
        boolean previousExtraRow = hasExtraRow();
        this.networkState = newNetworkState;
        boolean newExtraRow = hasExtraRow();
        if (previousExtraRow != newExtraRow) {
            if (previousExtraRow) {
                notifyItemRemoved(getItemCount());
            } else {
                notifyItemInserted(getItemCount());
            }
        } else if (newExtraRow && previousState != newNetworkState) {
            notifyItemChanged(getItemCount() - 1);
        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        private ViewMovieItemBinding bindingImpl;

        public ItemViewHolder(ViewMovieItemBinding bindingImpl) {
            super(bindingImpl.getRoot());
            this.bindingImpl = bindingImpl;
        }

        void onBind(Movie movie) {
            bindingImpl.setVariable(BR.model, movie);
            bindingImpl.executePendingBindings();
        }
    }

    static class StateViewHolder extends RecyclerView.ViewHolder {
        private ViewNetworkStateItemBinding bindingImpl;

        public StateViewHolder(ViewNetworkStateItemBinding bindingImpl) {
            super(bindingImpl.getRoot());
            this.bindingImpl = bindingImpl;
        }

        void onBind(NetworkState networkState) {
            bindingImpl.setVariable(BR.networkState, networkState);
            bindingImpl.executePendingBindings();
        }
    }
}
