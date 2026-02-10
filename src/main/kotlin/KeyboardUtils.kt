import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton

fun mahallaKeyboard(): InlineKeyboardMarkup {
    val rows = mutableListOf<List<InlineKeyboardButton>>()

    Mahalla.list.chunked(2).forEach { pair ->
        val row = pair.map { name ->
            InlineKeyboardButton().apply {
                text = name
                callbackData = "MAHALLA_$name"
            }
        }
        rows.add(row)
    }

    return InlineKeyboardMarkup(rows)
}
