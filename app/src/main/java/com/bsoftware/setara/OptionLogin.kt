package com.bsoftware.setara

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bsoftware.setara.ui.theme.SetaraTheme

class OptionLogin : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SetaraTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    OptionLoginView()
                }
            }
        }
    }
}

@Composable
fun OptionLoginView(){
    val context : Context = LocalContext.current
    val activity : Activity = OptionLogin()
   Column(
       verticalArrangement = Arrangement.Center,
       horizontalAlignment = Alignment.CenterHorizontally
   ) {
            OutlinedButton(
                onClick = {
                    // login Activity
                    context.startActivity(Intent(context,LoginActivity::class.java))
                    activity.finish()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 5.dp)
            ) {
                Text(text = stringResource(id = R.string.login_text))
            }
       
           OutlinedButton(
               onClick = {
                    // SignUp Activity
                    context.startActivity(Intent(context,SignupActivity::class.java))
                    activity.finish()
               },
               modifier = Modifier
                   .fillMaxWidth()
                   .padding(start = 10.dp, end = 10.dp, top = 5.dp)
           ) {
                Text(text = stringResource(id = R.string.signup_text))
           }
   }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun OptionLoginPreview() {
    SetaraTheme {
        OptionLoginView()
    }
}