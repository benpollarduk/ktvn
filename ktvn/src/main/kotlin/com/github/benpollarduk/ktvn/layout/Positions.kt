package com.github.benpollarduk.ktvn.layout

public object Positions {
    /**
     * Above.
     */
    public val above: Position = object : Position {
        override fun toString(): String {
            return "above"
        }
    }

    /**
     * Below.
     */
    public val below: Position = object : Position {
        override fun toString(): String {
            return "below"
        }
    }

    /**
     * To the left of.
     */
    public val leftOf: Position = object : Position {
        override fun toString(): String {
            return "left of"
        }
    }

    /**
     * To the right of.
     */
    public val rightOf: Position = object : Position {
        override fun toString(): String {
            return "right of"
        }
    }

    /**
     * Center.
     */
    public val center: Position = object : Position {
        override fun toString(): String {
            return "center"
        }
    }

    /**
     * Left.
     */
    public val left: Position = object : Position {
        override fun toString(): String {
            return "left"
        }
    }

    /**
     * Right.
     */
    public val right: Position = object : Position {
        override fun toString(): String {
            return "right"
        }
    }
}
