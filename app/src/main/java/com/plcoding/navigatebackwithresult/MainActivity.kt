package com.plcoding.navigatebackwithresult

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.plcoding.navigatebackwithresult.ui.theme.NavigateBackWithResultTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigateBackWithResultTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "screen1"
                ) {
                    composable("screen1") { entry ->
                        val text = entry.savedStateHandle.get<String>("my_text")
                        Column(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            text?.let {
                                Text(text = text)
                            }
                            Button(onClick = {
                                navController.navigate("screen2") {
                                    popUpTo("login") {
                                        inclusive = true
                                    }
                                }
                            }) {
                                Text(text = "Go to screen2")
                            }
                        }
                    }
                    composable("screen2") {
                        Column(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            var text by remember {
                                mutableStateOf("")
                            }
                            OutlinedTextField(
                                value = text,
                                onValueChange = { text = it },
                                modifier = Modifier.width(300.dp)
                            )
                            Button(onClick = {
                                navController.previousBackStackEntry
                                    ?.savedStateHandle
                                    ?.set("my_text", text)
                                navController.popBackStack()
                            }) {
                                Text(text = "Apply")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NavigateBackWithResultTheme {
        Greeting("Android")
    }
}