package com.odougle.composefirtsteps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ){
                CircularProgressBar(percentage = 0.8f, number = 100)
            }
        }
    }
}

@Composable
fun CircularProgressBar(
    percentage: Float,
    number: Int,
    fontSize: TextUnit = 28.sp,
    radius: Dp = 50.dp,
    color: Color = Color.Green,
    strokeWidth: Dp = 8.dp,
    animDuration: Int = 1000,
    animDelay: Int = 0
) {
    var animationPlayed by remember {
        mutableStateOf(false)
    }

    val curPercentage = animateFloatAsState(
        targetValue = if(animationPlayed) percentage else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = animDelay
        )
    )

    LaunchedEffect(key1 = true){
        animationPlayed = true
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(radius * 2f)
    ){
        Canvas(
            modifier = Modifier.size(radius * 2f)
        ){
            drawArc(
                color = color,
                -90f,
                360 * curPercentage.value,
                useCenter = false,
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
            )
        }
        Text(
            text = (curPercentage.value * number).toInt().toString(),
            color = Color.Black,
            fontSize = fontSize,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ConstraintLayout() {
    val constraints = ConstraintSet {
        val greenBox = createRefFor("greenBox")
        val redBox = createRefFor("redBox")
        val guideLine = createGuidelineFromTop(0.5f)

        constrain(greenBox) {
            top.linkTo(guideLine)
            start.linkTo(parent.start)
            width = Dimension.value(100.dp)
            height = Dimension.value(100.dp)
        }
        constrain(redBox) {
            top.linkTo(parent.top)
            start.linkTo(greenBox.end)
            width = Dimension.value(100.dp)
            height = Dimension.value(100.dp)
        }
        createHorizontalChain(greenBox, redBox, chainStyle = ChainStyle.Packed)
    }

    ConstraintLayout(
        constraintSet = constraints,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .background(Color.Green)
                .layoutId("greenBox")
        ) {

        }

        Box(
            modifier = Modifier
                .background(Color.Red)
                .layoutId("redBox")
        ) {

        }


    }
}

@Composable
fun LazyList() {
    LazyColumn {
        itemsIndexed(
            listOf("this", "is", "jetpack", "compose")
        ) { index, string ->
            Text(
                text = string,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp)
            )
        }
    }
}

@Composable
fun TextFieldAndSnackBar() {
    val scaffoldState = rememberScaffoldState()
    var textFieldState by remember {
        mutableStateOf("")
    }

    var scope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        scaffoldState = scaffoldState
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = 30.dp
                )
        ) {
            TextField(
                value = textFieldState,
                label = {
                    Text("Enter your name")
                },
                onValueChange = {
                    textFieldState = it
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(
                        "Hello $textFieldState"
                    )

                }
            }) {
                Text(text = "Please greet me")
            }
        }
    }
}


@Composable
fun ColorBox(
    modifier: Modifier = Modifier
) {
    val color = remember {
        mutableStateOf(Color.Yellow)
    }

    Box(
        modifier = modifier
            .background(color.value)
            .clickable {
                color.value = Color(
                    Random.nextFloat(),
                    Random.nextFloat(),
                    Random.nextFloat(),
                    1f
                )
            }
    )
}


@Composable
fun ImageCard() {
    val painter = painterResource(id = R.drawable.kermit2)
    val description = "Kermit is drinking tea"
    val title = "Kermit is drinking tea"
    Box(
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .padding(16.dp)
    ) {
        ImageCard(
            painter = painter,
            contentDescription = description,
            title = title
        )
    }
}

@Composable
fun ImageCard(
    painter: Painter,
    contentDescription: String,
    title: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        elevation = 5.dp
    ) {
        Box(modifier = Modifier.height(200.dp)) {
            Image(
                painter = painter,
                contentDescription = contentDescription,
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black
                            ),
                            startY = 300f
                        )
                    )
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Text(text = title, style = TextStyle(color = Color.White, fontSize = 16.sp))
            }
        }
    }
}

