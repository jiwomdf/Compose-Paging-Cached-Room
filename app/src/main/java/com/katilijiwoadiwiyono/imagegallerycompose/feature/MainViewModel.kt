package com.katilijiwoadiwiyono.imagegallerycompose.feature

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.katilijiwoadiwiyono.core.domain.model.ArtWorkModel
import com.katilijiwoadiwiyono.core.domain.usecase.ArtUseCase
import com.katilijiwoadiwiyono.core.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
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
    fun getArtwork(fetchDistance: Int, limit: Int): Flow<PagingData<ArtWorkModel>>
    fun searchArtwork(query: String, fetchDistance: Int, limit: Int)
}

class FakeMainViewModel : IMainViewModel {
    override val text: MutableStateFlow<String> = MutableStateFlow("")
    override val debounceText: Flow<String> = flow {  }
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

    val searchResult = mutableStateOf<Resource<List<ArtWorkModel>>>(Resource.Loading(emptyList()))

    override fun getArtwork(fetchDistance: Int, limit: Int): Flow<PagingData<ArtWorkModel>> {
        Log.e("jiwomdf", "getArtwork: ")
        return useCase.getArtwork(fetchDistance, limit)
    }

    override fun searchArtwork(
        query: String,
        fetchDistance: Int,
        limit: Int
    ) {
        Log.e("jiwomdf", "searchArtwork: $query")
        viewModelScope.launch {
            searchResult.value = useCase.searchArtwork(query, fetchDistance, limit)
        }
    }

}