package com.flameshine.rx.data.hotel.impl

import io.reactivex.rxjava3.core.Observable

import com.flameshine.rx.generator.ErrorGenerator
import com.flameshine.rx.data.hotel.HotelDataSource
import com.flameshine.rx.data.model.Hotel

class ExpediaDataSource(private val errorGenerator: ErrorGenerator) : HotelDataSource {

    private companion object {
        private const val ERROR_PROBABILITY = 50
    }

    override fun loadHotels(): Observable<List<Hotel>> {

        return Observable.create { emitter ->

            Thread.sleep(3000) // mock real response awaiting delay

            errorGenerator.generate(ERROR_PROBABILITY)?.let(emitter::onError)

            emitter.onNext(
                listOf(
                    Hotel(1, true),
                    Hotel(2, false),
                    Hotel(3, true)
                )
            )

            emitter.onComplete()
        }
    }
}