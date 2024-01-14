package com.bsoftware.setara

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bsoftware.setara.dataClass.ArticleDataClass
import com.bsoftware.setara.ui.theme.SetaraTheme

class ArticleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SetaraTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ArticleActivityView()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleActivityView(){
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .padding(top = 5.dp, start = 5.dp, end = 5.dp),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.article_sign),
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "ArrowBackIcon"
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
    ){innerPadding ->
        ArticleActivityContent(innerPadding = innerPadding)
    }
}

@Composable
fun ArticleActivityContent(innerPadding : PaddingValues){
    LazyColumn(
        contentPadding = innerPadding,
        modifier = Modifier
            .fillMaxSize()
    ){
        item {

        }
    }
}

// connect into ArticleShow
@Composable
fun ArticleShow(){
    Column(
        modifier = Modifier
            .fillMaxSize()
    ){
        // in here, we add some article
        Image(
            painter = painterResource(id = R.drawable.example_thumbnail),
            contentDescription = "ArticleImage",
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .padding(20.dp)
        ) {
            Text(
                text = "Title Article",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.padding(top = 10.dp))
            Text(
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas posuere odio quis ornare malesuada. Aenean tincidunt nunc volutpat, efficitur justo vitae, ornare odio. Nunc sed aliquam sem. Quisque dignissim lacus at tempor dignissim. Ut magna nunc, vestibulum quis consequat at, congue quis enim. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Ut in urna sagittis turpis euismod convallis non non justo. Vestibulum eu dolor nec massa lacinia fringilla in vitae lacus. Donec aliquet magna vel neque consectetur, in accumsan tellus volutpat. Proin vitae turpis a leo interdum vehicula. Donec condimentum, massa sed fermentum hendrerit, purus neque elementum magna, quis egestas purus nisl quis ligula. Vivamus id tincidunt libero. In in mattis sem, id tincidunt lorem. Cras euismod vitae velit nec commodo. Quisque mollis mauris sed malesuada faucibus."
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ArticleActivityPreview() {
    SetaraTheme {
       ArticleShow()
    }
}