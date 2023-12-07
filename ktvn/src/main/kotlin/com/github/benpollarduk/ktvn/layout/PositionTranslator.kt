package com.github.benpollarduk.ktvn.layout

import java.awt.Dimension
import java.awt.Point

/**
 * Provides positional translations.
 */
public object PositionTranslator {
    /**
     * Determine the top-left location of an [obj] in an [area], given a [position].
     */
    public fun topLeftOfObjectInArea(area: Dimension, obj: Dimension, position: Position): Point {
        return topLeftOfObjectInArea(area, obj, position.normalizedX, position.normalizedY)
    }

    /**
     * Determine the top-left location of an [obj] in an [area], given a [normalizedX] and [normalizedY].
     */
    public fun topLeftOfObjectInArea(
        area: Dimension,
        obj: Dimension,
        normalizedX: Double,
        normalizedY: Double
    ): Point {
        return Point(
            ((area.width * normalizedX) - (obj.width / 2)).toInt(),
            ((normalizedY * area.height) - obj.height).toInt()
        )
    }

    /**
     * Determine the center point of an object in an [area], given a [position].
     */
    public fun centerOfObjectInArea(area: Dimension, position: Position): Point {
        return centerOfObjectInArea(area, position.normalizedX, position.normalizedY)
    }

    /**
     * Determine the center point of an object in an [area], given a [normalizedX] and [normalizedY].
     */
    public fun centerOfObjectInArea(
        area: Dimension,
        normalizedX: Double,
        normalizedY: Double
    ): Point {
        return Point(
            ((area.width * normalizedX)).toInt(),
            ((normalizedY * area.height)).toInt()
        )
    }
}
