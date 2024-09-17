package fr.eni.eni_shop.ui.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import fr.eni.eni_shop.datastore.DataStoreManager
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Header(navController: NavHostController, dataStoreManager: DataStoreManager) {
    var isMenuExpanded by remember {
        mutableStateOf(false)
    }

    TopAppBar(
        title = {
        AppTitle(navController)
        },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Step Back")
            }
        },
        actions = {
            IconButton(onClick = {
                isMenuExpanded = !isMenuExpanded
            }) {
                Icon(Icons.Default.Menu, contentDescription = "Menu")
            }
        }
    )
    DropdownMenu(
        expanded = isMenuExpanded,
        onDismissRequest = { isMenuExpanded = !isMenuExpanded }
    ) {
        DarkModeSwitch(dataStoreManager)
    }
}

@Composable
fun AppTitle(navController: NavHostController) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .clickable { navController.navigate("articleList") },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = "Shopping card logo"
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = "ENI-SHOP", fontSize = 45.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(26.dp))
    }
}

@Composable
fun DarkModeSwitch(dataStoreManager: DataStoreManager) {
    val isDarkModeEnabled by dataStoreManager.darkModeSettings.collectAsState(initial = false)

    val coroutineScope = rememberCoroutineScope()

    DropdownMenuItem(text = {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Dark Mode : ")
            Spacer(modifier = Modifier.height(6.dp))
            Switch(
                checked = isDarkModeEnabled,
                onCheckedChange = {
                    coroutineScope.launch {
                        dataStoreManager.saveDarkThemeSettings(!isDarkModeEnabled)
                    }
                }
            )
        }
    }, onClick = {})
}