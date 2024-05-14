package com.example.dessertclicker.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dessertclicker.R
import com.example.dessertclicker.data.Datasource
import com.example.dessertclicker.data.DessertUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DessertClickerViewModel: ViewModel() {

    private val desserts: List<Dessert> = Datasource.dessertList

    private val _dessertClickerUiState = MutableStateFlow(
        DessertUiState(
            revenue = 0,
            dessertsSold = 0,
            currentDessertImageId = R.drawable.cupcake,
            currentDessertIndex = 0,
            currentDessertPrice = 0
        )
    )
    val dessertClickerUIState: StateFlow<DessertUiState> = _dessertClickerUiState

    fun onDessertClicked() {
        viewModelScope.launch {
            val currentState = _dessertClickerUiState.value

            val uiUpdate = currentState.copy(
                revenue = currentState.revenue + currentState.currentDessertPrice,
                dessertsSold = currentState.dessertsSold + 1
            )

            val dessertToShow = determineDessertToShow(desserts, uiUpdate.dessertsSold)

            _dessertClickerUiState.value = uiUpdate.copy(
                currentDessertImageId = dessertToShow.imageId,
                currentDessertPrice = dessertToShow.price
            )
        }

    }
    fun determineDessertToShow(
        desserts: List<Dessert>,
        dessertsSold: Int
    ): Dessert {
        var dessertToShow = desserts.first()
        for (dessert in desserts) {
            if (dessertsSold >= dessert.startProductionAmount) {
                dessertToShow = dessert
            } else {
                // The list of desserts is sorted by startProductionAmount. As you sell more desserts,
                // you'll start producing more expensive desserts as determined by startProductionAmount
                // We know to break as soon as we see a dessert who's "startProductionAmount" is greater
                // than the amount sold.
                break
            }
        }

        return dessertToShow
    }

}
