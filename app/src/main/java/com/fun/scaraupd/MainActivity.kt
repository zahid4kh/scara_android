package com.`fun`.scaraupd

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.EaseOutBack
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import androidx.wear.compose.material.ContentAlpha
import com.`fun`.scaraupd.ui.theme.ScaraUpdTheme
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.pow
import kotlin.math.sqrt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ScaraUpdTheme {
                App()
            }
            }
        }
    }
@Composable
@Preview
fun App() {
    ScaraUpdTheme {
        Row (horizontalArrangement = Arrangement.Center, modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .horizontalScroll(ScrollState(1), true)) {
            Column (horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                .weight(2f),
                verticalArrangement = Arrangement.SpaceAround ) {
                Inputs()
            }
            Column (horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                .weight(2f)) {
                Outputs()
            }
        }
    }
}
// Initializing results
var theta1_1 = ""
var theta1_2 = ""
var theta2_1 = ""
var theta2_2 = ""
var theta4_1 = ""
var theta4_2 = ""
var d = ""
// End
@Composable
fun Inputs(){

    var a2 by rememberSaveable { mutableStateOf("") }
    var a3 by rememberSaveable { mutableStateOf("") }
    var px by rememberSaveable { mutableStateOf("") }
    var py by rememberSaveable { mutableStateOf("") }
    var pz by rememberSaveable { mutableStateOf("") }
    var tcp by rememberSaveable { mutableStateOf("") }
    var tool by rememberSaveable { mutableStateOf("") }
    val myVals = listOf(a2, a3, px, py, pz, tcp, tool)

    if (myVals.all { it.isEmpty() }){
//        a2 = "".toDoubleOrNull().toString();
//        a3 = "".toDoubleOrNull().toString();
//        px = "".toDoubleOrNull().toString();
//        py = "".toDoubleOrNull().toString();
//        pz = "".toDoubleOrNull().toString();
//        tcp = "".toDoubleOrNull().toString();
//        tool = "".toDoubleOrNull().toString()
        a2 = ""
        a3 = ""
        px = ""
        py = ""
        pz = ""
        tcp = ""
        tool = ""
    }else{
        val pxDouble = px.toDoubleOrNull()
        val pyDouble = py.toDoubleOrNull()
        val pzDouble = pz.toDoubleOrNull()
        val a2Double = a2.toDoubleOrNull()
        val a3Double = a3.toDoubleOrNull()
        val tcpDouble = tcp.toDoubleOrNull()
        val toolDouble = tool.toDoubleOrNull()
        if (pxDouble!=null && pyDouble!=null && pzDouble!=null && a2Double!=null && a3Double!=null && tcpDouble!=null && toolDouble!=null){
            val l = sqrt(pxDouble.toDouble().pow(2) + pyDouble.toDouble().pow(2))
            val alpha_1 = atan2(pyDouble.toDouble(), pxDouble.toDouble())
            val cos_beta1 = (a2Double.toDouble().pow(2) + l.pow(2) - a3Double.toDouble().pow(2)) / (2 * a2Double.toDouble() * l)
            val sin_beta1 = sqrt(1 - cos_beta1.pow(2))
            val beta1 = atan2(sin_beta1, cos_beta1)
            val cos_fi = (a2Double.toDouble().pow(2) + a3Double.toDouble().pow(2) - l.pow(2)) / (2 * a2Double.toDouble() * a3Double.toDouble())
            val sin_fi = sqrt(1 - cos_fi.pow(2))
            val fi = atan2(sin_fi, cos_fi)
            d = (tcpDouble.toDouble() - pzDouble.toDouble()).toString()
            theta1_1 = (((alpha_1 - beta1) * 180) / PI).toString()
            theta1_2 = (((alpha_1 + beta1) * 180) / PI).toString()
            theta2_1 = (((PI - fi) * 180) / PI).toString()
            theta2_2 = (((-PI - fi) * 180) / PI).toString() // FIX
            theta4_1 = (((theta1_1.toDouble() + theta2_1.toDouble() - toolDouble.toDouble()) * 180) / PI).toString() // FIX
            theta4_2 = (((theta1_2.toDouble() + theta2_2.toDouble() - toolDouble.toDouble()) * 180) / PI).toString() //FIX
        }
    }

    Column (modifier = Modifier.verticalScroll(ScrollState(1), true).fillMaxSize().padding(horizontal = 5.dp)) {

        Text(modifier = Modifier.align(Alignment.CenterHorizontally).padding(top=50.dp),
            text = "INPUTS",
            color = MaterialTheme.colorScheme.onBackground)

        OutlinedTextField(modifier = Modifier.align(Alignment.CenterHorizontally),
            value = a2,   onValueChange = {a2 = it },   label = { Text( "Link 1") }, keyboardOptions = KeyboardOptions(keyboardType= KeyboardType.Number), maxLines = 1)
        OutlinedTextField(modifier = Modifier.align(Alignment.CenterHorizontally),
            value = a3,   onValueChange = {a3 = it},   label = { Text( "Link 2") }, keyboardOptions = KeyboardOptions(keyboardType= KeyboardType.Number), maxLines = 1)
        OutlinedTextField(modifier = Modifier.align(Alignment.CenterHorizontally),
            value = px,   onValueChange = {px = it},   label = { Text( "X coordinate") }, keyboardOptions = KeyboardOptions(keyboardType= KeyboardType.Number), maxLines = 1)
        OutlinedTextField(modifier = Modifier.align(Alignment.CenterHorizontally),
            value = py,   onValueChange = {py = it},   label = { Text( "Y coordinate") }, keyboardOptions = KeyboardOptions(keyboardType= KeyboardType.Number), maxLines = 1)
        OutlinedTextField(modifier = Modifier.align(Alignment.CenterHorizontally),
            value = pz,   onValueChange = {pz = it},   label = { Text( "Z coordinate") }, keyboardOptions = KeyboardOptions(keyboardType= KeyboardType.Number), maxLines = 1)
        OutlinedTextField(modifier = Modifier.align(Alignment.CenterHorizontally),
            value = tcp,  onValueChange = {tcp = it},  label = { Text( "TCP coordinate") }, keyboardOptions = KeyboardOptions(keyboardType= KeyboardType.Number), maxLines = 1)
        OutlinedTextField(modifier = Modifier.align(Alignment.CenterHorizontally),
            value = tool, onValueChange = {tool = it}, label = { Text( "Initial tool angle") }, keyboardOptions = KeyboardOptions(keyboardType= KeyboardType.Number), maxLines = 1)
    }
}


    @Composable
    fun Outputs(){
        Column (modifier = Modifier.verticalScroll(state = ScrollState(1), enabled = true)) {

            var expanded by rememberSaveable {mutableStateOf(false)}
            val rotationState by animateFloatAsState(targetValue = if (expanded) 180f else 0f, label = "")
            Text(modifier = Modifier
                .padding(top = 50.dp)
                .align(Alignment.CenterHorizontally),
                text = "OUTPUTS",
                color = MaterialTheme.colorScheme.onBackground)

            Card (modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .animateContentSize(animationSpec = tween(550, easing = EaseOutBack)),
                shape = RoundedCornerShape(15.dp),
                onClick = {expanded = !expanded}){

                Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp)){
                    Row(verticalAlignment = Alignment.CenterVertically){

                        Text(modifier = Modifier.weight(4f), text="Elbow-up", fontWeight = FontWeight.Bold)
                        IconButton(modifier = Modifier
                            .weight(1f)
                            .alpha(ContentAlpha.medium)
                            .rotate(rotationState),
                            onClick = { expanded = !expanded}) {
                            Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = "")
                        }
                    }
                    if (expanded){
                        OutlinedTextField(readOnly=true, value = theta1_1, onValueChange = { theta1_1 = it}, label = { Text( "Theta 1") })
                        OutlinedTextField(readOnly=true, value = theta2_1, onValueChange = { theta2_1 = it }, label = { Text( "Theta 2") })
                        OutlinedTextField(readOnly=true, value = d,        onValueChange = {d}, label = { Text( "Z-stroke") })
                        OutlinedTextField(readOnly=true, value = theta4_1, onValueChange = { theta4_1 = it}, label = { Text( "Theta 4") })
                    }
                }
            }
            var expanded2 by rememberSaveable {mutableStateOf(false)}
            val rotationState2 by animateFloatAsState(targetValue = if (expanded2) 180f else 0f, label="")
            Card (modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .animateContentSize(animationSpec = tween(550, easing = LinearOutSlowInEasing)),
                shape = RoundedCornerShape(15.dp),
                onClick = {expanded2 = !expanded2}){

                Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)){
                    Row(verticalAlignment = Alignment.CenterVertically){
                        Text(modifier = Modifier.weight(4f), text="Elbow-down", fontWeight = FontWeight.Bold)
                        IconButton(modifier = Modifier
                            .weight(1f)
                            .alpha(ContentAlpha.medium)
                            .rotate(rotationState2),
                            onClick = { expanded2 = !expanded2}) {
                            Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = "")
                        }
                    }
                    if (expanded2){
                        OutlinedTextField(readOnly=true, value = theta1_2, onValueChange = {theta1_2}, label = { Text( "Theta 1") })
                        OutlinedTextField(readOnly=true, value = theta2_2, onValueChange = {theta2_2}, label = { Text( "Theta 2") })
                        OutlinedTextField(readOnly=true, value = d, onValueChange = {d}, label = { Text( "Z-stroke") })
                        OutlinedTextField(readOnly=true, value = theta4_2, onValueChange = {theta4_2}, label = { Text( "Theta 4") })
                    }
                }

            }
        }
    }





