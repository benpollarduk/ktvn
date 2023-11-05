package com.github.benpollarduk.ktvn.logic

/**
 * Provides a mechanism for setting and getting flags.
 */
public class Flags {
    private val values: MutableMap<String, Boolean> = mutableMapOf()

    /**
     * Get a flag with a specified [name].
     */
    public operator fun get(name: String): Boolean {
        return getFlag(name)
    }

    /**
     * Sets a flag with a specified [name] to a specified [value].
     */
    public operator fun set(name: String, value: Boolean) {
        setFlag(name, value)
    }

    /**
     * Set a [flag] to a [value]. If the [value] is not specified it will default to true.
     */
    public fun setFlag(flag: String, value: Boolean = true) {
        values[flag] = value
    }

    /**
     * Get the value of [flag].
     */
    public fun getFlag(flag: String): Boolean {
        values.putIfAbsent(flag, false)
        return values[flag] ?: false
    }

    public companion object {
        /**
         * Provides an empty implementation of [Flags].
         */
        public val empty: Flags = Flags()
    }
}
