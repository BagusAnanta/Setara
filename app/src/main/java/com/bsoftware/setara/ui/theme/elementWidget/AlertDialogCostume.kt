package com.bsoftware.setara.ui.theme.elementWidget

import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.bsoftware.setara.ui.theme.elementWidget.ui.theme.SetaraTheme

class AlertDialogCostume : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SetaraTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Greeting2("Android")
                }
            }
        }
    }
}

@Composable
fun AlertDialogCostumeView(
    title : String,
    message : String,
    setDialog : (Boolean) -> Unit,
    onClickButton : () -> Unit
){
    Dialog(onDismissRequest = {setDialog(false)}) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White
        ) {
            Box(contentAlignment = Alignment.Center) {
                Column(modifier = Modifier.padding(20.dp)) {
                    // title text
                    Text(
                        text = title,
                        color = Color.Black,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.CenterHorizontally),
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Spacer(modifier = Modifier.padding(top = 15.dp))

                    Text(
                        text = message,
                        color = Color.Black,
                        style = TextStyle(
                            fontSize = 15.sp
                        )
                    )
                    
                    OutlinedButton(
                        onClick = { onClickButton()},
                        modifier = Modifier
                            .fillMaxWidth(),
                        shape = CircleShape,
                        colors = ButtonDefaults
                            .outlinedButtonColors(
                               containerColor = Color(0xFF39A7FF)
                            )
                    ) {
                       Text(
                           text = "Ok",
                           color = Color.White
                       )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AlertDialogCostumePreview() {
    SetaraTheme {
        AlertDialogCostumeView(
            title = "Login Error",
            message = "Login Failed, please try again more time",
            setDialog = {}
        ) {
            /*TODO*/
        }
    }
}