package com.cs4520.assignment5

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    LoginPage()
                }
            }
        }
    }

    @Composable
    fun LoginPage() {
        var usernameText by remember { mutableStateOf("")}
        var passwordText by remember { mutableStateOf("")}

        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            //Text(text = "Hello world!", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
            //Text(text = "hello world 2", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
            TextField(value = usernameText, modifier = Modifier.fillMaxWidth(), onValueChange = {usernameText = it}, label = { Text("Username")})
            TextField(value = passwordText, modifier = Modifier.fillMaxWidth(), onValueChange = {passwordText = it}, label = { Text("Password")})
            Button(onClick = { loginClicked(usernameText, passwordText) }) {
                Text("Login")
            }
        }
    }

    fun loginClicked(usernameText: String, passwordText: String) {
        if (usernameText == "admin" && passwordText == "admin") {
            // navigate to next page
        } else {
            Toast.makeText(applicationContext, "Provided login is invalid.", Toast.LENGTH_SHORT).show()
        }
    }
}