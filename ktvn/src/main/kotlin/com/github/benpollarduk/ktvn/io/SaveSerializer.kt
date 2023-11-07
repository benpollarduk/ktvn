package com.github.benpollarduk.ktvn.io

import java.io.File

public object SaveSerializer {
    /**
     * Save a [save] to a specified [path].
     */
    public fun toFile(save: Save, path: String): Boolean {
        return try {
            val contents = SaveJsonParser.toJson(save)
            File(path).writeText(contents)
            true
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Return a [Save] from a specified [path].
     */
    public fun fromFile(path: String): Save {
        return try {
            val json = File(path).readText()
            SaveJsonParser.fromJson(json)
        } catch (e: Exception) {
            Save.corrupt
        }
    }
}
