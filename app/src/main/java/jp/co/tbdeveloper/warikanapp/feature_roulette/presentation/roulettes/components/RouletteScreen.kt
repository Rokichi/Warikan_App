package jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.roulettes.components

import android.widget.Toast
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import jp.co.tbdeveloper.warikanapp.R
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Member
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.ResultWarikan
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Warikan
import jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.roulettes.RouletteViewModel
import jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.roulettes.RoulettesEvent
import jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.utlis.Circle
import jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.utlis.FunShape
import kotlinx.coroutines.flow.collectLatest
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun RouletteScreen(
    navController: NavController,
    viewModel: RouletteViewModel = hiltViewModel(),
) {
    val context = LocalContext.current

    val rouletteState = viewModel.rouletteState.collectAsState()
    val resultWarikanState = viewModel.resultWarikanState.collectAsState()
    val isRotating = remember { viewModel.isRotatingState }
    val resultDeg = remember { viewModel.resultDeg }

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is RouletteViewModel.UiEvent.SaveEvent -> {
                    Toast.makeText(context, "データを保存しました", Toast.LENGTH_SHORT).show()
                }
                is RouletteViewModel.UiEvent.StartRoulette -> {}
                is RouletteViewModel.UiEvent.StopRoulette -> {}
                is RouletteViewModel.UiEvent.EndRoulette -> {
                    //Toast.makeText(context, "ルーレットが終了しました", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Column(
        // 一回り小さく配置
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        // トップバー
        PageBackBar { navController.navigateUp() }
        Text(
            text = rouletteState.value.Total.toString() + " 円",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h1,
            color = MaterialTheme.colors.surface
        )
        // ルーレット
        Box(
            modifier = Modifier.padding(top = 30.dp),
            contentAlignment = Alignment.Center
        ) {
            CircleOfRoulette(
                viewModel = viewModel,
                size = 350.dp,
                members = rouletteState.value.Members,
                warikans = rouletteState.value.Warikans,
                sumOfpr = viewModel.sumOfProportion,
                isRotating = isRotating.value,
                isBordered = true,
                resultDeg = resultDeg.value
            )
            CircleButton(
                size = 100,
                difference = 5,
                beforeText = "START",
                afterText = "STOP",
                letterSpacing = 2,
                onClick = {
                    if (!isRotating.value) {
                        viewModel.onEvent(RoulettesEvent.StartClickEvent)
                    } else {
                        viewModel.onEvent(RoulettesEvent.StopClickEvent)
                    }
                }
            )
            Image(
                painter = painterResource(id = R.drawable.rounedtriangle),
                modifier = Modifier
                    .size(60.dp)
                    .offset(y = (-180).dp),
                contentDescription = "roulette arrow"
            )
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(15.dp),
        ) {
            MemberAndColorsScrollView(
                Modifier
                    .padding(top = 20.dp)
                    .weight(6.0f),
                results = resultWarikanState.value
            )
            MuteBar(Modifier.weight(1.0f))
        }
    }
}

@Composable
fun MuteBar(
    modifier: Modifier = Modifier,
    isMuted: Boolean = false
) {
    var isMuted by remember { mutableStateOf(isMuted) }
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
    ) {
        if (isMuted) {
            Image(
                painter = painterResource(id = R.drawable.ic_volume_off),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxHeight()
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = rememberRipple(color = Color.Black, radius = 18.dp),
                        onClick = { isMuted = !isMuted }
                    ),
                contentDescription = "Now is muted"
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.ic_volume_on),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxHeight()
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = rememberRipple(color = Color.Black, radius = 18.dp),
                        onClick = { isMuted = !isMuted }
                    ),
                contentDescription = "Now is not muted"
            )
        }
    }
}

@Composable
fun PageBackBar(
    onPageBackButtonClick: () -> Unit = {},
) {
    Row(
        // 横幅Max, 横は等間隔，縦は真ん中に
        modifier = Modifier
            .fillMaxWidth()
            .height(35.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_arrow_left),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxHeight()
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = rememberRipple(color = Color.Black, radius = 18.dp),
                    onClick = { onPageBackButtonClick(); }
                ),
            contentDescription = "page back button"
        )
    }
}

