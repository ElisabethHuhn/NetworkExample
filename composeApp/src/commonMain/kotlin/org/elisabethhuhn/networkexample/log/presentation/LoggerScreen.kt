package org.elisabethhuhn.networkexample.log.presentation

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun MainScreenRoute(

    viewModel: LoggerViewModel,
    onBackClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    MainScreen(

        state = state,
        onAction = { action ->
            when(action) {
                is LoggerAction.OnBackClick -> onBackClick()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
fun MainScreen(
    state: LoggerState,
    onAction: (LoggerAction) -> Unit
) {
    Column(
        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        SendBufferConfig(state)

        SendBufferTriggers(state, onAction)

        LastMessages(state)

        SentBufferStats(state)
    }
}


@Composable
private fun SendBufferConfig(state: LoggerState) {
    Spacer(modifier = Modifier.height(80.dp))

    OutlinedTextField(
        value = if (state.bufferDuration > 0) state.bufferDuration.toString() else "",
        onValueChange = { enteredDuration: String ->
            LoggerAction.UpdateLoggerBufferDuration(enteredDuration)
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
        value = if (state.bufferLength == 0) "" else state.bufferLength.toString(),
        onValueChange = { enteredLength: String ->
            LoggerAction.UpdateLoggerBufferLength(enteredLength)
        },
        label = {
            Text(
                text = "Max Log Buffer Length",
                style = MaterialTheme.typography.h6,
            )
        }
    )
}

@Composable
private fun SendBufferTriggers(
    state: LoggerState,
    onAction: (LoggerAction) -> Unit
) {
    Spacer(modifier = Modifier.height(15.dp))

    OutlinedTextField(
        value = if (state.numberToGenerate > 0) state.numberToGenerate.toString() else "",
        onValueChange = { enteredNumToGen: String ->
            onAction(LoggerAction.UpdateNumberToGenerateChanged(enteredNumToGen))
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
            onAction(LoggerAction.GenerateLogMessages)
        }
    ) {
        Text(
            text = "Log message(s)",
            style = MaterialTheme.typography.button,
        )
    }

    Button(
        onClick = {
            onAction(LoggerAction.FlushLogBuffer)
        }
    ) {
        Text(
            text = "Flush Buffer",
            style = MaterialTheme.typography.button,
        )
    }
}

@Composable
private fun LastMessages(state: LoggerState) {
    Spacer(modifier = Modifier.height(10.dp))
    Divider(
        color = MaterialTheme.colors.primary,
        thickness = 2.dp,
        startIndent = 0.dp
    )
    Spacer(modifier = Modifier.height(10.dp))

    Text(
        text = "Last Log Entry Created: ",
        style = MaterialTheme.typography.h6,
        fontWeight = FontWeight.Bold
    )

    Text(
        text = "severity = ${state.lastBuffered.severity}",
        style = MaterialTheme.typography.h6,
    )
    Text(
        text = "Timestamp = ${state.lastBuffered.timestamp}",
        style = MaterialTheme.typography.h6,
    )
    Text(
        text = state.lastBuffered.message,
        style = MaterialTheme.typography.h6,
    )
    Spacer(modifier = Modifier.height(10.dp))

    Text(
        text = "Last Log Entry Sent: ",
        style = MaterialTheme.typography.h6,
        fontWeight = FontWeight.Bold
    )

    Text(
        text = "severity = ${state.lastSent.severity}",
        style = MaterialTheme.typography.h6,
    )
    Text(
        text = "Timestamp = ${state.lastSent.timestamp}",
        style = MaterialTheme.typography.h6,
    )
    Text(
        text = state.lastSent.message,
        style = MaterialTheme.typography.h6,
    )
}

@Composable
private fun SentBufferStats(state: LoggerState) {
    Spacer(modifier = Modifier.height(10.dp))
    Divider(
        color = MaterialTheme.colors.primary,
        thickness = 2.dp,
        startIndent = 0.dp
    )
    Spacer(modifier = Modifier.height(10.dp))

    Text(
        text = "Buffer Sent Stats: ",
        style = MaterialTheme.typography.h6,
        fontWeight = FontWeight.Bold
    )

    Text(
        text = "total sent = ${state.totalSent}",
        style = MaterialTheme.typography.h6,
    )
    Text(
        text = "total queued = ${state.totalQueued}",
        style = MaterialTheme.typography.h6,
    )
    Text(
        text = "total Success = ${state.totalSuccess}",
        style = MaterialTheme.typography.h6,
    )
    Text(
        text = "total Error = ${state.totalError}",
        style = MaterialTheme.typography.h6,
    )
}









