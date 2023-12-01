package com.github.benpollarduk.ktvn.layout

/**
 * Provides a collection of base positions.
 */
public object Positions {
    /**
     * Get the minimum.
     */
    private const val MIN: Double = 0.0

    /**
     * Get half.
     */
    private const val HALF: Double = 0.5

    /**
     * Get quarter.
     */
    private const val QUARTER: Double = 0.25

    /**
     * Get out of bounds.
     */
    private const val OUT_OF_BOUNDS: Double = -1.0

    /**
     * Above.
     */
    public val above: Position = object : Position {
        override val xOrigin: XOrigin = XOrigin.LEFT
        override val yOrigin: YOrigin = YOrigin.TOP
        override val normalizedX: Double = HALF
        override val normalizedY: Double = MIN

        override fun toString(): String {
            return "above"
        }
    }

    /**
     * Below.
     */
    public val below: Position = object : Position {
        override val xOrigin: XOrigin = XOrigin.LEFT
        override val yOrigin: YOrigin = YOrigin.BOTTOM
        override val normalizedX: Double = HALF
        override val normalizedY: Double = MIN
        override fun toString(): String {
            return "below"
        }
    }

    /**
     * To the left of.
     */
    public val leftOf: Position = object : Position {
        override val xOrigin: XOrigin = XOrigin.LEFT
        override val yOrigin: YOrigin = YOrigin.BOTTOM
        override val normalizedX: Double = OUT_OF_BOUNDS
        override val normalizedY: Double = MIN
        override fun toString(): String {
            return "left of"
        }
    }

    /**
     * To the right of.
     */
    public val rightOf: Position = object : Position {
        override val xOrigin: XOrigin = XOrigin.RIGHT
        override val yOrigin: YOrigin = YOrigin.BOTTOM
        override val normalizedX: Double = OUT_OF_BOUNDS
        override val normalizedY: Double = MIN
        override fun toString(): String {
            return "right of"
        }
    }

    /**
     * Center.
     */
    public val center: Position = object : Position {
        override val xOrigin: XOrigin = XOrigin.LEFT
        override val yOrigin: YOrigin = YOrigin.BOTTOM
        override val normalizedX: Double = HALF
        override val normalizedY: Double = MIN
        override fun toString(): String {
            return "center"
        }
    }

    /**
     * Left.
     */
    public val left: Position = object : Position {
        override val xOrigin: XOrigin = XOrigin.LEFT
        override val yOrigin: YOrigin = YOrigin.BOTTOM
        override val normalizedX: Double = QUARTER
        override val normalizedY: Double = MIN
        override fun toString(): String {
            return "left"
        }
    }

    /**
     * Right.
     */
    public val right: Position = object : Position {
        override val xOrigin: XOrigin = XOrigin.RIGHT
        override val yOrigin: YOrigin = YOrigin.BOTTOM
        override val normalizedX: Double = QUARTER
        override val normalizedY: Double = MIN
        override fun toString(): String {
            return "right"
        }
    }

    /**
     * None.
     */
    public val none: Position = object : Position {
        override val xOrigin: XOrigin = XOrigin.LEFT
        override val yOrigin: YOrigin = YOrigin.BOTTOM
        override val normalizedX: Double = OUT_OF_BOUNDS
        override val normalizedY: Double = OUT_OF_BOUNDS
        override fun toString(): String {
            return "none"
        }
    }
}
