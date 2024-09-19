package fr.eni.eni_shop.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import fr.eni.eni_shop.bo.Article
import fr.eni.eni_shop.dao.DaoType
import fr.eni.eni_shop.dao.memory.ArticleDaoMemoryImpl
import fr.eni.eni_shop.dao.network.RetrofitClient
import fr.eni.eni_shop.repository.ArticleRepository
import fr.eni.eni_shop.db.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ArticleDetailViewModel(private val articleRepository: ArticleRepository): ViewModel() {

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
                return ArticleDetailViewModel(ArticleRepository(ArticleDaoMemoryImpl.getInstance(), AppDatabase.getInstance(application.applicationContext).articleDao())) as T
            }
        }
    }

    private val _article = MutableStateFlow<Article>(Article())
    val article: StateFlow<Article> = _article

    private val _isArticleSaved = MutableStateFlow<Boolean>(false)
    val isArticleSaved: StateFlow<Boolean> = _isArticleSaved

    fun initArticle(id: Long) {

        viewModelScope.launch {
            val currentArticle = RetrofitClient.articleApiService.getArticleById(id)
            if (currentArticle != null) {
                _article.value = currentArticle
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            val savedArticle = articleRepository.getArticleById(id, DaoType.ROOM)
            if(savedArticle != null) _isArticleSaved.value = true
        }
    }

    fun checkArticleInDb(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val savedArticle = articleRepository.getArticleById(id,  DaoType.ROOM)
            _isArticleSaved.value = savedArticle != null
        }
    }

    fun saveArticleInDb(article: Article) {
        viewModelScope.launch(Dispatchers.IO) {
            var idArticle = articleRepository.saveArticle(article, DaoType.ROOM)
            checkArticleInDb(idArticle)
        }
    }

    fun deleteArticleInDb(article: Article) {
        viewModelScope.launch(Dispatchers.IO) {
            articleRepository.deleteArticle(article, DaoType.ROOM)
            checkArticleInDb(article.id)
        }
    }

}