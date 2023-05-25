package fi.lipp.plugin.antonymizer.util

interface AntonymContainer<T> {
    fun get(key: T, direction: Direction): T?
}