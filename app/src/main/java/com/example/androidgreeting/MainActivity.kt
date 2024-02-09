package com.example.androidgreeting

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidgreeting.service.GreetingInfo
import com.example.androidgreeting.service.GreetingService
import com.example.androidgreeting.ui.theme.AndroidGreetingTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidGreetingTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .paddingFromBaseline(
                            top = 80.dp
                        ),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting()
                }
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var message by remember { mutableStateOf("Tap button to greet.") }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "$message",
            color = Color.Magenta,
            modifier = modifier
        )
        Button(onClick = {
            GreetingService.instance.greet().enqueue(object : Callback<GreetingInfo> {
                override fun onResponse(
                    call: Call<GreetingInfo>,
                    response: Response<GreetingInfo>
                ) {
                    val responseCode = response.code()
                    Toast.makeText(context, "response code: $responseCode", Toast.LENGTH_SHORT)
                        .show()
                    val greetingInfo = response.body()
                    if (greetingInfo != null) {
                        message = greetingInfo.message
                    }
                }

                override fun onFailure(call: Call<GreetingInfo>, t: Throwable) {
                    Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                }
            })
        }) {
            Text(text = "Greet")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidGreetingTheme {
        Greeting()
    }
}