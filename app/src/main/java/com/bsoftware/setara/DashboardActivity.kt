package com.bsoftware.setara

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
                   Box(modifier = Modifier.background(Color.White)) {
                       DashboardActivityView()
                   }
                }
            }
        }
    }
}

@Composable
fun DashboardActivityView(){
    Column {
        // header
        DashboardActivityHeader()
        // content can scroll
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 10.dp)
        ){
            // category item
            item {
                Text(
                    text = stringResource(id = R.string.category_sign),
                    style = TextStyle(
                        fontSize = 20.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .padding(10.dp)
                )

                DashboardActivityMenu()
            }

            // video recommendation
            item {
                Spacer(modifier = Modifier.padding(top = 10.dp))
                Text(
                    text = stringResource(id = R.string.recommendation_sign),
                    style = TextStyle(
                        fontSize = 20.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .padding(10.dp)
                )

                RecommendVideo()
            }
        }
    }
}

@Composable
fun DashboardActivityHeader(){
    var level by remember { mutableStateOf(0) }
    var mission by remember { mutableStateOf(0) }
    var action by remember { mutableStateOf(0) }
    var username by remember { mutableStateOf("Hello") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = colorResource(id = R.color.blue_100))
            .height(160.dp)
    ) {
        // Image Account
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(start = 20.dp, top = 20.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "UserPhotoProfile",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
            )

            Text(
                text = username,
                style = TextStyle(
                    fontSize = 18.sp,
                    color = Color.Black
                ),
                modifier = Modifier
                    .padding(top = 10.dp)
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(top = 35.dp, start = 20.dp)
        ){
            // Level
            Column(
                modifier = Modifier
                    .padding(end = 40.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    text = level.toString(),
                    style = TextStyle(
                        fontSize = 25.sp,
                        color = Color.Black
                    )
                )
                Text(
                    text = "Level",
                    style = TextStyle(
                        fontSize = 15.sp,
                        color = Color.Black
                    )

                )
            }
            // Mission
            Column(
                modifier = Modifier
                    .padding(end = 40.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = mission.toString(),
                    style = TextStyle(
                        fontSize = 25.sp,
                        color = Color.Black
                    )
                )
                Text(
                    text = "Misi",
                    style = TextStyle(
                        fontSize = 15.sp,
                        color = Color.Black
                    )
                )
            }
            // Action
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = action.toString(),
                    style = TextStyle(
                        fontSize = 25.sp,
                        color = Color.Black
                    )
                )
                Text(
                    text = "Aksi",
                    style = TextStyle(
                        fontSize = 15.sp,
                        color = Color.Black
                    )
                )
            }
        }
    }
}

@Composable
fun DashboardActivityMenu(){
    val context : Context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        LazyRow{
            item{
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ){
                    Card(
                        modifier = Modifier
                            .size(120.dp, 150.dp)
                            .clickable {
                                context.startActivity(Intent(context,VideoOptionActivity::class.java))
                            }
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.option_login_asseth),
                                contentDescription = "SoftSkill asseth",
                                modifier = Modifier
                                    .size(100.dp,100.dp)
                            )
                            Text(
                                text = "Pengembangan Diri",
                                style = TextStyle(
                                    fontSize = 15.sp,
                                    textAlign = TextAlign.Center,
                                    color = Color.White
                                )
                            )
                        }
                    }

                    Card(
                        modifier = Modifier
                            .size(120.dp,150.dp)
                            .clickable {
                                context.startActivity(Intent(context,HardSkillActivity::class.java))
                            }
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxSize()
                        ){
                            Image(
                                painter = painterResource(id = R.drawable.hardskill_image),
                                contentDescription = "HardSkill asseth",
                                modifier = Modifier
                                    .size(100.dp,100.dp)
                            )
                            Text(
                                text = "Pelatihan Hardskill",
                                style = TextStyle(
                                    fontSize = 15.sp,
                                    textAlign = TextAlign.Center,
                                    color = Color.White
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RecommendVideo(){
    val getAllVideo = VideoSoftSkill().getVideoAll()
    val context : Context = LocalContext.current
    var videoId by remember { mutableStateOf("") }

    // Recommendation
    Column(
        modifier = Modifier
            .height(400.dp)
            .padding(10.dp)
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ){
            items(getAllVideo){ videoData ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clickable {
                            // intent
                            videoId = videoData.videoId.toString()
                            val intent = Intent(context, VideoPlayerSoftSkillActivity::class.java)
                            intent.putExtra("id", videoId)
                            context.startActivity(intent)
                        }
                ) {
                    AsyncImage(
                        model = videoData.thumbnail,
                        contentDescription = "ExampleThumbnail",
                        modifier = Modifier
                            .fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
                Text(
                    text = videoData.title!!,
                    style = TextStyle(
                        color = Color.Black
                    )
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DahboardActivityPreview() {
    SetaraTheme {
        DashboardActivityMenu()
    }
}