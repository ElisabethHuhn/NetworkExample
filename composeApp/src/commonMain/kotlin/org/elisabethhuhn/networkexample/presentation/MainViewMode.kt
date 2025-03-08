package org.elisabethhuhn.networkexample.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.elisabethhuhn.networkexample.logger.Logger
import org.elisabethhuhn.networkexample.logger.LoggerState
import org.elisabethhuhn.networkexample.model.loggermodel.LoggingMsgSeverity
import org.elisabethhuhn.networkexample.util.formatCurrentTimestamp

class MainViewMode () : ViewModel() {

    /* ********************************************************************
     * State values displayed in MainScreen UI
     */
    private val _state = MutableStateFlow(MainState())
    //if there needs to be initialization, use ,onStart{} and .stateIn{}
    val state = _state.asStateFlow()


    /*
     * actions triggered by User in MainScreen UI
     */
    fun onAction(action: MainAction) {
        when(action) {
            is MainAction.NumberToGenerateChanged -> {
                updateNumberToGenerate(action.number)
            }
            is MainAction.UpdateLogger -> {
                updateLogger(action.logger)
            }
            is MainAction.IncrLogCounter -> {
                incrLogCounter()
            }
            is MainAction.SetCurrentTimestamp -> {
                updateTimestamp()
            }
            is MainAction.UpdateLoggerBufferLength -> {
                updateLoggerBufferLength(action.lengthString)
            }
            is MainAction.UpdateLoggerBufferDuration -> {
                updateLoggerBufferDuration(action.durationString)
            }
        }
    }

    /*
     * functions to update the UI State values displayed in MainScreen UI
     */
    fun updateIsLoading(isLoading: Boolean) {
        _state.update { it.copy(isLoading = isLoading) }
    }

    private fun updateLogger(logger: Logger) {
        _state.update { it.copy(logger = logger) }
    }

    private fun updateTimestamp() {
        val timestamp = formatCurrentTimestamp()
        _state.update { it.copy(currentTimestamp = timestamp) }
    }

    private fun incrLogCounter() {
        val newCounter = _state.value.logCounter + 1
        _state.update { it.copy(logCounter = newCounter) }
    }

    private fun updateNumberToGenerate(newCounter: String) {
        val numberToGenerate = if (newCounter != "") {
            try{
                newCounter.toInt()
            } catch (e: NumberFormatException) {
                0
            }
        } else {
            0
        }
        _state.update { it.copy(numberToGenerate = numberToGenerate) }
    }

    fun updateSeverity(newSeverity: LoggingMsgSeverity) {
        _state.update { it.copy(severity = newSeverity) }
    }

    fun updateLogMessage(newMessage: String) {
        _state.update { it.copy(message = newMessage) }
    }
    private fun updateLoggerBufferLength(lengthString: String) {
        var length = 0
        if (lengthString != "") {
            try{
                length = lengthString.toInt()
            } catch (e: NumberFormatException) {
                length = 0
            }
        } else {
            length = 0
        }
        _state.value.logger?.setBufferLength(length)
    }
    private fun updateLoggerBufferDuration(durationString: String) {
        var duration = 0L
        if (durationString != "") {
            try{
                duration = durationString.toLong()
            } catch (e: NumberFormatException) {
                duration = 0L
            }
        } else {
            duration = 0L
        }
        _state.value.logger?.setBufferDuration(duration)
    }

    /* ********************************************************************
     * State of Logging message
     */
    private val _loggerState = MutableStateFlow<LoggerState>(LoggerState())
    val loggerState = _loggerState.asStateFlow()

    /*
  * functions to update the Logging State values
  */
    fun updateLoggingState(loggingState: LoggerState) {
        _loggerState.update { loggingState }
    }



    /*
     * functions to carry out the UI actions triggered by User in MainScreen UI
     */


}