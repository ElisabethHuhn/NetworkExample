package org.elisabethhuhn.networkexample.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import org.elisabethhuhn.networkexample.logger.Logger
import org.elisabethhuhn.networkexample.util.formatCurrentTimestamp


@Composable
fun MainScreen(logger: Logger) {

    //Normally this state info would be in the viewModel, but for this abbreviated architecture it is here
    var showContent by remember { mutableStateOf(false) }

    var currentTimestamp by remember { mutableStateOf("NO TIMESTAMP YET") }
    var logCounter by remember { mutableIntStateOf(0) }
    var numberToGenerate by remember { mutableIntStateOf(1) }

    val loggerState by logger.loggerState.collectAsStateWithLifecycle()

    var severity by remember {
        mutableStateOf("NOT ASSIGNED")
    }
    var message by remember {
        mutableStateOf("NO MESSAGE YET")
    }

    //End of presentation state
    val scope = rememberCoroutineScope()

    Column(
        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.height(80.dp))

        OutlinedTextField(
            value = if (loggerState.bufferDuration > 0) loggerState.bufferDuration.toString() else "",
            onValueChange = { enteredDuration : String ->
                //Normally we would call an action in the ViewModel to respond to this user trigger
                if (enteredDuration != "") {
                    try{
                        val bufferDuration = enteredDuration.toLong()
                        logger.setBufferDuration(bufferDuration)
                    } catch (e: NumberFormatException) {
                        logger.setBufferDuration(0)
                    }
                } else {
                    logger.setBufferDuration(0)
                }
                //end of user action
            },
            label = {
                Text(
                    text = "Max Log Buffer Duration MS",
                    style = MaterialTheme.typography.h6,
                    )
            }
        )

        Spacer(modifier = Modifier.height(15.dp))
        OutlinedTextField(
            value = if (loggerState.bufferLength == 0) "" else loggerState.bufferLength.toString() ,
            onValueChange = { enteredLength : String ->
                if (enteredLength != "") {
                    try{
                        val bufferLength = enteredLength.toInt()
                        logger.setBufferLength(bufferLength)
                    } catch (e: NumberFormatException) {
                        logger.setBufferLength(0)
                    }
                } else {
                    logger.setBufferLength(0)
                }
            },
            label = {
                Text(
                    text = "Max Log Buffer Length",
                    style = MaterialTheme.typography.h6,
                )
            }
        )
        Spacer(modifier = Modifier.height(15.dp))

        OutlinedTextField(
            value = if (numberToGenerate > 0) numberToGenerate.toString() else "",
            onValueChange = { enteredNumToGen : String ->
                numberToGenerate = if (enteredNumToGen != "") {
                    try{
                        enteredNumToGen.toInt()
                    } catch (e: NumberFormatException) {
                        0
                    }
                } else {
                    0
                }
            },
            label = {
                Text(
                    text = "Number log messages to generate",
                    style = MaterialTheme.typography.h6,
                )
            }
        )
        Spacer(modifier = Modifier.height(10.dp))


        Button(
            onClick = {
                //In a normal architecture, this would be hidden behind a ViewModel action
                // that calls a repository function
                var genCounter = 0
                while (genCounter < numberToGenerate) {
                    showContent = !showContent
                    genCounter++
                    logCounter++
                    currentTimestamp = formatCurrentTimestamp()
                    severity = when {
                            logCounter % 2 == 0 -> "INFO"
                            logCounter % 3 == 0 -> "ERROR"
                            logCounter % 5 == 0 -> "WARN"
                            else -> "DEBUG"
                        }
                    message = "Log message $logCounter"

                    logger.run {
                        val msg = message
                        val sev = severity
                        scope.launch {
                            log(msg = msg, severity = sev)
                        }
                    }
                }
            }
        ) {
            Text(
                text = "Log message(s)",
                style = MaterialTheme.typography.button,
            )
        }

        Button(
            onClick = {
                logger.run {
                    scope.launch {
                        flushBuffer()
                    }
                }
            }
        ) {
            Text(
                text = "Flush Buffer",
                style = MaterialTheme.typography.button,
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Divider(
            color = MaterialTheme.colors.primary,
            thickness = 2.dp,
            startIndent = 0.dp
        )
        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Last Log Entry: ",
            style = MaterialTheme.typography.h6,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "severity = $severity",
            style = MaterialTheme.typography.h6,
        )
        Text(
            text = "Timestamp = $currentTimestamp",
            style = MaterialTheme.typography.h6,
        )
        Text(
            text = message,
            style = MaterialTheme.typography.h6,
        )
        Spacer(modifier = Modifier.height(10.dp))
        Divider(
            color = MaterialTheme.colors.primary,
            thickness = 2.dp,
            startIndent = 0.dp
        )
        Spacer(modifier = Modifier.height(10.dp))


        if (loggerState.requestResult.isNotEmpty()) {
            Text(
                text = loggerState.requestResult,
                style = MaterialTheme.typography.h6,
            )
        }
        if (loggerState.errorResult.isNotEmpty()) {
            Text(
                text = loggerState.errorResult,
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.error
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        if (loggerState.lastBufferMessage.isNotEmpty()) {
            Text(
                text = loggerState.lastBufferMessage,
                style = MaterialTheme.typography.h6,
                color = Color.Blue
            )
        }
    }
}