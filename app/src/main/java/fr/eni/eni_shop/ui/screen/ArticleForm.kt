package fr.eni.eni_shop.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import fr.eni.eni_shop.bo.Article
import fr.eni.eni_shop.viewmodel.ArticleListViewModel

@Composable
fun ArticleForm(
    articleViewModel: ArticleListViewModel = viewModel(factory = ArticleListViewModel.Factory),
    navBtn: () -> Unit
) {
    var title by rememberSaveable {
        mutableStateOf("")
    }

    var description by rememberSaveable {
        mutableStateOf("")
    }

    var price by rememberSaveable {
        mutableStateOf("")
    }

    var image by rememberSaveable {
        mutableStateOf("")
    }

    var category by rememberSaveable {
        mutableStateOf("")
    }

    val categories by articleViewModel.categories.collectAsState(initial = emptyList())

    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10. dp)
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = title,
            onValueChange = {title = it},
            label = {
                Text(text = "Titre")
            }
        )

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = description,
            onValueChange = {description = it},
            label = {
                Text(text = "Description")
            }
        )

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = price,
            onValueChange = {price = it},
            label = {
                Text(text = "Prix")
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = image,
            onValueChange = {image = it},
            label = {
                Text(text = "Image")
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )
        
        DynamicSelectTextField(
            selectedValue = category,
            options = categories,
            label = "Choissir une catgorie",
            onValueChangedEvent = { category = it }
        )
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = {
                articleViewModel.createNewArticle(Article(
                    id = 0L,
                    title = title,
                    description = description,
                    price = price.toFloat(),
                    image = image,
                    category = category
                ))
                navBtn()
            }) {
                Text(text = "Enregistrer")
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
@Composable
fun DynamicSelectTextField(
    selectedValue: String,
    options: List<String>,
    label: String,
    onValueChangedEvent: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            readOnly = true,
            value = selectedValue,
            onValueChange = {},
            label = { Text(text = label) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = OutlinedTextFieldDefaults.colors(),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { option: String ->
                DropdownMenuItem(
                    text = { Text(text = option) },
                    onClick = {
                        expanded = false
                        onValueChangedEvent(option)
                    }
                )
            }
        }
    }
}
