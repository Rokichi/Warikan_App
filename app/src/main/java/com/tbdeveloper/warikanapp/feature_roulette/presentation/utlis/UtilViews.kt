package com.tbdeveloper.warikanapp.feature_roulette.presentation.utlis

import android.view.KeyEvent
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ShadowButton(
    text: String = "",
    backGroundColor: Color = MaterialTheme.colors.background,
    borderColor: Color = MaterialTheme.colors.surface,
    textStyle: androidx.compose.ui.text.TextStyle = MaterialTheme.typography.button,
    modifier: Modifier = Modifier,
    padding: Int = 15,
    offsetY: Dp = 3.dp,
    offsetX: Dp = (-1).dp,
    onClick: () -> Unit,
) {
    Box(
    )
    {
        Box(
            modifier = Modifier
                .offset(
                    x = offsetX,
                    y = offsetY
                )
                .clip(RoundedCornerShape(10.dp))
                .background(Color.Black.copy(alpha = 0.15f))
        )
        Card(
            //elevation = 3.dp,
            backgroundColor = backGroundColor,
            border = BorderStroke(1.dp, borderColor),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier,
        ) {
            Box(
                Modifier.clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = rememberRipple(color = Color.Black, bounded = true),
                    onClick = onClick
                )
            ) {
                Text(
                    text = text,
                    textAlign = TextAlign.Center,
                    modifier = modifier.padding(horizontal = padding.dp),
                    style = textStyle,
                    color = MaterialTheme.colors.surface
                )
            }
        }
    }
}

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    maxLength: Int? = null,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    placeholderText: String = "Placeholder",
    color :Color = Color.LightGray,
    fontSize: TextUnit = MaterialTheme.typography.body1.fontSize,
    text: String = "",
    width: Dp = 150.dp,
    height: Dp = 50.dp,
    offsetX: Dp = 0.dp,
    offsetY: Dp = (0).dp,
    onValueChange: (String) -> Unit,
    cursorHeight: Float = 0.8f,
    isOnlyNum: Boolean = false,
) {
    Box(contentAlignment = Alignment.Center, modifier = modifier.padding(bottom = 5.dp)) {
        val tmp = text
        var text by rememberSaveable { mutableStateOf(text) }
        text = tmp

        val focusManager = LocalFocusManager.current
        val selectionColors = TextSelectionColors(
            handleColor = MaterialTheme.colors.surface,
            backgroundColor = Color.Black.copy(alpha = 0.2f)
        )
        CompositionLocalProvider(LocalTextSelectionColors provides selectionColors) {
            BasicTextField(
                modifier = Modifier
                    .background(
                        MaterialTheme.colors.background,
                        RoundedCornerShape(0.dp),
                    )
                    .border(1.dp, color, RoundedCornerShape(5.dp))
                    .height(height)
                    .width(width)
                    .offset(
                        x = offsetX,
                        y = offsetY
                    )
                    .onKeyEvent {
                        if (it.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_ENTER
                        ) {
                            focusManager.clearFocus()
                            true
                        }
                        false
                    },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = if (isOnlyNum) KeyboardType.NumberPassword else KeyboardType.Text
                ),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }
                ),
                value = text,
                onValueChange = {
                    var input = ""
                    if (maxLength == null) input = it
                    else input = it.take(maxLength)
                    onValueChange(input)
                    text = input
                },
                singleLine = true,
                cursorBrush = Brush.verticalGradient(
                    0.00f to Color.Transparent,
                    1 - cursorHeight + 0.05f to Color.Transparent,
                    1 - cursorHeight + 0.05f to MaterialTheme.colors.surface,
                    cursorHeight + 0.05f to MaterialTheme.colors.surface,
                    cursorHeight + 0.05f to Color.Transparent,
                    1.00f to Color.Transparent
                ),
                textStyle = LocalTextStyle.current.copy(
                    color = MaterialTheme.colors.surface,
                    fontSize = fontSize,
                    textAlign = TextAlign.Center
                ),
                decorationBox = { innerTextField ->
                    Row(
                        modifier,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (leadingIcon != null) leadingIcon()
                        Box(
                            modifier = Modifier.weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            if (text.isEmpty()) Text(
                                placeholderText,
                                textAlign = TextAlign.Center,
                                style = LocalTextStyle.current.copy(
                                    color = MaterialTheme.colors.surface.copy(alpha = 0.5f),
                                    fontSize = fontSize,
                                )
                            )
                            innerTextField()
                        }
                        if (trailingIcon != null) trailingIcon()
                    }
                },
            )
        }
    }
}

@Composable
fun Triangle(
    modifier: Modifier = Modifier
) {
    val triangleShape = GenericShape { size, _ ->
        moveTo(size.width / 2f, 0f)
        lineTo(size.width, size.height)
        lineTo(0f, size.height)
    }

    Box(
        modifier = modifier
            .size(100.dp)
            .clip(RoundedCornerShape(50.dp))
            .clip(triangleShape)
            .background(Color.LightGray)
    )
}

