package ru.androidlearning.moviesearch.model

import java.text.SimpleDateFormat
import java.util.*

data class MoveDetails(
    val move: Move = getDefaultMove(),
    val genre: String? = "Триллер/криминал",
    val durationInMinutes: Int? = 154,
    val description: String? = "Двое бандитов Винсент Вега и Джулс Винфилд проводят время в философских беседах " +
            "в перерыве между разборками и «решением проблем» с должниками своего криминального босса " +
            "Марселласа Уоллеса. Параллельно разворачивается три истории. В первой из них Винсент присматривает " +
            "за женой Марселласа Мией и спасает ее от передозировки наркотиков. Во второй рассказывается о Бутче Кулидже," +
            " боксере, нанятом Уоллесом, чтобы сдать бой, но обманувшим его."
) {
    companion object {
        private fun getDefaultMove(): Move =
            Move("Криминальное чтиво", getDateFromString("1995/12/27"), 80f, null)

        private fun getDateFromString(dateStr: String): Date? {
            val sdf = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
            return sdf.parse(dateStr)
        }
    }
}
