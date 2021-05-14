package com.jm.twitterstream

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class TwitterStreamConfigurationKtTest {

    @Test
    fun validConfiguration() {
        val config = TwitterStreamConfiguration(
                "bla",
                "blo",
                "ble",
                "bli"
        )

        assertFalse(config.isNotValidConfiguraion())
    }

    @Test
    fun isNotValidConfiguration() {
        val config = TwitterStreamConfiguration(
                "bla",
                "blo",
                "ble",
                ""
        )

        assertTrue(config.isNotValidConfiguraion())
    }
}