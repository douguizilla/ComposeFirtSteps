package com.odougle.composefirtsteps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.odougle.composefirtsteps.ui.theme.ComposeFirtStepsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

        }
    }
}



@Composable
fun ImageCard(){

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ImageCard()
}