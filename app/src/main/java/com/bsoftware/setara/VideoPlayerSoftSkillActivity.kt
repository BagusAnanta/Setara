package com.bsoftware.setara

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.bsoftware.setara.ui.theme.SetaraTheme
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.google.firebase.storage.FirebaseStorage


class VideoPlayerSoftSkillActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SetaraTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    VideoPlayerSoftSkillActivityView()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoPlayerSoftSkillActivityView(){
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    var titleVideo by remember{ mutableStateOf("Video 1") }

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
                            color = Color.Black,
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
        VideoPlayerSoftSkillActivityContent(innerPadding = innerPadding)
    }
}

@Composable
fun VideoPlayerSoftSkillActivityContent(innerPadding : PaddingValues){
    var urlData by remember { mutableStateOf("") }

    // example url
    // val urlVideo = urlData
    // Log.d("UrlVideo", urlVideo)
    // val urlVideo = "https://firebasestorage.googleapis.com/v0/b/setaraapps.appspot.com/o/Bagaimana%20cara%20kerja%20Televisi%20Satelit_.mp4?alt=media&token=d6c7243f-2e9f-4162-90fd-28a22591ce43"
    val context : Context = LocalContext.current
    urlData = "Bagaimana cara kerja Televisi Satelit_.mp4"

    LazyColumn(
        contentPadding = innerPadding,
        modifier = Modifier
            .fillMaxSize()
    ){
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {
                // exo player at here
                val exoPlayer = remember(context){
                    ExoPlayer.Builder(context).build().apply {
                        val dataSourceFactory = DefaultDataSourceFactory(context, Util.getUserAgent(context,context.packageName))
                        // get url from firebase storage
                        FirebaseStorage.getInstance().getReference(urlData).downloadUrl
                            .addOnSuccessListener { url ->
                                val sourceLink = ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(MediaItem.fromUri(url))
                                prepare(sourceLink)
                            }
                            .addOnFailureListener {
                                    fail -> Log.e("Get Uri Error", fail.toString())
                            }
                    }
                }

                AndroidView(factory = {context ->
                    PlayerView(context).apply {
                        player = exoPlayer
                    }
                })
            }

            Text(
                text = stringResource(id = R.string.trancript_sign),
                modifier = Modifier
                    .padding(start = 10.dp,top = 10.dp)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun VideoPlayerSoftSkillActivityPreview() {
    SetaraTheme {
        VideoPlayerSoftSkillActivityView()
    }
}