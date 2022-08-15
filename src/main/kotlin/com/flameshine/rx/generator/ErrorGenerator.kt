package com.flameshine.rx.generator

interface ErrorGenerator {
    fun generate(probability: Int): RuntimeException?
}