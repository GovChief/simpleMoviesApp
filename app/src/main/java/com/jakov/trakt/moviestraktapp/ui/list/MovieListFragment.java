package com.jakov.trakt.moviestraktapp.ui.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jakov.trakt.moviestraktapp.R;
import com.jakov.trakt.moviestraktapp.databinding.FragmentMoviesListBinding;
import com.jakov.trakt.moviestraktapp.shared.OnTextChangeListener;
import com.jakov.trakt.moviestraktapp.ui.base.BaseFragment;
import com.jakov.trakt.moviestraktapp.ui.base.BaseViewModel;
import com.jakov.trakt.moviestraktapp.ui.details.DetailsScreen;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MovieListFragment extends BaseFragment<MovieListState> {

    MovieListViewModel viewModel;
    private FragmentMoviesListBinding binding;
    private MovieListAdapter adapter;

    @Override
    public BaseViewModel<MovieListState> provideViewModel() {
        return viewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(MovieListViewModel.class);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
        @NonNull LayoutInflater inflater,
        ViewGroup container,
        Bundle savedInstanceState
    ) {
        binding = FragmentMoviesListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new MovieListAdapter(viewModel::loadNextPage, movie -> {
            Bundle bundle = new Bundle();
            bundle.putInt(DetailsScreen.ID_EXTRA, movie.id);
            Navigation.findNavController(view).navigate(R.id.action_MovieListFragment_to_DetailsFragment, bundle);
        });
        binding.moviesRecyclerView.setAdapter(adapter);

        binding.fab.setOnClickListener(view1 -> {
            adapter.clearItems();
            viewModel.refresh();
        });

        binding.textInputLayout.getEditText()
            .addTextChangedListener((OnTextChangeListener) (text) -> viewModel.setSearchText(text));
    }

    @Override
    public void handleState(MovieListState movieListState) {
        if (movieListState instanceof MovieListState.Loading) {
            binding.loadingBar.setVisibility(View.VISIBLE);
            binding.moviesRecyclerView.setVisibility(View.GONE);
            binding.infoMessage.setVisibility(View.GONE);
        } else if (movieListState instanceof MovieListState.Loaded) {
            MovieListState.Loaded listState = (MovieListState.Loaded) movieListState;

            binding.loadingBar.setVisibility(View.GONE);
            binding.infoMessage.setVisibility(View.GONE);
            binding.moviesRecyclerView.setVisibility(View.VISIBLE);
            adapter.setItems(listState.movies);
        } else if (movieListState instanceof MovieListState.UnkownError) {
            binding.loadingBar.setVisibility(View.GONE);
            binding.moviesRecyclerView.setVisibility(View.GONE);
            binding.infoMessage.setVisibility(View.VISIBLE);

            binding.infoMessage.setText(R.string.error_occured_please_refresh_the_screen);
        } else if (movieListState instanceof  MovieListState.Empty) {
            binding.loadingBar.setVisibility(View.GONE);
            binding.moviesRecyclerView.setVisibility(View.GONE);
            binding.infoMessage.setVisibility(View.VISIBLE);

            binding.infoMessage.setText(R.string.empty_search);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}