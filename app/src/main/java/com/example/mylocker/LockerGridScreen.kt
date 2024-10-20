package com.example.mylocker

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// @author Salim OUESLATI


data class Casier(val id: Int, var estDisponible: Boolean, var user: String? = null)

val casiers = List(20) { index ->
    when (index) {
        2 -> Casier(id = index + 1, estDisponible = false, user = "Alice")  // Casier 3 occupé par Alice
        7 -> Casier(id = index + 1, estDisponible = false, user = "Bob")    // Casier 8 occupé par Bob
        10 -> Casier(id = index + 1, estDisponible = false, user = "Alex")
        11 -> Casier(id = index + 1, estDisponible = false, user = "Duy")
        15 -> Casier(id = index + 1, estDisponible = false, user = "Samantha")
        16 -> Casier(id = index + 1, estDisponible = false, user = "Samantha")
        17 -> Casier(id = index + 1, estDisponible = false, user = "Samantha")

        else -> Casier(id = index + 1, estDisponible = true)  // Autres casiers disponibles
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LockerGridScreen() {
    val casiersState = remember { mutableStateListOf(*casiers.toTypedArray()) }


    var selectedLocker by remember { mutableStateOf<Casier?>(null) }
    var lockerClosed by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Gestion des Casiers") }) },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                if (!lockerClosed) {
                    LegendSection()

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        modifier = Modifier.weight(1f)
                    ) {
                        items(casiersState.size) { index ->
                            val casier = casiersState[index]
                            val context = LocalContext.current

                            LockerItem(
                                casier = casier,
                                isSelected = selectedLocker == casier,
                                onClick = {
                                    if (casier.estDisponible) {
                                        selectedLocker = casier
                                        Toast.makeText(context, "Ce casier est réservé", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            )
                        }
                    }
                }


                selectedLocker?.let { casier ->
                    if (!lockerClosed) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Button(
                                onClick = {
                                    casier.estDisponible = false
                                    lockerClosed = true
                                },
                                modifier = Modifier
                                    .padding(8.dp)
                                    .height(50.dp)
                                    .fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFFFF9800),
                                    contentColor = Color.White // Couleur du texte
                                )
                            ) {
                                Text(
                                    text = "Verrouillez le casier numéro ${casier.id}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }

                if (lockerClosed) {
                    Text(
                        text = buildAnnotatedString {
                            append("Casier Numéro ")
                            selectedLocker?.id?.let { id ->
                                withStyle(style = SpanStyle(color = Color(0xFFFF9800))) {
                                    append("$id")
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        textAlign = TextAlign.Center,
                        fontSize = 35.sp,
                        color = Color.Blue
                    )
                    Text(
                        text = "Le casier ${selectedLocker?.id} a été verrouillé avec succès !",
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        textAlign = TextAlign.Center,
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Button(
                            onClick = {
                                selectedLocker?.estDisponible = true
                                lockerClosed = false
                                selectedLocker = null
                            },
                            modifier = Modifier
                                .padding(8.dp)
                                .height(50.dp)
                                .fillMaxWidth(0.6f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF4CAF50),
                                contentColor = Color.White
                            )
                        ) {
                            Text(
                                text = "Déverrouiller mon casier",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White,
                                fontSize = 15.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Cliquez sur le bouton pour déverrouiller le casier et revenir à la grille des casiers.",
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    )
}

@Composable
fun LegendSection() {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .background(Color(0xFF4CAF50), shape = CircleShape) // Vert
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Casier disponible")
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .background(Color.Red, shape = CircleShape) // Rouge
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Casier occupé")
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .background(Color(0xFFFF9800), shape = CircleShape) // Orange
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Casier sélectionné")
        }
    }
}


@Composable
fun LockerItem(casier: Casier, isSelected: Boolean, onClick: () -> Unit) {
    val backgroundColor = when {
        isSelected -> Color(0xFFFF9800)
        casier.estDisponible -> Color(0xFF4CAF50)
        else -> Color.Red
    }

    Card(
        modifier = Modifier
            .padding(8.dp)
            .size(100.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = if (isSelected) CardDefaults.elevatedCardElevation(8.dp) else CardDefaults.elevatedCardElevation(2.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Casier ${casier.id}", color = Color.White)
        }
    }

}



