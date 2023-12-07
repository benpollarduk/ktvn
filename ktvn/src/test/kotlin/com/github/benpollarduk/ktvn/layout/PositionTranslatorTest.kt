package com.github.benpollarduk.ktvn.layout

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.awt.Dimension

class PositionTranslatorTest {
    @Test
    fun `given center when get center of object in area then return center`() {
        // Given
        val position = Positions.center
        val area = Dimension(100, 100)

        // When
        val result = PositionTranslator.centerOfObjectInArea(area, position)

        // Then
        Assertions.assertEquals(50, result.x)
        Assertions.assertEquals(100, result.y)
    }

    @Test
    fun `given 0p5 0p5 when get center of object in area then return center`() {
        // Given
        val area = Dimension(100, 100)

        // When
        val result = PositionTranslator.centerOfObjectInArea(area, 0.5, 0.5)

        // Then
        Assertions.assertEquals(50, result.x)
        Assertions.assertEquals(50, result.y)
    }

    @Test
    fun `given center when get top left of object in area then return correct top left`() {
        // Given
        val position = Positions.center
        val area = Dimension(100, 100)
        val obj = Dimension(10, 10)

        // When
        val result = PositionTranslator.topLeftOfObjectInArea(area, obj, position)

        // Then
        Assertions.assertEquals(45, result.x)
        Assertions.assertEquals(90, result.y)
    }
}
