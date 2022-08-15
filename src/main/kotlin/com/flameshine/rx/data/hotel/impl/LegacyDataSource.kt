package com.flameshine.rx.data.hotel.impl

import io.reactivex.rxjava3.core.Observable

import com.flameshine.rx.generator.ErrorGenerator
import com.flameshine.rx.data.hotel.HotelDataSource
import com.flameshine.rx.data.model.Hotel

class LegacyDataSource(private val errorGenerator: ErrorGenerator) : HotelDataSource {

    private companion object {
        private const val ERROR_PROBABILITY = 5
    }

    override fun loadHotels(): Observable<List<Hotel>> {

        return Observable.create { emitter ->

            Thread.sleep(5000) // mock real response awaiting delay

            errorGenerator.generate(ERROR_PROBABILITY)?.let(emitter::onError)

            emitter.onNext(
                listOf(
                    Hotel(1, true)
                )
            )

            emitter.onComplete()
        }
    }
}