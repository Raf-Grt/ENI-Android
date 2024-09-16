package fr.eni.eni_shop.ui.screen

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import fr.eni.eni_shop.R
import fr.eni.eni_shop.bo.Article
import fr.eni.eni_shop.viewmodel.ArticleViewModel


@Composable
fun ArticleList(
    articleViewModel: ArticleViewModel = viewModel(factory = ArticleViewModel.Factory),
    navBtn: (id: Long) -> Unit
    ) {
    val categories by articleViewModel.categories.collectAsState()
    val articles by articleViewModel.filteredArticles.collectAsState()

    Column {
        // ========== Categories elements ==========
        CategoriesFilterChips(categories = categories, articleViewModel = articleViewModel)
        Spacer(modifier = Modifier.height(26.dp))

        // ========== Articles elements ==========
        LazyVerticalGrid(
            modifier = Modifier.fillMaxWidth(),
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(articles) { ArticleCard(article = it, navBtn = navBtn) }
        }
    }
}

@Composable
fun ArticleCard(article: Article, navBtn: (id: Long) -> Unit) {
    val context = LocalContext.current
    val idArticle = article.id
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .height(200.dp)
            .border(width = 1.dp, shape = RoundedCornerShape(10.dp), color = Color.Gray)
            .padding(vertical = 10.dp, horizontal = 6.dp)
            .clickable {
                navBtn(idArticle)
            },
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = secureUrl(article.urlImage),
                contentDescription = article.name,
                placeholder = painterResource(id = R.drawable.img_not_available),
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.Gray, CircleShape),
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = article.name,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.clickable {
                val googleIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q=${article.name}"))
                context.startActivity(googleIntent)
            }
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(text = article.price.toString()+"€")
        }

    }
}


@Composable
fun CategoriesFilterChips(categories: List<String>, articleViewModel: ArticleViewModel) {
    var selectedCat by rememberSaveable {
        mutableStateOf<String?>(null)
    }

    LazyRow {
        items(categories) { category ->
            FilterChip(
                selected = selectedCat == category,
                onClick = {
                    selectedCat = if (selectedCat == category) null else category
                    if (selectedCat == category) {
                        articleViewModel.filterList(category)
                    } else {
                        articleViewModel.filterList("")
                    }
                },
                label = { Text(category) }
            )
            Spacer(modifier = Modifier.width(6.dp))
        }
    }
}

private fun secureUrl(url: String): String {
    return if (url.startsWith("http://")) {
        url.replace("http://", "https://")
    } else {
        url
    }
}