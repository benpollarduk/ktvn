package com.github.benpollarduk.ktvn.layout

/**
 * Provides a collection of base positions.
 */
public object Positions {
    /**
     * Get the maximum.
     */
    private const val MAX: Double = 1.0

    /**
     * Get the negative limit.
     */
    private const val NEGATIVE_LIMIT: Double = -1.0

    /**
     * Get the positive limit.
     */
    private const val POSITIVE_LIMIT: Double = 2.0

    /**
     * Get half.
     */
    private const val HALF: Double = 0.5

    /**
     * Get quarter.
     */
    private const val QUARTER: Double = 0.25

    /**
     * Get quarter.
     */
    private const val THREE_QUARTER: Double = 0.75

    /**
     * Above.
     */
    public val above: Position = object : Position {
        override val normalizedX: Double = HALF
        override val normalizedY: Double = NEGATIVE_LIMIT

        override fun toString(): String {
            return "above"
        }
    }

    /**
     * Below.
     */
    public val below: Position = object : Position {
        override val normalizedX: Double = HALF
        override val normalizedY: Double = POSITIVE_LIMIT
        override fun toString(): String {
            return "below"
        }
    }

    /**
     * To the left of.
     */
    public val leftOf: Position = object : Position {
        override val normalizedX: Double = NEGATIVE_LIMIT
        override val normalizedY: Double = MAX
        override fun toString(): String {
            return "left of"
        }
    }

    /**
     * To the right of.
     */
    public val rightOf: Position = object : Position {
        override val normalizedX: Double = POSITIVE_LIMIT
        override val normalizedY: Double = MAX
        override fun toString(): String {
            return "right of"
        }
    }

    /**
     * Center.
     */
    public val center: Position = object : Position {
        override val normalizedX: Double = HALF
        override val normalizedY: Double = MAX
        override fun toString(): String {
            return "center"
        }
    }

    /**
     * Left.
     */
    public val left: Position = object : Position {
        override val normalizedX: Double = QUARTER
        override val normalizedY: Double = MAX
        override fun toString(): String {
            return "left"
        }
    }

    /**
     * Right.
     */
    public val right: Position = object : Position {
        override val normalizedX: Double = THREE_QUARTER
        override val normalizedY: Double = MAX
        override fun toString(): String {
            return "right"
        }
    }

    /**
     * Stop.
     */
    public val none: Position = object : Position {
        override val normalizedX: Double = NEGATIVE_LIMIT
        override val normalizedY: Double = NEGATIVE_LIMIT
        override fun toString(): String {
            return "none"
        }
    }
}
