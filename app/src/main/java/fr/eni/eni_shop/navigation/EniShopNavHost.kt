package fr.eni.eni_shop.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import fr.eni.eni_shop.ui.screen.ArticleDetails
import fr.eni.eni_shop.ui.screen.ArticleForm
import fr.eni.eni_shop.ui.screen.ArticleList

@Preview
@Composable
fun EniShopNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = "articleList"
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable("articleList") {
            val navBtnComportement = { idArticle: Long ->
                navController.navigate("articleDetails/$idArticle")
            }
            ArticleList(navBtn = navBtnComportement)
        }
        composable(
            route = "articleDetails/{idArticle}",
            arguments = listOf(navArgument("idArticle") { type = NavType.LongType })) {
                entries ->
            val idArticle: Long? = entries.arguments?.getLong("idArticle")
            if (idArticle !== null) {
                ArticleDetails(id = idArticle)
            }
        }
        composable("articleForm") {
            val navBtnComportement = {
                navController.navigate("articleList")
            }
            ArticleForm(navBtn = navBtnComportement)
        }
    }
}