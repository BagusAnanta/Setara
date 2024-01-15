package com.bsoftware.setara

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
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
import com.bsoftware.setara.dataClass.QuizDataClass
import com.bsoftware.setara.ui.theme.SetaraTheme

class QuizActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SetaraTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    QuizActivityView()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizActivityView(){
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .padding(top = 5.dp, start = 5.dp, end = 5.dp),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.quiz_sign),
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
        QuizActivityContent(innerPadding = innerPadding)
    }
}

@Composable
fun QuizActivityContent(innerPadding : PaddingValues){
    val question = listOf<QuizDataClass>(
        QuizDataClass(
            "Manakah yang tidak termasuk adjektiva dari kata berikut...",
            listOf("Tempat tidur","Lucu","Cantik","Lelah"),
            "Tempat tidur"
        ),
        QuizDataClass(
            "Adjektiva merupakan kata sifat. Pernyataan tersebut benar atau salah ?",
            listOf("Benar","Salah"),
            "Benar"
        )
    )
    LazyColumn(
        contentPadding = innerPadding,
        modifier = Modifier
            .fillMaxSize()
    ){
        item {
            QuizActivityComponent(question = question)
        }
    }
}

@Composable
fun QuizActivityComponent(question : List<QuizDataClass>){
    val context : Context = LocalContext.current
    var progress = remember{ mutableStateOf(0) }
    var selectedAnswer by remember { mutableStateOf(-1) }
    var isReset by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LinearProgressIndicator(
            progress = progress.value / question.size.toFloat(),
            modifier = Modifier
                .fillMaxWidth(),
            color = Color.Green,
        )
        Text(
            text =
            try {
                question[progress.value].question
            } catch (E: ArrayIndexOutOfBoundsException){
                Log.e("ArrayIndexException", E.toString()).toString()
            },
            modifier = Modifier
                .fillMaxWidth()
        )

        for (option in 0 until question[progress.value].option.size){
            Button(
                onClick = {
                    // fill SelectedAnswer
                    selectedAnswer = option
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if(selectedAnswer == option){
                        Color.Blue
                    } else if(isReset) {
                        Color.White
                    } else {
                        Color.White
                    }
                )
            ) {
                Text(
                    text = question[progress.value].option[option]
                )
            }
        }

        Row{
            Button(onClick = {
               try {
                   progress.value -= 1
               } catch (E : ArrayIndexOutOfBoundsException){
                   Toast.makeText(context,"Soal Tidak Ada Lagi",Toast.LENGTH_SHORT).show()
               }
            }) {
                Text(text = "Kembali")
            }

            Button(onClick = { 
                try{
                    if(question[selectedAnswer].option[selectedAnswer] == question[progress.value].correctAnswer){
                        progress.value += 1
                        isReset = true
                        // nested if in here
                        if(progress.value == question.size){
                            // intent in here

                        }
                    } else {
                        progress.value = 0
                    }
                } catch (E : ArrayIndexOutOfBoundsException){

                }
            }) {
                Text(text = "Selanjutnya")
            }
        }
    }
}




@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    SetaraTheme {
        QuizActivityView()
    }
}