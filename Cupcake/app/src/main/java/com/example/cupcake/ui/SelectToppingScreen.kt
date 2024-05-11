/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.cupcake.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.cupcake.R
import com.example.cupcake.ui.theme.CupcakeTheme
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController


/**
 * Composable that allows the user to select toppings for cupcakes.
 * Expects a list of topping options and provides callbacks for handling selection.
 */
@Composable
fun SelectToppingScreen(
    subtotal: String,
    navController: NavHostController,
    toppingOptions: List<String>,
    onToppingSelectionChanged: (String) -> Unit = {},
    onCancelButtonClicked: () -> Unit = {},
    onNextButtonClicked: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    CupcakeTheme {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
            Text(
                text = stringResource(id = R.string.select_toppings),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_small))
            )
            LazyColumn(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(toppingOptions.size) { index ->
                    val topping = toppingOptions[index]
                    val isSelected = remember { mutableStateListOf(false) }

                    Row(
                        modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_small))
                    ) {
                        Checkbox(
                            checked = isSelected.first(),
                            onCheckedChange = { isChecked ->
                                isSelected[0] = isChecked
                                if (isChecked) {
                                    onToppingSelectionChanged(topping)
                                }
                            },
                            modifier = Modifier.padding(end = dimensionResource(id = R.dimen.padding_small))
                        )
                        Text(
                            text = topping,
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = onCancelButtonClicked) {
                    Text(text = stringResource(id = R.string.cancel))
                }
                Button(onClick = onNextButtonClicked) {
                    Text(text = stringResource(id = R.string.next))
                }
            }
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
        }
    }
}

@Preview
@Composable
fun SelectToppingPreview() {
    val navController = rememberNavController()
    CupcakeTheme {
        val context = LocalContext.current
        SelectToppingScreen(
            subtotal = "$5.99",
            navController = navController,
            toppingOptions = listOf("Sprinkles", "Cherry", "Chocolate Chips"),
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.padding_medium))
        )
    }
}