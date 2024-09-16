package fr.eni.eni_shop.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import fr.eni.eni_shop.bo.Article
import fr.eni.eni_shop.repository.ArticleRepository
import kotlinx.coroutines.flow.MutableStateFlow

class ArticleViewModel(articleRepository: ArticleRepository): ViewModel() {

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
                return ArticleViewModel(ArticleRepository()) as T
            }
        }
    }

    private val _categories = MutableStateFlow<List<String>>(emptyList())
    val categories = _categories

    private val _articles = MutableStateFlow<List<Article>>(emptyList())

    private val _filteredArticles = MutableStateFlow<List<Article>>(emptyList())
    val filteredArticles = _filteredArticles

    private val _currentArticle = MutableStateFlow<Article>(Article())
    val currentArticle = _currentArticle

    init {
        _categories.value = listOf("electronics", "men's clothing","women's clothing", "Other")
        _articles.value = articleRepository.getAllArticles()
        _filteredArticles.value = _articles.value
    }

    fun filterList(filter: String) {
        if (filter.length > 0) {
            _filteredArticles.value = _articles.value.filter { article: Article -> article.category == filter }
        } else {
            _filteredArticles.value = _articles.value
        }
    }

    fun getArticleById(id: Long) {
        val article = this._articles.value.find { article -> article.id === id }
        if(article == null) return
        this._currentArticle.value = article
    }

}