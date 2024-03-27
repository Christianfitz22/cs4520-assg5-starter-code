package com.cs4520.assignment5

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room

class MainActivity : ComponentActivity() {
    private lateinit var productViewModel: ProductViewModel
    private lateinit var database: ProductDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        database = Room.databaseBuilder(
            applicationContext,
            ProductDatabase::class.java, "product_list"
        ).allowMainThreadQueries().build()

        DatabaseHolder.database = database

        productViewModel = ViewModelProvider(this, ProductViewModelFactory())[ProductViewModel::class.java]

        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MainNavigation()
                }
            }
        }
    }

    @Composable
    fun MainNavigation() {
        val navController = rememberNavController()
        NavHost(navController, startDestination = "listPage") {
            composable("loginPage") { LoginPage(onNavigateToListPage = {navController.navigate("listPage")})}
            composable("listPage") { ListPage()}
        }
    }

    @Composable
    fun LoginPage(onNavigateToListPage: () -> Unit) {
        var usernameText by remember { mutableStateOf("")}
        var passwordText by remember { mutableStateOf("")}

        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            //Text(text = "Hello world!", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
            //Text(text = "hello world 2", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
            TextField(value = usernameText, modifier = Modifier.fillMaxWidth(), onValueChange = {usernameText = it}, label = { Text("Username")})
            TextField(value = passwordText, modifier = Modifier.fillMaxWidth(), onValueChange = {passwordText = it}, label = { Text("Password")})
            Button(onClick = { loginClicked(usernameText, passwordText, onNavigateToListPage) }) {
                Text("Login")
            }
        }
    }

    fun loginClicked(usernameText: String, passwordText: String, onNavigateToListPage: () -> Unit) {
        if (usernameText == "admin" && passwordText == "admin") {
            onNavigateToListPage()
        } else {
            Toast.makeText(applicationContext, "Provided login is invalid.", Toast.LENGTH_SHORT).show()
        }
    }

    @Composable
    fun ListPage() {
        //val dataList = List(20) {"test"}

        val dataList by productViewModel.getProductData().observeAsState(listOf())
        // other case handling needed

        LazyColumn {
            items(items = dataList) { data -> ProductEntryParse(data)}
        }
    }

    @Composable
    fun ProductEntryParse(productEntry: ProductData) {
        Row(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
            if (productEntry.type == "Food") {
                Image(
                    painter = painterResource(R.drawable.food),
                    contentDescription = "food image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(100.dp).padding(10.dp)
                )
            } else {
                Image(
                    painter = painterResource(R.drawable.equipment),
                    contentDescription = "food image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(100.dp).padding(10.dp)
                )
            }
            Column(modifier = Modifier.height(100.dp).padding(10.dp), verticalArrangement = Arrangement.Center) {
                Text(text = productEntry.name)
                if (productEntry.expiryDate != null) {
                    Text(text = productEntry.expiryDate)
                }
                Text(text = productEntry.type)
            }
        }
    }
}