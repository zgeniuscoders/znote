package cd.zgeniuscoders.znote.note.data.dto

data class SpeechRecognitionDto(
    val error: String? = null,
    val speech: SpeechRecognitionDtoData
)

data class SpeechRecognitionDtoData(
    val message: String
)