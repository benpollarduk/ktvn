package com.github.benpollarduk.ktvn.logic

import com.github.benpollarduk.ktvn.structure.CancellationToken
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import kotlin.concurrent.thread

class ProgressionControllerTest {
    @Test
    fun `given force skip when await acknowledgement then completes`() {
        // Given
        val controller = ProgressionController()
        val cancellationToken = CancellationToken()
        val canSkipStep = false
        var running = true
        var complete = false
        var startTime = System.currentTimeMillis()
        controller.progressionMode = ProgressionMode.Skip(true)

        // When
        thread {
            controller.awaitAcknowledgement(canSkipStep, cancellationToken)
            complete = true
            running = false
        }

        while (running && System.currentTimeMillis() - startTime < 1000) {
            Thread.sleep(10)
        }

        // Then
        Assertions.assertTrue(complete)
    }

    @Test
    fun `given skip with skippable step when await acknowledgement then completes`() {
        // Given
        val controller = ProgressionController()
        val cancellationToken = CancellationToken()
        val canSkipStep = true
        var running = true
        var complete = false
        var startTime = System.currentTimeMillis()
        controller.progressionMode = ProgressionMode.Skip(false)

        // When
        thread {
            controller.awaitAcknowledgement(canSkipStep, cancellationToken)
            complete = true
            running = false
        }

        while (running && System.currentTimeMillis() - startTime < 1000) {
            Thread.sleep(10)
        }

        // Then
        Assertions.assertTrue(complete)
    }

    @Test
    fun `given skip with non-skippable step when await acknowledgement then does not complete`() {
        // Given
        val controller = ProgressionController()
        val cancellationToken = CancellationToken()
        val canSkipStep = false
        var running = true
        var complete = false
        var startTime = System.currentTimeMillis()
        controller.progressionMode = ProgressionMode.Skip(false)

        // When
        thread {
            controller.awaitAcknowledgement(canSkipStep, cancellationToken)
            complete = true
            running = false
        }

        while (running && System.currentTimeMillis() - startTime < 1000) {
            Thread.sleep(10)
        }

        // Then
        Assertions.assertFalse(complete)
    }

    @Test
    fun `given wait for confirmation with non-skippable step when await acknowledgement then does not complete`() {
        // Given
        val controller = ProgressionController()
        val cancellationToken = CancellationToken()
        val canSkipStep = false
        var running = true
        var complete = false
        var startTime = System.currentTimeMillis()
        controller.progressionMode = ProgressionMode.WaitForConfirmation

        // When
        thread {
            controller.awaitAcknowledgement(canSkipStep, cancellationToken)
            complete = true
            running = false
        }

        while (running && System.currentTimeMillis() - startTime < 1000) {
            Thread.sleep(10)
        }

        // Then
        Assertions.assertFalse(complete)
    }

    @Test
    fun `given auto with non-skippable step when await acknowledgement then completes`() {
        // Given
        val controller = ProgressionController()
        val cancellationToken = CancellationToken()
        val canSkipStep = false
        var running = true
        var complete = false
        var startTime = System.currentTimeMillis()
        controller.progressionMode = ProgressionMode.Auto(10)

        // When
        thread {
            controller.awaitAcknowledgement(canSkipStep, cancellationToken)
            complete = true
            running = false
        }

        while (running && System.currentTimeMillis() - startTime < 1000) {
            Thread.sleep(10)
        }

        // Then
        Assertions.assertTrue(complete)
    }
}
