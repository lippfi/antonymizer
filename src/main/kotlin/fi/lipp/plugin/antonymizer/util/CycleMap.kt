package fi.lipp.plugin.antonymizer.util

class CycleMap<T>(private val entries: Array<Array<T>>): AntonymContainer<T> {
    override fun get(key: T, direction: Direction): T? {
        for (entry in entries) {
            val index = entry.indexOf(key)
            if (index == -1) continue
            return entry[(index + direction.toInt()).mod(entry.size)]
        }
        return null
    }
}