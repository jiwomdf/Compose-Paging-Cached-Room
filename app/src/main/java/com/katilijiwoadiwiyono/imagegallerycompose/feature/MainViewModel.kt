package com.katilijiwoadiwiyono.imagegallerycompose.feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.katilijiwoadiwiyono.core.domain.model.ArtWorkModel
import com.katilijiwoadiwiyono.core.domain.usecase.ArtUseCase
import com.katilijiwoadiwiyono.core.utils.Resource
import com.katilijiwoadiwiyono.core.utils.ResourceState
import com.katilijiwoadiwiyono.imagegallerycompose.util.setFailed
import com.katilijiwoadiwiyono.imagegallerycompose.util.setSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
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
import javax.inject.Inject


interface IMainViewModel {
    val text: MutableStateFlow<String>
    val debounceText: Flow<String>
    val searchResult: StateFlow<Resource<List<ArtWorkModel>>>
    fun getArtwork(fetchDistance: Int, limit: Int): Flow<PagingData<ArtWorkModel>>
    fun searchArtwork(query: String, fetchDistance: Int, limit: Int)
}

class FakeMainViewModel: IMainViewModel {
    override val text: MutableStateFlow<String> = MutableStateFlow("")
    override val debounceText: Flow<String> = flow {  }
    override val searchResult:StateFlow<Resource<List<ArtWorkModel>>> = MutableStateFlow(Resource(ResourceState.Success, emptyList()))
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
    override val debounceText = text.debounce(2000)
        .distinctUntilChanged()
        .flatMapLatest {
            flowOf(it)
        }

    private var _searchResult = MutableStateFlow(Resource<List<ArtWorkModel>>(ResourceState.Success, emptyList()))
    override val searchResult: StateFlow<Resource<List<ArtWorkModel>>> = _searchResult

    override fun getArtwork(fetchDistance: Int, limit: Int): Flow<PagingData<ArtWorkModel>> {
        return useCase.getArtwork(fetchDistance, limit)
    }

    override fun searchArtwork(
        query: String,
        fetchDistance: Int,
        limit: Int
    ) {
        viewModelScope.launch {
            try {
                _searchResult.setSuccess(useCase.searchArtwork(query, fetchDistance, limit))
            } catch (ex: Exception) {
                _searchResult.setFailed(ex.message.toString())
            }
        }
    }

}