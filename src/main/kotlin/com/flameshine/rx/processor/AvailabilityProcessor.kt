package com.flameshine.rx.processor

import io.reactivex.rxjava3.disposables.Disposable

interface AvailabilityProcessor {
    fun process(): Disposable
}