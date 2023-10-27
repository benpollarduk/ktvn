package scenes

public object Positions {
    /**
     * Above.
     */
    public val above : OffscreenPosition = object : OffscreenPosition { }

    /**
     * Below.
     */
    public val below : OffscreenPosition = object : OffscreenPosition { }

    /**
     * To the left of.
     */
    public val leftOf : OffscreenPosition = object : OffscreenPosition { }

    /**
     * To the right of.
     */
    public val rightOf : OffscreenPosition = object : OffscreenPosition { }

    /**
     * Center.
     */
    public val center : OnscreenPosition = object : OnscreenPosition { }

    /**
     * Left.
     */
    public val left : OnscreenPosition = object : OnscreenPosition { }

    /**
     * Right.
     */
    public val right : OnscreenPosition = object : OnscreenPosition { }
}
