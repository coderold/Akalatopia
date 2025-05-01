package com.example.aklatopia.home.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.aklatopia.ui.theme.Beige
import com.example.aklatopia.ui.theme.DarkBlue
import com.example.aklatopia.ui.theme.Green
import com.example.aklatopia.ui.theme.OffWhite
import com.example.aklatopia.ui.theme.Yellow
import kotlinx.coroutines.delay
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.draw.alpha
import com.example.aklatopia.assets.BeigeBackButton
import com.example.aklatopia.data.BookCategory
import com.example.aklatopia.assets.ExtraBoldText
import com.example.aklatopia.assets.ImageCard
import com.example.aklatopia.assets.Line
import com.example.aklatopia.R
import com.example.aklatopia.WindowInfo
import com.example.aklatopia.data.books
import com.example.aklatopia.list.components.ListBookCard
import com.example.aklatopia.rememberWindowInfo
import kotlinx.coroutines.launch

@Composable
fun Search(navHostController: NavHostController){
    var searchText by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    var selectedCategory by remember { mutableStateOf<BookCategory?>(null) }
    val windowInfo = rememberWindowInfo()
    val isScreenRotated = windowInfo.screenWidthInfo is WindowInfo.WindowType.Medium

    Scaffold(
        topBar = {
            Column {
                Box(
                    modifier = Modifier
                        .background(Beige)
                        .clip(RoundedCornerShape(bottomEnd = 10.dp, bottomStart = 10.dp))
                        .background(DarkBlue)
                        .fillMaxWidth()
                        .fillMaxHeight(0.23f)
                )
                {
                    Text(
                        text = "Aklatopia",
                        fontFamily = FontFamily(Font(R.font.poppins_extrabold)),
                        color = Beige,
                        fontSize = 36.sp,
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(top = 20.dp, bottom = 10.dp)
                            .alpha(if (isScreenRotated) 0f else 1f)
                    )
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                    ) {
                        BeigeBackButton(
                            navHostController,
                            modifier = Modifier
                                .padding(5.dp)
                                .align(Alignment.CenterStart)
                        )
                        CustomShapeSearchBar(
                            modifier = Modifier
                                .padding(start = 35.dp)
                                .align(Alignment.CenterEnd),
                            text = searchText,
                            onTextChange = { searchText = it },
                            isScreenRotated
                        )
                    }
                }

                if (!isScreenRotated){
                    ExtraBoldText(
                        text = "Categories",
                        size = 24.sp,
                        color = DarkBlue,
                        modifier = Modifier.padding(horizontal = 20.dp)
                    )
                }

                CategoriesRow(
                    selectedCategory = selectedCategory,
                    onCategorySelected = { category ->
                        coroutineScope.launch {
                            selectedCategory = if (selectedCategory == category) null else category
                        }
                    }
                )
                Line()
            }
        }
    ){
            padding ->
        SearchBookCard(navHostController, searchText, selectedCategory, padding)
    }
}

@Composable
fun CategoryBtn(
    category: BookCategory,
    isSelected: Boolean,
    onCategorySelected: (BookCategory) -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = { onCategorySelected(category) },
        modifier = modifier
            .height(40.dp)
            .widthIn(min = 100.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Green else OffWhite,
            contentColor = if (isSelected) Beige else DarkBlue
        )
    ) {
        Text(
            text = category.displayName,
            fontSize = 14.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontFamily = FontFamily(Font(R.font.poppins_semibold))
        )
    }
}

@Composable
fun CustomShapeSearchBar(
    modifier: Modifier,
    text: String,
    onTextChange: (String) -> Unit,
    isScreenRotated: Boolean
) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        value = text,
        onValueChange = onTextChange,
        placeholder = { Text(
            text = "Search...",
            fontFamily = FontFamily(Font(R.font.poppins_semibold)),
            color = DarkBlue,
            fontSize = if (isScreenRotated) 12.sp else 14.sp,
        ) },
        trailingIcon = { Icon(
            Icons.Default.Search,
            contentDescription = "Search",
            tint = DarkBlue
        ) },
        modifier = modifier.then(
            Modifier
                .padding(if (isScreenRotated) 10.dp else 20.dp)
                .fillMaxWidth()
                .heightIn(min = 56.dp)
                .clip(RoundedCornerShape(30.dp))
                .background(Color.LightGray.copy(alpha = 0.2f))
                .focusRequester(focusRequester)
                .onFocusChanged {focusState ->
                    if (focusState.isFocused) {
                        keyboardController?.show()
                    }
                }
        ),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = OffWhite,
            unfocusedTextColor = DarkBlue,
            focusedContainerColor = OffWhite,
            focusedLabelColor = Green,
            focusedIndicatorColor = Yellow,
            cursorColor = DarkBlue
        ),
        singleLine = true,
        textStyle = TextStyle(
            color = DarkBlue,
            fontSize = if (isScreenRotated) 12.sp else 14.sp,
            fontFamily = FontFamily(Font(R.font.poppins_medium)),
            textAlign = TextAlign.Start,
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search,
            keyboardType = KeyboardType.Text
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                keyboardController?.hide()
            }
        )
    )

    LaunchedEffect(Unit) {
        delay(100)
        focusRequester.requestFocus()
    }
}

@Composable
fun SearchBookGrid(navHostController: NavHostController, title: String){
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ){
        val filteredBooks = books.filter { it.title.contains(title, ignoreCase = true) }
        items(filteredBooks){ book->
            ImageCard(
                pic = book.cover,
                desc = book.desc,
                title = book.title,
                navHostController = navHostController
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchBookCard(
    navHostController: NavHostController,
    title: String,
    selectedCategory: BookCategory?,
    paddingValues: PaddingValues
){
    val filteredBooks by remember(title, selectedCategory) {
        derivedStateOf {
            books.filter { book ->
                book.title.contains(title, ignoreCase = true) &&
                        (selectedCategory == null || book.category == selectedCategory)
            }
        }
    }

    LazyColumn(
        modifier = Modifier
            .background(Beige)
            .fillMaxSize()
            .padding(paddingValues)
    ){
        items(items = filteredBooks){ book->
            ListBookCard(
                book.title,
                label = "Add to List",
                navHostController,
                onClick = {}
            )
        }

    }
}

@Composable
fun CategoriesRow(
    selectedCategory: BookCategory?,
    onCategorySelected: (BookCategory) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        BookCategory.allCategories.forEach { category ->
            category.let { // Only proceed if category is not null
                CategoryBtn(
                    category = it,
                    isSelected = it == selectedCategory,
                    onCategorySelected = { onCategorySelected(category) }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchPrev(){
    val navHostController = rememberNavController()
    Search(navHostController)
}