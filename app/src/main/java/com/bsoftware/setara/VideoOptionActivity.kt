package com.bsoftware.setara

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import com.bsoftware.setara.dataClass.VideoSoftSkillDataClass
import com.bsoftware.setara.firebase.FirebaseRealtime
import com.bsoftware.setara.sharePreference.VideoIdSharePref
import com.bsoftware.setara.ui.theme.SetaraTheme
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class VideoOptionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SetaraTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    VideoOptionActivityView()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoOptionActivityView(){
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .padding(top = 5.dp, start = 5.dp, end = 5.dp),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.videooption_sign),
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
        VideoOptionActivityContect(innerPadding = innerPadding)
    }
}

@Composable
fun VideoOptionActivityContect(innerPadding : PaddingValues){
    val dataList = remember { mutableStateListOf<VideoSoftSkillDataClass>() }

    LaunchedEffect(FirebaseRealtime().getReference()) {
        val postListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    dataList.clear()

                    for (projectSnapshot in snapshot.children) {
                        val dataMap = projectSnapshot.value as? Map<*, *>?

                        if (dataMap != null) {
                            val videoSoftSkillDataClass = VideoSoftSkillDataClass(
                                videoId = dataMap["videoId"] as? String ?: "",
                                title = dataMap["title"] as? String ?: "",
                                subtitle = dataMap["subtitle"] as? String ?: "",
                                link = dataMap["link"] as? String ?: "",
                                thumbnail = dataMap["thumbnail"] as? String ?: ""
                            )
                            dataList.add(videoSoftSkillDataClass)
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("getAllDataVideo() Error", error.toString())
            }

        }
        FirebaseRealtime().getReference().addValueEventListener(postListener)
    }

    // you can get a count video from firebase in here
    LazyColumn(
        contentPadding = innerPadding,
        modifier = Modifier
            .fillMaxSize()
    ){
       items(dataList){listData ->
            VideoPreview(videoSoftSkillDataClass = listData)
       }
    }

}

@Composable
fun VideoPreview(videoSoftSkillDataClass : VideoSoftSkillDataClass){
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
                    intent.putExtra("id",videoId)
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
fun VideoOptionActivityPreview() {
    SetaraTheme {
        VideoOptionActivityView()
    }
}