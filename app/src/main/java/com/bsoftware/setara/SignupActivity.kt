package com.bsoftware.setara

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bsoftware.setara.firebase.FirebaseAuthentication
import com.bsoftware.setara.ui.theme.SetaraTheme
import com.bsoftware.setara.ui.theme.elementWidget.AlertDialogCostumeView

class SignupActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SetaraTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SignupActivityView()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupActivityView(){
    var email by remember{ mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var passwordVisible by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    val context : Context = LocalContext.current
    val activity : Activity = SignupActivity()
    val firebaseAuth = FirebaseAuthentication()

    val showDialogAlert = remember { mutableStateOf(false) }
    var alertMessage by remember { mutableStateOf("") }

    // alert dialog
    // if a true value
    if(showDialogAlert.value){
        AlertDialogCostumeView(
            title = "Ooops",
            message = alertMessage,
            setDialog = {showDialogAlert.value = it}
        ) {
            // if a click, we change to false again
            showDialogAlert.value = false
        }
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // email field
        OutlinedTextField(
            value = email,
            onValueChange = {email = it},
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, top = 5.dp),
            label = {
                Text(text = stringResource(id = R.string.email_inputtext))
            },
            leadingIcon = {
                Icon(
                    Icons.Outlined.MailOutline,
                    contentDescription = "EmailIcon"
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )
        )

        // password field
        OutlinedTextField(
            value = password,
            onValueChange = {password = it},
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, top = 10.dp),
            label = {
                Text(text = stringResource(id = R.string.password_inputtext))
            },
            leadingIcon = {
                Icon(
                    Icons.Outlined.Lock,
                    contentDescription = "PasswordIcon"
                )
            },
            visualTransformation = if(passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password
            ),
            keyboardActions = KeyboardActions(
                onDone = {focusManager.clearFocus()}
            ),
            trailingIcon = {
                IconButton(
                    onClick = {passwordVisible = !passwordVisible}
                ) {
                    Icon(
                        painter = if(passwordVisible) painterResource(id = R.drawable.visible_outline_icon) else painterResource(id = R.drawable.visible_off_outline_icon),
                        contentDescription = if(passwordVisible) "HidePassword" else "ShowPassword"
                    )
                }
            }
        )

        OutlinedButton(
            onClick = {
                firebaseAuth.apply {
                    initFirebaseAuth()
                    createDataUser(
                        email = email,
                        password = password,
                        onSuccess = {
                            // intent into dashboard
                            Toast.makeText(context,"Register Succesfull", Toast.LENGTH_SHORT).show()
                            context.startActivity(Intent(context,DashboardActivity::class.java))
                            activity.finish()
                        },
                        onFailed = {
                            // if a fail
                             alertMessage = "You username or password maybe wrong input, please try again"
                             showDialogAlert.value = true
                        },
                        activity = activity
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 25.dp, end = 25.dp, top = 10.dp)
        ) {
            Text(text = stringResource(id = R.string.signup_button))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SignupActivityPreview() {
    SetaraTheme {
        SignupActivityView()
    }
}