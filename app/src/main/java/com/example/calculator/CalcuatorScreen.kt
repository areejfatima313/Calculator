package com.example.calculator

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ---------------------------------------------------------
// 2. THEME COLORS
// ---------------------------------------------------------
val PinkBackground = Color(0xFFFFF0F5)
val PinkDisplayBg = Color(0xFFFFF5F9)
val PinkButtonBg = Color(0xFFFFD1E0)
val PinkOperatorBg = Color(0xFFE94E86)
val PinkText = Color(0xFF8A1C3C)

val WhiteBackground = Color(0xFFFFFFFF)
val WhiteDisplayBg = Color(0xFFF8F8F8)
val WhiteButtonBg = Color(0xFFFFFFFF)
val WhiteOperatorBg = Color(0xFFE94E86)
val WhiteText = Color(0xFF333333)

// ---------------------------------------------------------
// 3. MAIN CALCULATOR SCREEN
// ---------------------------------------------------------
@Composable
fun CalculatorScreen() {
    var isPinkTheme by remember { mutableStateOf(true) }
    var expression by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("0") }

    val bgColor = if (isPinkTheme) PinkBackground else WhiteBackground
    val displayBg = if (isPinkTheme) PinkDisplayBg else WhiteDisplayBg
    val buttonBg = if (isPinkTheme) PinkButtonBg else WhiteButtonBg
    val textColor = if (isPinkTheme) PinkText else WhiteText

    // Calculation Logic (Simple Left-to-Right)
    fun calculate(exp: String): String {
        if (exp.isEmpty()) return "0"
        if (exp.last() in "+-×÷.") return result

        return try {
            val cleanExp = exp.replace("×", "*").replace("÷", "/").replace("−", "-")
            var answer = 0.0
            var currentNum = ""
            var currentOp = '+'

            for (char in cleanExp) {
                if (char in "+-*/") {
                    if (currentNum.isNotEmpty()) {
                        val num = currentNum.toDouble()
                        when (currentOp) {
                            '+' -> answer += num
                            '-' -> answer -= num
                            '*' -> answer *= num
                            '/' -> answer /= num
                        }
                    }
                    currentOp = char
                    currentNum = ""
                } else {
                    currentNum += char
                }
            }
            if (currentNum.isNotEmpty()) {
                val num = currentNum.toDouble()
                when (currentOp) {
                    '+' -> answer += num
                    '-' -> answer -= num
                    '*' -> answer *= num
                    '/' -> answer /= num
                }
            }

            if (answer % 1.0 == 0.0) answer.toInt().toString()
            else String.format("%.2f", answer)
        } catch (e: Exception) {
            "Error"
        }
    }

    Surface(modifier = Modifier.fillMaxSize(), color = bgColor) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // --- TOP BAR ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("☰", color = textColor, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Text(
                    text = if (isPinkTheme) "☾" else "☀",
                    color = if (isPinkTheme) PinkOperatorBg else WhiteText,
                    fontSize = 25.sp,
                    modifier = Modifier.clickable { isPinkTheme = !isPinkTheme }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // --- DISPLAY AREA ---
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .shadow(2.dp, RoundedCornerShape(32.dp))
                    .clip(RoundedCornerShape(32.dp))
                    .background(displayBg)
                    .padding(24.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                Column(horizontalAlignment = Alignment.End) {
                    // Expression Text
                    Text(
                        text = if (expression.isEmpty()) "0" else expression,
                        fontSize = 24.sp,
                        color = textColor.copy(alpha = 0.7f)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    // Result Text
                    Text(
                        text = result,
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold,
                        color = textColor
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // --- KEYPAD ---
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                // Row 1
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    CalcButton("C", Modifier.weight(1f), false, isPinkTheme, buttonBg, textColor, PinkOperatorBg) {
                        expression = ""; result = "0"
                    }
                    CalcButton("⌫", Modifier.weight(1f), false, isPinkTheme, buttonBg, textColor, PinkOperatorBg) {
                        if (expression.isNotEmpty()) {
                            expression = expression.dropLast(1)
                            result = calculate(expression)
                        }
                    }
                    CalcButton("÷", Modifier.weight(1f), false, isPinkTheme, buttonBg, textColor, PinkOperatorBg) {
                        if (expression.isNotEmpty() && expression.last() !in "+-×÷") expression += "÷"
                    }
                    CalcButton("×", Modifier.weight(1f), true, isPinkTheme, buttonBg, textColor, PinkOperatorBg) {
                        if (expression.isNotEmpty() && expression.last() !in "+-×÷") expression += "×"
                    }
                }
                // Row 2
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    CalcButton("7", Modifier.weight(1f), false, isPinkTheme, buttonBg, textColor, PinkOperatorBg) { expression += "7"; result = calculate(expression) }
                    CalcButton("8", Modifier.weight(1f), false, isPinkTheme, buttonBg, textColor, PinkOperatorBg) { expression += "8"; result = calculate(expression) }
                    CalcButton("9", Modifier.weight(1f), false, isPinkTheme, buttonBg, textColor, PinkOperatorBg) { expression += "9"; result = calculate(expression) }
                    CalcButton("−", Modifier.weight(1f), true, isPinkTheme, buttonBg, textColor, PinkOperatorBg) {
                        if (expression.isNotEmpty() && expression.last() !in "+-×÷") expression += "−"
                    }
                }
                // Row 3
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    CalcButton("4", Modifier.weight(1f), false, isPinkTheme, buttonBg, textColor, PinkOperatorBg) { expression += "4"; result = calculate(expression) }
                    CalcButton("5", Modifier.weight(1f), false, isPinkTheme, buttonBg, textColor, PinkOperatorBg) { expression += "5"; result = calculate(expression) }
                    CalcButton("6", Modifier.weight(1f), false, isPinkTheme, buttonBg, textColor, PinkOperatorBg) { expression += "6"; result = calculate(expression) }
                    CalcButton("+", Modifier.weight(1f), true, isPinkTheme, buttonBg, textColor, PinkOperatorBg) {
                        if (expression.isNotEmpty() && expression.last() !in "+-×÷") expression += "+"
                    }
                }

                // Row 4 & 5
                Row(
                    modifier = Modifier.height(140.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Column(
                        modifier = Modifier.weight(3f),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.weight(1f)) {
                            CalcButton("1", Modifier.weight(1f), false, isPinkTheme, buttonBg, textColor, PinkOperatorBg) { expression += "1"; result = calculate(expression) }
                            CalcButton("2", Modifier.weight(1f), false, isPinkTheme, buttonBg, textColor, PinkOperatorBg) { expression += "2"; result = calculate(expression) }
                            CalcButton("3", Modifier.weight(1f), false, isPinkTheme, buttonBg, textColor, PinkOperatorBg) { expression += "3"; result = calculate(expression) }
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.weight(1f)) {
                            CalcButton("0", Modifier.weight(2f), false, isPinkTheme, buttonBg, textColor, PinkOperatorBg) { expression += "0"; result = calculate(expression) }
                            CalcButton(".", Modifier.weight(1f), false, isPinkTheme, buttonBg, textColor, PinkOperatorBg) {
                                val lastNum = expression.split(Regex("[+\\-×÷]")).lastOrNull() ?: ""
                                if (!lastNum.contains(".")) expression += "."
                            }
                        }
                    }
                    CalcButton(
                        text = "=",
                        modifier = Modifier.weight(1f).fillMaxHeight(),
                        isOperator = true,
                        isPinkTheme = isPinkTheme,
                        buttonBg = buttonBg,
                        textColor = textColor,
                        operatorBg = PinkOperatorBg
                    ) {
                        if (expression.isNotEmpty()) {
                            result = calculate(expression)
                            expression = ""
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // --- BOTTOM DECORATION (Hearts) ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("♥", color = PinkOperatorBg, fontSize = 20.sp)

                Text("♡──────♡──────♡", color = PinkOperatorBg, fontSize = 14.sp)

                Text("♥", color = PinkOperatorBg, fontSize = 20.sp)
            }
        }
    }
}

