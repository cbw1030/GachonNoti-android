package io.wiffy.gachonNoti.ui.main.searcher

class ClassTimePerDay {
    private val day = BooleanArray(168) { true } // 14교시

    //GET
    fun getAllDayTable(): BooleanArray = day

    fun getTableOfClassByIndex(index: Any?): BooleanArray {
        return when (index) {
            is Int? -> getTableOfClass(index ?: -1) // 교시
            is Int -> getTableOfClass(index) // 교시
            is String? -> getTableOfClass(index ?: "NULL") // 타임 (A, B, C...)
            is String -> getTableOfClass(index)// 타임 (A, B, C...)
            else -> BooleanArray(12) { false } // 1교시 (1시간기준)
        }
    }

    private fun getTableOfClass(index: Int): BooleanArray {
        return if (index in 0 until 14) {
            day.copyOfRange(index * 12, (index + 1) * 12)
        } else BooleanArray(12) { false }
    }

    private fun getTableOfClass(symbol: String): BooleanArray {
        return when {
            symbol.length == 1 -> when (symbol.toUpperCase().toCharArray()[0]) {
                in 'A'..'E' -> analysisCharacter(symbol.toUpperCase().toCharArray()[0])
                in '1'..'9' -> day.copyOfRange((symbol.toInt() - 1) * 12, symbol.toInt() * 12)
                else -> BooleanArray(15) { false }
            }
            symbol.length == 2 -> if (symbol == "10" || symbol == "11" || symbol == "12" || symbol == "13" || symbol == "14") {
                day.copyOfRange((symbol.toInt() - 1) * 12, symbol.toInt() * 12)
            } else BooleanArray(12) { false }
            else -> BooleanArray(15) { false }
        }
    }

    private fun analysisCharacter(symbol: Char): BooleanArray = when (symbol) {
        'A' -> day.copyOfRange(6, 21)
        'B' -> day.copyOfRange(24, 39)
        'C' -> day.copyOfRange(42, 57)
        'D' -> day.copyOfRange(60, 75)
        'E' -> day.copyOfRange(78, 93)
        else -> BooleanArray(15) { false }
    }
    //GET -
    //SET

//SET -
}