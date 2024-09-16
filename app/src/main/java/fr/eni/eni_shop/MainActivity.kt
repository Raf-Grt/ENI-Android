package fr.eni.eni_shop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import fr.eni.eni_shop.navigation.EniShopNavHost
import fr.eni.eni_shop.navigation.FloatingBtnAddArticle
import fr.eni.eni_shop.ui.screen.ArticleDetails
import fr.eni.eni_shop.ui.screen.ArticleForm
import fr.eni.eni_shop.ui.screen.ArticleList
import fr.eni.eni_shop.ui.shared.Header
import fr.eni.eni_shop.ui.theme.EniShopTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EniShopTheme {
                val navController = rememberNavController()

                Scaffold(
                    topBar = { Header(navController) },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 10.dp),
                    floatingActionButton = {
                        FloatingBtnAddArticle(navController)
                    },
                    floatingActionButtonPosition = FabPosition.End
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                    ) {
                        EniShopNavHost(navController = navController)
                    }
                }
            }
        }
    }
}
