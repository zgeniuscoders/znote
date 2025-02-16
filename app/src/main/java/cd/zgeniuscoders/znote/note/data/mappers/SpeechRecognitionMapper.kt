package cd.zgeniuscoders.znote.note.data.mappers

import cd.zgeniuscoders.znote.note.data.dto.SpeechRecognitionDto
import cd.zgeniuscoders.znote.note.domain.models.Speech

fun SpeechRecognitionDto.toSpeechModel(): Speech {
    return Speech(
        message = speech.message
    )
}