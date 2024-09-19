package fr.eni.eni_shop.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import fr.eni.eni_shop.R
import fr.eni.eni_shop.viewmodel.ArticleDetailViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun ArticleDetails(id: Long, articleViewModel: ArticleDetailViewModel = viewModel(factory = ArticleDetailViewModel.Factory)) {
    LaunchedEffect(id) {
        articleViewModel.initArticle(id)
    }

    val article by articleViewModel.article.collectAsState()

    if (article == null) return

    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val formattedDate = dateFormat.format(article.date)

    val isArticleSaved by articleViewModel.isArticleSaved.collectAsState()

    Column {
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = article.title, fontWeight = FontWeight.Bold, fontSize = 20.sp)

        Spacer(modifier = Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            AsyncImage(
                model = article.image,
                contentDescription = article.title,
                placeholder = painterResource(id = R.drawable.img_not_available),
                modifier = Modifier.size(200.dp)
            )
        }
        Spacer(modifier = Modifier.height(35.dp))
        Text(text = article.description)
        Spacer(modifier = Modifier.height(35.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Prix : ${article.price}", modifier = Modifier.testTag("ARTICLE_PRICE_TAG"))
            Text(text = "Date de sortie : ${formattedDate}")
        }
        Spacer(modifier = Modifier.height(26.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Favoris")
            Checkbox(checked = isArticleSaved, onCheckedChange = { it ->
                if (it) {
                    articleViewModel.saveArticleInDb(article)
                } else {
                    articleViewModel.deleteArticleInDb(article)
                }

            })
        }
    }
}