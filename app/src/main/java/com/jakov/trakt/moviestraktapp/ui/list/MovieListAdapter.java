package com.jakov.trakt.moviestraktapp.ui.list;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jakov.trakt.moviestraktapp.R;
import com.jakov.trakt.moviestraktapp.data.ui_model.UiMovie;
import com.jakov.trakt.moviestraktapp.databinding.ItemMovieBinding;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieListViewHolder> {

    private static final int LOAD_NEXT_PAGE_ITEMS_OFFSET = 4;

    private final LoadMoreItemsListener loadMoreItemsListener;
    private final OnMovieClickListener onClickListener;
    private List<UiMovie> items = new ArrayList<>();

    public MovieListAdapter(LoadMoreItemsListener loadMoreItemsListener, OnMovieClickListener onClickListener) {
        this.loadMoreItemsListener = loadMoreItemsListener;
        this.onClickListener = onClickListener;
    }

    public void setItems(List<UiMovie> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void clearItems() {
        this.items.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMovieBinding binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MovieListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieListViewHolder holder, int position) {
        holder.bindViewHolder(items.get(position), onClickListener);
        if (getItemCount() - position <= LOAD_NEXT_PAGE_ITEMS_OFFSET) {
            loadMoreItemsListener.onLoadNext();
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class MovieListViewHolder extends RecyclerView.ViewHolder {

        private final ItemMovieBinding binding;

        public MovieListViewHolder(@NonNull ItemMovieBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindViewHolder(UiMovie movie, OnMovieClickListener onClickListener) {
            binding.movieTitle.setText(movie.title);
            binding.getRoot().setOnClickListener(view -> onClickListener.onMoveClicked(movie));
            Glide.with(binding.getRoot()).setDefaultRequestOptions(RequestOptions.timeoutOf(5)).load(movie.imageCover)
                .placeholder(R.drawable.ic_cinema).error(R.drawable.ic_cinema).into(binding.movieCover);
        }
    }

    interface LoadMoreItemsListener {

        void onLoadNext();
    }

    interface OnMovieClickListener {

        void onMoveClicked(UiMovie movie);
    }
}
