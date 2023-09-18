package com.katilijiwoadiwiyono.imagegallerycompose.feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.katilijiwoadiwiyono.core.domain.model.ArtWorkModel
import com.katilijiwoadiwiyono.core.domain.usecase.ArtUseCase
import com.katilijiwoadiwiyono.core.utils.Resource
import com.katilijiwoadiwiyono.core.utils.ResourceState
import com.katilijiwoadiwiyono.imagegallerycompose.util.setError
import com.katilijiwoadiwiyono.imagegallerycompose.util.setValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject


interface IMainViewModel {
    val text: MutableStateFlow<String>
    val debounceText: Flow<String>
    val searchResult: StateFlow<Resource<List<ArtWorkModel>>>
    val isSearchLoading: StateFlow<Boolean>
    fun getArtwork(fetchDistance: Int, limit: Int): Flow<PagingData<ArtWorkModel>>
    fun searchArtwork(query: String, fetchDistance: Int, limit: Int)
}

class FakeMainViewModel: IMainViewModel {
    override val text: MutableStateFlow<String> = MutableStateFlow("")
    override val debounceText: Flow<String> = flow {  }
    override val searchResult:StateFlow<Resource<List<ArtWorkModel>>> = MutableStateFlow(Resource(ResourceState.Success, emptyList()))
    override val isSearchLoading: StateFlow<Boolean> = MutableStateFlow(false)

    override fun getArtwork(fetchDistance: Int, limit: Int): Flow<PagingData<ArtWorkModel>> {
        return flow {  }
    }
    override fun searchArtwork(query: String, fetchDistance: Int, limit: Int) {}
}

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: ArtUseCase
): ViewModel(), IMainViewModel {

    override val text = MutableStateFlow("")
    override val debounceText = text.debounce(1000)
        .distinctUntilChanged()
        .flatMapLatest {
            flowOf(it)
        }

    private var _searchResult = MutableStateFlow(Resource<List<ArtWorkModel>>(ResourceState.Success, emptyList()))
    override val searchResult: StateFlow<Resource<List<ArtWorkModel>>> = _searchResult

    override val isSearchLoading: StateFlow<Boolean> get() = _isLoading
    private var _isLoading = MutableStateFlow(false)

    override fun getArtwork(fetchDistance: Int, limit: Int): Flow<PagingData<ArtWorkModel>> =
        useCase.getArtwork(fetchDistance, limit)

    override fun searchArtwork(
        query: String,
        fetchDistance: Int,
        limit: Int
    ) {
        viewModelScope.launch {
            try {
                _isLoading.setValue(true)
                withContext(Dispatchers.Default) {
                    runBlocking {
                        _searchResult.setValue(useCase.searchArtwork(query, fetchDistance, limit))
                        withContext(Dispatchers.Main) {
                        _isLoading.setValue(false)
                        }
                    }
                }
            } catch (ex: Exception) {
                _isLoading.setValue(false)
                _searchResult.setError(ex.message.toString())
            }
        }
    }

}