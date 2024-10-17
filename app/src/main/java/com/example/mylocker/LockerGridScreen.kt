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

    // Variable d'état pour le casier sélectionné
    var selectedLocker by remember { mutableStateOf<Casier?>(null) }
    var lockerClosed by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Gestion des Casiers") }) },
        content = { paddingValues -> // Ajout du padding pour éviter que le contenu soit caché par la TopAppBar
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)  // Applique le padding pour éviter que le contenu chevauche la barre supérieure
            ) {
                // Si aucun casier n'est fermé, afficher la grille des casiers
                if (!lockerClosed) {
                    // Affichage de la légende avant le LazyVerticalGrid
                    LegendSection()

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        modifier = Modifier.weight(1f)  // Remplit l'espace vertical disponible
                    ) {
                        items(casiersState.size) { index ->
                            val casier = casiersState[index]
                            val context = LocalContext.current

                            LockerItem(
                                casier = casier,
                                isSelected = selectedLocker == casier, // Vérifie si le casier est sélectionné
                                onClick = {
                                    if (casier.estDisponible) {
                                        selectedLocker = casier  // Sélectionner le casier
                                    } else {
                                        Toast.makeText(context, "Ce casier est réservé", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            )
                        }
                    }
                }

                // Si un casier est sélectionné mais non encore fermé, afficher le bouton pour le fermer
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
                                    casier.estDisponible = false  // Simuler la fermeture du casier
                                    lockerClosed = true
                                },
                                modifier = Modifier
                                    .padding(8.dp)
                                    .height(50.dp)
                                    .fillMaxWidth(), // Limiter la largeur à 60% de l'écran pour un look plus élégant
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFFFF9800), // Utilisation de la couleur orange
                                    contentColor = Color.White // Couleur du texte
                                )
                            ) {
                                Text(
                                    text = "Verrouillez le casier numéro ${casier.id}",
                                    style = MaterialTheme.typography.bodyMedium, // Style de texte moderne
                                    color = Color.White
                                )
                            }
                        }
                    }
                }

                // Si le casier a été fermé, afficher un message de confirmation et le bouton pour ouvrir le casier
                if (lockerClosed) {
                    Text(
                        text = buildAnnotatedString {
                            append("Casier Numéro ")
                            selectedLocker?.id?.let { id ->
                                withStyle(style = SpanStyle(color = Color(0xFFFF9800))) { // Couleur orange
                                    append("$id")
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        textAlign = TextAlign.Center,
                        fontSize = 35.sp,
                        color = Color.Blue // Couleur par défaut pour le reste du texte
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
                                selectedLocker?.estDisponible = true  // Simuler l'ouverture du casier
                                lockerClosed = false  // Réinitialiser l'état après ouverture
                                selectedLocker = null  // Désélectionner le casier
                            },
                            modifier = Modifier
                                .padding(8.dp)
                                .height(50.dp)
                                .fillMaxWidth(0.6f), // Limiter la largeur à 60% de l'écran
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF4CAF50), // Couleur verte
                                contentColor = Color.White // Couleur du texte
                            )
                        ) {
                            Text(
                                text = "Déverrouiller mon casier",
                                style = MaterialTheme.typography.bodyMedium, // Style de texte moderne
                                color = Color.White,
                                fontSize = 15.sp
                            )
                        }
                    }

                    // Message indiquant que le casier est ouvert et rediriger vers la grille
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
        // Légende pour les couleurs des casiers
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
    // Choisir la couleur de fond en fonction de l'état du casier
    val backgroundColor = when {
        isSelected -> Color(0xFFFF9800) // Couleur pour le casier sélectionné
        casier.estDisponible -> Color(0xFF4CAF50)// Couleur pour le casier disponible
        else -> Color.Red// Couleur pour le casier occupé
    }

    // Utiliser Card pour le casier
    Card(
        modifier = Modifier
            .padding(8.dp)
            .size(100.dp)
            .clickable { onClick() }, // Action quand on clique sur le casier
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = if (isSelected) CardDefaults.elevatedCardElevation(8.dp) else CardDefaults.elevatedCardElevation(2.dp) // Élévation conditionnelle
    ) {
        Box(
            modifier = Modifier.fillMaxSize(), // Remplit tout l'espace du Card
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Casier ${casier.id}", color = Color.White) // Amélioration de la visibilité du texte
        }
    }

}