@Composable
fun CircleOfRoulette(
    modifier: Modifier = Modifier,
    viewModel: RouletteViewModel,
    size: Dp,
    members: List<Member>,
    warikans: List<Warikan>,
    sumOfpr: Int,
    isRotating: Boolean = false,
    isBordered: Boolean = false,
    resultDeg: Float
) {
    var oldDegree = 0f
    var deg: Float = 360f / sumOfpr
    val memberColors = List(members.size) { i ->
        Member.memberColors[members[i].color]
    }
    var currentRotation by rememberSaveable { mutableStateOf(0f) }
    val rotation = remember { Animatable(currentRotation) }

    LaunchedEffect(isRotating) {
        // 1回転を1秒で永遠に
        if (isRotating) {
            rotation.animateTo(
                targetValue = currentRotation + 360f,
                animationSpec = infiniteRepeatable(
                    animation = tween(400, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                )
            ) { currentRotation = value }
        } else {
            if (currentRotation > 0f) {
                // Slow down rotation on pause
                // current % 360 = 0にして角度調整
                var targetRotation = currentRotation + 360f - (currentRotation % 360f) + resultDeg
                targetRotation %= 360f

                val state = rotation.animateTo(
                    targetValue = if (targetRotation - currentRotation >= 180f) targetRotation else targetRotation + 360f,
                    animationSpec = tween(
                        durationMillis = 1200 + 50 * (targetRotation % 100).toInt(),
                        easing = LinearOutSlowInEasing,
                    ),
                ) {
                    currentRotation = value
                }
                if (!state.endState.isRunning) {
                    viewModel.onEvent(RoulettesEvent.EndRouletteEvent)
                }
            }
        }
    }

    Box(
        Modifier.rotate(rotation.value),
        contentAlignment = Alignment.Center
    ) {
        val radius = size / 2
        for (warikan in warikans) {
            val sweepAngle: Float = warikan.proportion * deg
            FunShape(
                modifier = modifier.size(size),
                backGroundColor =
                if (warikan.color != -1) Member.memberColors[warikan.color]
                else Color.LightGray,
                startAngle = oldDegree,
                sweepAngle = sweepAngle,
                isBordered = isBordered
            )
            val deg = (oldDegree + 270f + sweepAngle / 2) % 360f
            Box(
                modifier = Modifier
                    .offset(
                        (radius.value * 7 / 10 * cos(Math.toRadians(deg.toDouble()))).dp,
                        (radius.value * 7 / 10 * sin(Math.toRadians(deg.toDouble()))).dp
                    )
                    .rotate(deg + 90f)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.White)
                    .border(
                        width = 1.dp,
                        color = Color.LightGray,
                        shape = RoundedCornerShape(20.dp),
                    )
                    .padding(horizontal = 5.dp),
                contentAlignment = Alignment.Center
            ) {
                RoundedRatio(memberColors = memberColors, ratios = warikan.ratios)
            }
            oldDegree += sweepAngle
        }
    }
}

@Composable
fun CircleButton(
    modifier: Modifier = Modifier,
    size: Int,
    difference: Int,
    beforeText: String,
    afterText: String = "",
    letterSpacing: Int,
    onClick: () -> Unit = {},
) {
    val text = remember { mutableStateOf(beforeText) }
    Circle(
        modifier = modifier
            .size((size + difference).dp),
        backGroundColor = MaterialTheme.colors.background
    )
    Circle(
        modifier = Modifier
            .size(size.dp)
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = rememberRipple(
                    color = MaterialTheme.colors.background,
                    radius = (size / 2).dp
                ),
                onClick = {
                    if (afterText.isNotEmpty()) text.value = afterText
                    onClick()
                }
            ),
        text = text.value,
        textStyle = MaterialTheme.typography.caption,
        letterSpacing = letterSpacing.sp
    )
}

@Composable
fun MemberAndColorsScrollView(
    modifier: Modifier = Modifier,
    results: List<ResultWarikan>
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        items(results) { result ->
            MemberAndColor(result = result)
        }
    }
}