// ---------------------------------------------------------
// 4. REUSABLE CALCULATOR BUTTON
// ---------------------------------------------------------
@Composable
fun CalcButton(
    text: String,
    modifier: Modifier = Modifier,
    isOperator: Boolean,
    isPinkTheme: Boolean,
    buttonBg: Color,
    textColor: Color,
    operatorBg: Color,
    onClick: () -> Unit
) {
    val bgColor = if (isOperator) operatorBg else buttonBg
    val txtColor = if (isOperator) Color.White else textColor

    val shape = when {
        text == "=" -> RoundedCornerShape(32.dp)
        text == "0" -> RoundedCornerShape(50)
        isOperator && text != "C" && text != "⌫" -> RoundedCornerShape(24.dp)
        else -> CircleShape
    }

    Box(
        modifier = modifier
            .height(72.dp)
            .shadow(3.dp, shape)
            .clip(shape)
            .background(bgColor)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = when (text) {
                "⌫" -> 24.sp
                "÷", "×", "−", "+" -> 30.sp
                "=" -> 36.sp
                else -> 28.sp
            },
            fontWeight = FontWeight.Medium,
            color = txtColor
        )
    }
}

// ---------------------------------------------------------
// 5. PREVIEW
// ---------------------------------------------------------
@Preview(showBackground = true, widthDp = 400, heightDp = 850)
@Composable
fun CalculatorPreview() {
    CalculatorScreen()
}