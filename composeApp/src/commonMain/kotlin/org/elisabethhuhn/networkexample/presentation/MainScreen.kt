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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import org.elisabethhuhn.networkexample.logger.Logger
import org.elisabethhuhn.networkexample.logger.LoggerState
import org.elisabethhuhn.networkexample.model.loggermodel.LoggingMsgSeverity

@Composable
fun MainScreenRoute(
    logger: Logger,
    viewModel: MainViewMode,
    onBackClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    MainScreen(
        logger = logger,
        state = state,
        onAction = { action ->
            when(action) {
                is MainAction.OnBackClick -> onBackClick()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
fun MainScreen(
    logger: Logger,
    state: MainState,
    onAction: (MainAction) -> Unit
) {

    var showContent by rememberSaveable { mutableStateOf(false) }

    //End of presentation state
    val scope = rememberCoroutineScope()

    //set up mechanism to pass the logger result state back to the viewmodel
    val firstTime by rememberSaveable { mutableStateOf(true) }

    // define lambda that will pass the Logger result state back to this viewmodel
    fun passResultState() : (LoggerState) -> Unit = { result ->
        onAction(MainAction.ReportLoggingResult(result))
    }

    //initialize the logger to return result state
    LaunchedEffect(firstTime) {
        MainAction.UpdateLogger(logger)
        logger.setReportResult(passResultState())
    }

    Column(
        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.height(80.dp))

        OutlinedTextField(
            value = if (state.loggerState.bufferDuration > 0) state.loggerState.bufferDuration.toString() else "",
            onValueChange = { enteredDuration : String ->
               MainAction.UpdateLoggerBufferDuration(enteredDuration)
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
            value = if (state.loggerState.bufferLength == 0) "" else state.loggerState.bufferLength.toString() ,
            onValueChange = { enteredLength : String ->
               MainAction.UpdateLoggerBufferLength(enteredLength)
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
            value = if (state.numberToGenerate > 0) state.numberToGenerate.toString() else "",
            onValueChange = { enteredNumToGen : String ->
                onAction(MainAction.NumberToGenerateChanged(enteredNumToGen))
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
                while (genCounter < state.numberToGenerate) {
                    showContent = !showContent
                    genCounter++
                    onAction(MainAction.IncrLogCounter)
                    onAction(MainAction.SetCurrentTimestamp)

                    //build a log message
                    val severity = when {
                        state.logCounter % 2 == 0 -> LoggingMsgSeverity.INFO
                        state.logCounter % 3 == 0 -> LoggingMsgSeverity.ERROR
                        state.logCounter % 5 == 0 -> LoggingMsgSeverity.WARNING
                        else -> LoggingMsgSeverity.DEBUG
                    }
                    val message = "Log message ${state.logCounter}"

                    //send the log message
                    logger.run {
                        val msg = message
                        val sev = severity.toString()
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
            text = "severity = ${state.severity}",
            style = MaterialTheme.typography.h6,
        )
        Text(
            text = "Timestamp = ${state.currentTimestamp}",
            style = MaterialTheme.typography.h6,
        )
        Text(
            text = state.message,
            style = MaterialTheme.typography.h6,
        )
        Spacer(modifier = Modifier.height(10.dp))
        Divider(
            color = MaterialTheme.colors.primary,
            thickness = 2.dp,
            startIndent = 0.dp
        )
        Spacer(modifier = Modifier.height(10.dp))


        if (state.loggerState.requestResult.isNotEmpty()) {
            Text(
                text = state.loggerState.requestResult,
                style = MaterialTheme.typography.h6,
            )
        }
        if (state.loggerState.errorResult.isNotEmpty()) {
            Text(
                text = state.loggerState.errorResult,
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.error
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        if (state.loggerState.lastBufferMessage.isNotEmpty()) {
            Text(
                text = state.loggerState.lastBufferMessage,
                style = MaterialTheme.typography.h6,
                color = Color.Blue
            )
        }
    }
}


