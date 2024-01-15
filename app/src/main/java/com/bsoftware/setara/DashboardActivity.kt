package com.bsoftware.setara

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.bsoftware.setara.dataClass.VideoSoftSkillDataClass
import com.bsoftware.setara.dataVideo.VideoSoftSkill
import com.bsoftware.setara.ui.theme.SetaraTheme

class DashboardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SetaraTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                   DashboardActivityView()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardActivityView(){
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    var level by remember{ mutableStateOf(1) }

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .padding(top = 5.dp, start = 5.dp, end = 5.dp),
        topBar = {
           TopAppBar(
               title = {
                   Text(
                       text = stringResource(id = R.string.level_sign,level),
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 25.sp
                        )
                   )
               },
               actions = {
                   IconButton(
                       onClick = { /*TODO*/ }
                   ) {
                       Icon(
                           imageVector = Icons.Filled.Notifications,
                           contentDescription = "Notification Icon",
                           modifier = Modifier
                               .size(25.dp,25.dp)
                       )
                   }
               },
               scrollBehavior = scrollBehavior,

           )
        }
    ) {innerPadding ->
        DashboardActivityContent(innerPadding = innerPadding)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardActivityContent(innerPadding : PaddingValues){
    var search by remember { mutableStateOf("") }

    var streak by remember { mutableStateOf(0) }
    var action by remember { mutableStateOf(0) }
    
    val getAllVideo = VideoSoftSkill().getVideoAll()

    val context : Context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 60.dp)
    ) {
        // search bar
        OutlinedTextField(
            value = search,
            onValueChange = {search = it},
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp, top = 5.dp),
            label = {
                Text(text = stringResource(id = R.string.search_sign))
            },
        )

        // streak and action
        Row(
            horizontalArrangement = Arrangement.spacedBy(30.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .wrapContentWidth(Alignment.CenterHorizontally)
        ) {
            Card(
                modifier = Modifier
                    .size(100.dp, 100.dp)
                    .fillMaxSize(),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = streak.toString(),
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = stringResource(id = R.string.streak_sign),
                        style = TextStyle(
                            fontSize = 15.sp
                        )
                    )
                }
            }

            Card(
                modifier = Modifier
                    .size(100.dp, 100.dp)
                    .fillMaxSize(),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = action.toString(),
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = stringResource(id = R.string.action_sign),
                        style = TextStyle(
                            fontSize = 15.sp
                        )
                    )
                }
            }
        }

        // menu
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        ) {
            Text(
                text = stringResource(id = R.string.category_sign),
                style = TextStyle(
                    fontSize = 20.sp,
                ),
                modifier = Modifier
                    .padding(10.dp)
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(30.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .wrapContentWidth(Alignment.CenterHorizontally)
            ){
                Button(onClick = {
                    // intent
                    context.startActivity(Intent(context,VideoOptionActivity::class.java))
                }) {
                    Text(
                        text = stringResource(id = R.string.selfimprove_sign),
                        style = TextStyle(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                Button(onClick = {
                    context.startActivity(Intent(context,HardSkillActivity::class.java))
                }) {
                    Text(
                        text = stringResource(id = R.string.hardskill_sign),
                        style = TextStyle(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }

        // Recommendation
        Text(
            text = stringResource(id = R.string.recommendation_sign),
            style = TextStyle(
                fontSize = 20.sp,
            ),
            modifier = Modifier
                .padding(10.dp)
        )
        LazyColumn{
            items(getAllVideo){ videoData ->
                RecommendVideo(videoSoftSkillDataClass = videoData)
            }
        }
    }
}

@Composable
fun RecommendVideo(videoSoftSkillDataClass: VideoSoftSkillDataClass){
    val context : Context = LocalContext.current
    var videoId by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp, top = 10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ){
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clickable {
                    // intent
                    videoId = videoSoftSkillDataClass.videoId.toString()
                    val intent = Intent(context, VideoPlayerSoftSkillActivity::class.java)
                    intent.putExtra("id", videoId)
                    context.startActivity(intent)
                }
        ) {
            AsyncImage(
                model = videoSoftSkillDataClass.thumbnail,
                contentDescription = "ExampleThumbnail",
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        Text(
            text = videoSoftSkillDataClass.title!!
        )
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DahboardActivityPreview() {
    SetaraTheme {
       DashboardActivityView()
    }
}