package com.bsoftware.setara

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.bsoftware.setara.dataVideo.VideoSoftSkill
import com.bsoftware.setara.ui.theme.SetaraTheme
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util

class VideoPlayerSoftSkillActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SetaraTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colorResource(id = R.color.blue_100)
                ) {
                    // you get a video Id from a option before in videoOption
                    var videoId by remember { mutableStateOf("") }

                    var title by remember { mutableStateOf("") }
                    var subtitle by remember { mutableStateOf("") }
                    var urlVideo by remember { mutableStateOf("") }

                    videoId = intent.getStringExtra("id").toString()
                    Log.d("VideoId", videoId)
                    // get a data from id
                    VideoSoftSkill().apply {
                        getVideoById(videoId = videoId).forEach {
                            title = it.title.toString()
                            subtitle = it.subtitle.toString()
                            urlVideo = it.link.toString()

                            VideoPlayerSoftSkillActivityView(title,subtitle,urlVideo)
                        }
                    }

                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoPlayerSoftSkillActivityView(titleVideo : String,subtitle: String,linkVideo : String){
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .padding(top = 5.dp, start = 5.dp, end = 5.dp),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = titleVideo,
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
                            contentDescription = "ArrowBackIcon",
                            tint = Color.Black
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = colorResource(id = R.color.blue_100),
                    titleContentColor = Color.Black
                )
            )
        },
    ){innerPadding ->
        VideoPlayerSoftSkillActivityContent(innerPadding = innerPadding,subtitle,linkVideo)
    }
}

@Composable
fun VideoPlayerSoftSkillActivityContent(innerPadding : PaddingValues, subtitle : String, urlVideo : String){
    val context : Context = LocalContext.current

    LazyColumn(
        contentPadding = innerPadding,
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.blue_100))
    ){
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
            ) {
                // exo player at here
                val exoPlayer = remember(context){
                    ExoPlayer.Builder(context).build().apply {
                        val dataSourceFactory = DefaultDataSourceFactory(context, Util.getUserAgent(context,context.packageName))
                        Log.d("ExoPlayer Url", urlVideo)
                        val sourceLink = ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(MediaItem.fromUri(urlVideo))
                        prepare(sourceLink)
                    }
                }


                AndroidView(factory = {context ->
                    PlayerView(context).apply {
                        player = exoPlayer
                    }
                })
            }
            
            Column {
                Text(
                    text = stringResource(id = R.string.trancript_sign),
                    modifier = Modifier
                        .padding(start = 10.dp,top = 10.dp),
                    style = TextStyle(
                        color = Color.Black
                    )
                )
                Text(
                    text = subtitle,
                    modifier = Modifier
                        .padding(20.dp),
                    style = TextStyle(
                        textAlign = TextAlign.Justify,
                        color = Color.Black
                    )
                )
                // Button
                Button(
                    onClick = {
                        // start activity
                        context.startActivity(Intent(context,QuizActivity::class.java))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(30.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.blue_500)
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = "Selanjutnya",
                        style = TextStyle(
                            color = Color.White
                        )
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun VideoPlayerSoftSkillActivityPreview() {
    SetaraTheme {
        val videoId by remember { mutableStateOf("Video1") }

        var title by remember { mutableStateOf("") }
        var subtitle by remember { mutableStateOf("") }
        var urlVideo by remember { mutableStateOf("") }

        // get a data from id
        VideoSoftSkill().apply {
            getVideoById(videoId = videoId).forEach {
                title = it.title.toString()
                subtitle = it.subtitle.toString()
                urlVideo = it.link.toString()

                VideoPlayerSoftSkillActivityView(title,subtitle,urlVideo)
            }
        }
    }
}