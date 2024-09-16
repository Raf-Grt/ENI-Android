package fr.eni.eni_shop

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import fr.eni.eni_shop.repository.ArticleRepository
import fr.eni.eni_shop.ui.screen.ArticleDetails
import fr.eni.eni_shop.viewmodel.ArticleViewModel
import org.junit.Rule
import org.junit.Test

class ArticleDetailsTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun articlePriceTest() {
        composeTestRule.setContent {
            ArticleDetails(id = 1)
        }

        composeTestRule.onNodeWithTag("ARTICLE_PRICE_TAG")
            .assertIsDisplayed()
            .assertTextContains("109.95", substring = true)
    }
}