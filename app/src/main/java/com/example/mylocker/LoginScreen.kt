package com.example.mylocker

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

//@author Salim OUESLATI

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit, onNavigateToRegister: () -> Unit) {
    var userId by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.my_locker_logo), // Remplacez par le nom de votre image
            contentDescription = "App Icon Logo",
            modifier = Modifier
                .size(210.dp) // Ajuste la taille de l'image
                .padding(bottom = 16.dp) // Espace en bas de l'image
                .clip(RoundedCornerShape(16))
        )

        Text(text = "Connexion", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = userId,
            onValueChange = { userId = it },
            label = { Text("User ID") },
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),

                    visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (userId == "test" && password == "1234") {
                    onLoginSuccess()
                } else {
                    showError = true
                }
            },
            modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF9800), // Utilisation de la couleur orange
            contentColor = Color.White
        )
        ) {
            Text(text = "Se connecter")
        }

        if (showError) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "User ID ou mot de passe incorrect", color = MaterialTheme.colorScheme.error)
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Pas encore de compte ? Inscrivez-vous",
            modifier = Modifier.clickable {
                onNavigateToRegister() // Appel de la fonction pour naviguer vers RegisterScreen
            },
            style = MaterialTheme.typography.bodyMedium.copy(color = Color.Blue)
        )
    }
}

