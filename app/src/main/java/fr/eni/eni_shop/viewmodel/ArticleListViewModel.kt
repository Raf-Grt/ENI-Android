package fr.eni.eni_shop.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import fr.eni.eni_shop.bo.Article
import fr.eni.eni_shop.dao.memory.ArticleDaoMemoryImpl
import fr.eni.eni_shop.dao.network.RetrofitClient
import fr.eni.eni_shop.repository.ArticleRepository
import fr.eni.eni_shop.db.AppDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ArticleListViewModel(articleRepository: ArticleRepository): ViewModel() {

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
                return ArticleListViewModel(ArticleRepository(ArticleDaoMemoryImpl.getInstance(), AppDatabase.getInstance(application.applicationContext).articleDao())) as T
            }
        }
    }

    private val _categories = MutableStateFlow<List<String>>(emptyList())
    val categories = _categories

    private val _articles = MutableStateFlow<List<Article>>(emptyList())

    private val _filteredArticles = MutableStateFlow<List<Article>>(emptyList())
    val filteredArticles = _filteredArticles

    init {
        //_categories.value = listOf("electronics", "men's clothing","women's clothing", "Other")
        // _articles.value = articleRepository.getAllArticles()
        viewModelScope.launch {
            _categories.value = RetrofitClient.articleApiService.getAllCategories()
        }
        fetchArticles()
    }

    fun filterList(filter: String) {
        if (filter.length > 0) {
            _filteredArticles.value = _articles.value.filter { article: Article -> article.category == filter }
        } else {
            _filteredArticles.value = _articles.value
        }
    }

    fun fetchArticles() {
        viewModelScope.launch {
            val response = RetrofitClient.articleApiService.getArticles()
            _articles.value = response
            _filteredArticles.value = _articles.value
        }
    }

    fun createNewArticle(article: Article) {
        viewModelScope.launch {
            val result = RetrofitClient.articleApiService.createArticle(article)
            val newList = _articles.value + result
            _articles.value = newList
            _filteredArticles.value = _articles.value
        }
    }

}
