package com.example.inventory.ui.category

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.inventory.data.Category
import com.example.inventory.data.ItemsRepository

class CategoryEntryViewModel(private val categoriesRepository: ItemsRepository) : ViewModel() {

    /**
     * Holds current item ui state
     */
    var categoryUiState by mutableStateOf(CategoryUiState())
        private set

}

/**
 * Represents Ui State for an Category.
 */
data class CategoryUiState(
    val categoryDetails: CategoryDetails = CategoryDetails(),
    val isEntryValid: Boolean = false
)

data class CategoryDetails(
    val id: Int = 0,
    val name: String = ""
)

/**
 * Extension function to convert [CategoryDetails] to [Category]
 */
fun CategoryDetails.toItem(): Category = Category(
    id = id,
    name = name
)


/**
 * Extension function to convert [Category] to [CategoryUiState]
 */
fun Category.toItemUiState(isEntryValid: Boolean = false): CategoryUiState = CategoryUiState(
    categoryDetails = this.toCategoryDetails(),
    isEntryValid = isEntryValid
)

/**
 * Extension function to convert [Category] to [CategoryDetails]
 */
fun Category.toCategoryDetails(): CategoryDetails = CategoryDetails(
    id = id,
    name = name
)