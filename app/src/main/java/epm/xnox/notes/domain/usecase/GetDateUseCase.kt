package epm.xnox.notes.domain.usecase

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

class GetDateUseCase @Inject constructor() {
    operator fun invoke(): String = SimpleDateFormat(
        "dd MMM. yyyy",
        Locale.getDefault()
    ).format(Calendar.getInstance().time)
}