package com.example.moviedb.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedb.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _query.debounce(500)
                .distinctUntilChanged()
                .collectLatest { searchTerm ->
                    if (searchTerm.isNotBlank()) {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                        repository.searchMovies(searchTerm).fold(
                            onSuccess = { response ->
                                _uiState.value = HomeUiState(
                                    movies = response.results,
                                    isLoading = false
                                )
                            },
                            onFailure = { e ->
                                _uiState.value = HomeUiState(
                                    error = e.message,
                                    isLoading = false
                                )

                            }
                        )
                    } else {
                        _uiState.value = HomeUiState()
                    }

                }
        }
    }

    fun onQueryChange(newQuery: String) {
        _query.value = newQuery
    }

}