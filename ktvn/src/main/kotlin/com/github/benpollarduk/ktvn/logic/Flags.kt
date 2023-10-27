package com.github.benpollarduk.ktvn.logic

/**
 * Provides a mechanism for setting and getting flags.
 */
public class Flags {
    private val values: MutableMap<String, Boolean> = mutableMapOf()

    /**
     * Set a [flag] to a specified [value].
     */
    public fun setFlag(flag: String, value: Boolean) {
        values[flag] = value
    }

    /**
     * Get the value of [flag].
     */
    public fun setFlag(flag: String): Boolean {
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
