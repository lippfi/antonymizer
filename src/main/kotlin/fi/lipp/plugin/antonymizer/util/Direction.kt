package fi.lipp.plugin.antonymizer.util

enum class Direction(private val step: Int) {
    FORWARD(1),
    BACKWARD(-1);
    
    fun toInt(): Int {
        return step
    }
}