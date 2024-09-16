package fr.eni.eni_shop.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun FloatingBtnAddArticle(navController: NavHostController) {
    FloatingActionButton(
        onClick = { navController.navigate("articleForm") }
    ) {
        Icon(imageVector = Icons.Default.Add, contentDescription = "Add article button")
    }
}