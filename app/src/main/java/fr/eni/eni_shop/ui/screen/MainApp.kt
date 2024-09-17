package fr.eni.eni_shop.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import fr.eni.eni_shop.datastore.DataStoreManager
import fr.eni.eni_shop.navigation.EniShopNavHost
import fr.eni.eni_shop.navigation.FloatingBtnAddArticle
import fr.eni.eni_shop.ui.shared.Header
import fr.eni.eni_shop.ui.theme.EniShopTheme

@Composable
fun MainApp(dataStoreManager: DataStoreManager) {
    val isDarkThemeEnabled by dataStoreManager.darkModeSettings.collectAsState(initial = false)

    EniShopTheme(
        darkTheme = isDarkThemeEnabled
    ) {
        val navController = rememberNavController()

        Scaffold(
            topBar = { Header(navController = navController, dataStoreManager = dataStoreManager) },
            modifier = Modifier
                .fillMaxSize(),
            floatingActionButton = {
                FloatingBtnAddArticle(navController)
            },
            floatingActionButtonPosition = FabPosition.End
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 12.dp)
            ) {
                EniShopNavHost(navController = navController)
            }
        }
    }
}