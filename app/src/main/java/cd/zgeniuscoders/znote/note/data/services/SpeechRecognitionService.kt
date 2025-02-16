package cd.zgeniuscoders.znote.note.data.services

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import cd.zgeniuscoders.znote.Resource
import cd.zgeniuscoders.znote.note.data.dto.SpeechRecognitionDto
import cd.zgeniuscoders.znote.note.data.dto.SpeechRecognitionDtoData
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class SpeechRecognitionService(
    val context: Context,
) {


    fun startSpeechRecognition(): Flow<Resource<SpeechRecognitionDto>> = callbackFlow {
        val sp = SpeechRecognizer.createSpeechRecognizer(context)
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "fr-FR")
        }

        sp.setRecognitionListener(object : android.speech.RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {

            }

            override fun onBeginningOfSpeech() {

            }

            override fun onRmsChanged(rmsdB: Float) {

            }

            override fun onBufferReceived(buffer: ByteArray?) {

            }

            override fun onEndOfSpeech() {

            }

            override fun onError(error: Int) {
                trySend(
                    Resource.Error(message = "Erreur de reconnaissance vocale")
                )
            }

            override fun onResults(results: Bundle?) {
                val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (!matches.isNullOrEmpty()) {
                    trySend(
                        Resource.Success(
                            SpeechRecognitionDto(
                                speech = SpeechRecognitionDtoData(matches[0])
                            )
                        )
                    )
                }
            }

            override fun onPartialResults(partialResults: Bundle?) {

            }

            override fun onEvent(eventType: Int, params: Bundle?) {

            }

        })

        sp.startListening(intent)

        awaitClose()
    }


}