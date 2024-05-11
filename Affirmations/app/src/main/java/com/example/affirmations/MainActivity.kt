package com.example.affirmations

//import Datasource
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.affirmations.model.Affirmation
import com.example.affirmations.ui.theme.AffirmationsTheme
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.affirmations.data.Datasource


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AffirmationsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainPage()
                }
            }
        }
    }


}

@Composable
fun MainPage() {
    val affirmations = Datasource().loadAffirmations()
    affirmationList(affirmationList = affirmations)
}
@Composable
private fun AffirmationCard(
    affirmation: Affirmation,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val context = LocalContext.current

    Card(modifier = modifier.clickable { onClick() }

    ) {
        Column {
            Image(
                painter = painterResource(affirmation.imageResourceId),
                contentDescription = stringResource(affirmation.stringResourceId),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(194.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = LocalContext.current.getString(affirmation.stringResourceId),
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.headlineSmall,

                )


        }
    }
}

@Composable
fun affirmationList(affirmationList: List<Affirmation>, modifier: Modifier = Modifier) {

    val context = LocalContext.current
    val (showDialog, setShowDialog) = remember { mutableStateOf(false) }
    val (selectedAffirmation, setSelectedAffirmation) = remember { mutableStateOf<Affirmation?>(null) }


    LazyColumn(modifier = modifier) {
        items(affirmationList) { affirmation ->
            AffirmationCard(
                affirmation = affirmation,
                modifier = Modifier.padding(8.dp),

                onClick = {
                    Toast.makeText(context, context.getString(affirmation.stringResourceId), Toast.LENGTH_LONG).show()
                    setSelectedAffirmation(affirmation)
                    setShowDialog(true)
                }
            )
        }
    }

    if (showDialog && selectedAffirmation != null) {
        AffirmationDialog(
            affirmation = selectedAffirmation,
            onDismissRequest = {
                setShowDialog(false)
                setSelectedAffirmation(null)
            }
        )
    }
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
//                Text(
//                    text = context.getString(affirmation.stringResourceId),
//                    style = MaterialTheme.typography.headlineSmall
//                )
    }
}



@Composable
fun AffirmationDialog(
    affirmation: Affirmation?,
    onDismissRequest: () -> Unit
) {
    val context = LocalContext.current

    affirmation?.let {
        Dialog(
            onDismissRequest = onDismissRequest,
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            )
        )
        {

            Surface(
                color = Color.White,
                modifier = Modifier.padding(top = 16.dp),
                shape = RoundedCornerShape(16.dp),
            )
            {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(affirmation.imageResourceId),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(194.dp),
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        text = context.getString(affirmation.stringResourceId),
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .background(
                                Color.White,
                                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                            )
                    )
                }
            }
        }
    }
}



@Preview
@Composable
private fun AffirmationListPreview() {
    AffirmationsTheme {
        affirmationList(
            affirmationList = Datasource().loadAffirmations(),
        )
    }
}
