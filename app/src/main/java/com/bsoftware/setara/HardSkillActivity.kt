package com.bsoftware.setara

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.bsoftware.setara.dataVideo.VideoHardSkill
import com.bsoftware.setara.firebase.FirebaseArticle
import com.bsoftware.setara.ui.theme.SetaraTheme

class HardSkillActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SetaraTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HardSkillActivityView()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HardSkillActivityView(){
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .padding(top = 5.dp, start = 5.dp, end = 5.dp),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.selfimprove_sign),
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
        HardSkillActivityContent(innerPadding = innerPadding)
    }
}

@Composable
fun HardSkillActivityContent(innerPadding : PaddingValues){
    LazyColumn(
        contentPadding = innerPadding,
        modifier = Modifier
            .fillMaxSize()
    ){
        item{
            Text(text = stringResource(id = R.string.article_sign))
            Spacer(modifier = Modifier.padding(bottom = 10.dp))
            ArticleHardSkill()
        }

        item{
            Spacer(modifier = Modifier.padding(bottom = 20.dp))
            Text(text = stringResource(id = R.string.recommendation_sign))
            HardSkillVideoRecommend()
        }
    }
}

@Composable
fun ArticleHardSkill(){
   val context : Context = LocalContext.current

   val getArticle = FirebaseArticle().getArticleAll()
   var articleId by remember{ mutableStateOf("") }

   Row(
       modifier = Modifier
           .fillMaxWidth()
   ){
       LazyRow(
           contentPadding = PaddingValues(5.dp),
           horizontalArrangement = Arrangement.spacedBy(10.dp)
       ){
           items(items = getArticle){dataArticle ->
               Card(
                   modifier = Modifier
                       .size(150.dp, 200.dp)
                       .clickable {
                           // intent
                           articleId = dataArticle.articleId.toString()
                           val intent = Intent(context, ArticleActivity::class.java)
                           intent.putExtra("idArticle", articleId)
                           context.startActivity(intent)
                       }
               ) {
                   Column(
                       modifier = Modifier
                           .fillMaxSize()
                   ) {
                       AsyncImage(
                           model = dataArticle.thumbnail,
                           contentDescription = "ThumbnailArticle",
                           modifier = Modifier
                               .fillMaxWidth()
                               .height(100.dp),
                           contentScale = ContentScale.Crop
                       )
                       Text(
                           text = dataArticle.title.toString(),
                           style = TextStyle(
                               color = Color.Black,
                               fontSize = 14.sp
                           ),
                           modifier = Modifier
                               .padding(5.dp)
                       )
                   }
               }
           }
       }
   }
}

@Composable
fun HardSkillVideoRecommend(){
    val context : Context = LocalContext.current

    val videoHardSkill = VideoHardSkill().getVideoAll()
    var videoId by remember { mutableStateOf("") }

    Column(modifier = Modifier.height(400.dp)) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ){
            items(items = videoHardSkill){dataVideo ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clickable {
                            // intent
                            videoId = dataVideo.videoId.toString()
                            // change intent
                            val intent = Intent(context, VideoPlayerHardSkillActivity::class.java)
                            intent.putExtra("idHardSkill", videoId)
                            context.startActivity(intent)
                        }
                ) {
                     AsyncImage(
                         model = dataVideo.thumbnail,
                         contentDescription = "ExampleThumbnail",
                         modifier = Modifier
                             .fillMaxSize(),
                         contentScale = ContentScale.Crop
                     )
                }
                Text(
                    text = dataVideo.title!!
                )
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HardSkillActivityPreview() {
    SetaraTheme {
        HardSkillActivityView()
    }
}