@Composable
fun Circle(
    modifier: Modifier = Modifier,
    backGroundColor: Color = MaterialTheme.colors.surface,
    text: String = "",
    textColor: Color = MaterialTheme.colors.background,
    textStyle: androidx.compose.ui.text.TextStyle = MaterialTheme.typography.body1,
    letterSpacing: TextUnit = TextUnit.Unspecified
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(backGroundColor),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = modifier.wrapContentHeight(),
            text = text,
            color = textColor,
            style = textStyle,
            textAlign = TextAlign.Center,
            letterSpacing = letterSpacing
        )
    }
}

@Composable
fun FunShape(
    modifier: Modifier,
    backGroundColor: Color,
    startAngle: Float,
    sweepAngle: Float,
    isBordered: Boolean = false,
    borderColor: Color = MaterialTheme.colors.background,
) {
    Box(contentAlignment = Alignment.Center) {
        Canvas(
            modifier = modifier
        ) {
            drawArc(
                color = backGroundColor,
                startAngle = (270f + startAngle) % 360f,
                sweepAngle = sweepAngle,
                useCenter = true,
            )
        }
        if (isBordered) {
            Canvas(
                modifier = modifier
            ) {
                drawArc(
                    color = borderColor,
                    startAngle = (270f + startAngle) % 360f,
                    sweepAngle = sweepAngle,
                    useCenter = true,
                    style = Stroke(5f),
                )
            }
        }
    }
}

@Composable
fun NoRippleDropdownMenuItem(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues = MenuDefaults.DropdownMenuItemContentPadding,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit
) {
    NoRippleDropdownMenuItemContent(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        contentPadding = contentPadding,
        interactionSource = interactionSource,
        content = content
    )
}


@Composable
internal fun NoRippleDropdownMenuItemContent(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues = MenuDefaults.DropdownMenuItemContentPadding,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit
) {
    val DropdownMenuItemDefaultMinWidth = 112.dp
    val DropdownMenuItemDefaultMaxWidth = 280.dp
    val DropdownMenuItemDefaultMinHeight = 48.dp
    // TODO(popam, b/156911853): investigate replacing this Row with ListItem
    Row(
        modifier = modifier
            .clickable(
                enabled = enabled,
                onClick = onClick,
                interactionSource = interactionSource,
                indication = null
            )
            .fillMaxWidth()
            // Preferred min and max width used during the intrinsic measurement.
            .sizeIn(
                minWidth = DropdownMenuItemDefaultMinWidth,
                maxWidth = DropdownMenuItemDefaultMaxWidth,
                minHeight = DropdownMenuItemDefaultMinHeight
            )
            .padding(contentPadding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val typography = MaterialTheme.typography
        ProvideTextStyle(typography.subtitle1) {
            val contentAlpha = if (enabled) ContentAlpha.high else ContentAlpha.disabled
            CompositionLocalProvider(LocalContentAlpha provides contentAlpha) {
                content()
            }
        }
    }
}

@Composable
fun AutoResizeText(
    text: String,
    fontSizeRange: FontSizeRange,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    style: TextStyle = LocalTextStyle.current,
) {
    var fontSizeValue by remember { mutableStateOf(fontSizeRange.max.value) }
    var readyToDraw by remember { mutableStateOf(false) }

    Text(
        text = text,
        color = color,
        maxLines = maxLines,
        fontStyle = fontStyle,
        fontWeight = fontWeight,
        fontFamily = fontFamily,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = lineHeight,
        overflow = overflow,
        softWrap = softWrap,
        style = style,
        fontSize = fontSizeValue.sp,
        onTextLayout = {
            if (it.didOverflowHeight && !readyToDraw) {
                val nextFontSizeValue = fontSizeValue - fontSizeRange.step.value
                if (nextFontSizeValue <= fontSizeRange.min.value) {
                    // Reached minimum, set minimum font size and it's readToDraw
                    fontSizeValue = fontSizeRange.min.value
                    readyToDraw = true
                } else {
                    // Text doesn't fit yet and haven't reached minimum text range, keep decreasing
                    fontSizeValue = nextFontSizeValue
                }
            } else {
                // Text fits before reaching the minimum, it's readyToDraw
                readyToDraw = true
            }
        },
        modifier = modifier.drawWithContent { if (readyToDraw) drawContent() }
    )
}

data class FontSizeRange(
    val min: TextUnit,
    val max: TextUnit,
    val step: TextUnit = DEFAULT_TEXT_STEP,
) {
    init {
        require(min < max) { "min should be less than max, $this" }
        require(step.value > 0) { "step should be greater than 0, $this" }
    }

    companion object {
        private val DEFAULT_TEXT_STEP = 1.sp
    }
}
