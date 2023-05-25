package fi.lipp.plugin.antonymizer.util

internal class BiMap<T>(private val original: Map<T, T>): AntonymContainer<T> {
    private val reversed: HashMap<T, T> = HashMap()

    init {
        reversed.putAll(original.map { it.value to it.key })
    }
    
    override fun get(key: T, direction: Direction): T? {
        return original[key] ?: reversed[key]
    }
}