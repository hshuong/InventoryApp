package com.example.inventory.ui.item

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventory.data.Item
import com.example.inventory.data.ItemsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class ItemListViewModel(
    savedStateHandle: SavedStateHandle,
    itemsRepository: ItemsRepository
) : ViewModel() {

    private val categoryId: Int = checkNotNull(savedStateHandle[ItemListDestination.categoryIdArg])
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
//    val itemListUiState: StateFlow<ItemListUiState> =
//        itemsRepository.getAllItemsStream().map { ItemListUiState(it) }
//            .stateIn(
//                scope = viewModelScope,
//                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
//                initialValue = ItemListUiState()
//            )
    val itemListUiState: StateFlow<ItemListUiState> =
        itemsRepository.getItemsByCategoryStream(categoryId).map { ItemListUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = ItemListUiState()
            )
}


/**
 * Ui State for ItemListScreen
 */
data class ItemListUiState(val itemList: List<Item> = listOf())