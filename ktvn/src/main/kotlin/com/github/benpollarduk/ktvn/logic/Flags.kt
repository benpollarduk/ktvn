package com.github.benpollarduk.ktvn.logic

/**
 * Provides a mechanism for setting and getting flags.
 */
public class Flags {
    private val values: MutableMap<String, Boolean> = mutableMapOf()

    /**
     * Get a condition with a specified [name].
     */
    public operator fun get(name: String): Boolean {
        return getValue(name)
    }

    /**
     * Sets a condition with a specified [name] to a specified [value].
     */
    public operator fun set(name: String, value: Boolean) {
        if (value) {
            setTrue(name)
        } else {
            setFalse(name)
        }
    }

    /**
     * Set a [flag] true.
     */
    public infix fun setTrue(flag: String) {
        values[flag] = true
    }

    /**
     * Set a [flag] false.
     */
    public infix fun setFalse(flag: String) {
        values[flag] = false
    }

    /**
     * Get the value of [flag].
     */
    public infix fun getValue(flag: String): Boolean {
        values.putIfAbsent(flag, false)
        return values[flag] ?: false
    }

    /**
     * Convert to a map.
     */
    public fun toMap(): Map<String, Boolean> {
        return values
    }

    public companion object {
        /**
         * Provides an empty implementation of [Flags].
         */
        public val empty: Flags = Flags()

        /**
         * Create a new [Flags] from a map.
         */
        public fun fromMap(map: Map<String, Boolean>): Flags {
            val flags = Flags()

            map.forEach {
                if (it.value) {
                    flags.setTrue(it.key)
                } else {
                    flags.setFalse(it.key)
                }
            }

            return flags
        }
    }
}
