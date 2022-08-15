package com.flameshine.rx.generator.impl

import com.flameshine.rx.generator.ErrorGenerator

class ErrorGeneratorImpl : ErrorGenerator {

    override fun generate(probability: Int): RuntimeException? {
        val hazard = (0..100).random()
        return if (hazard in 0..probability) RuntimeException("An unexpected error has occurred") else null
    }
}