package com.flameshine.rx.data.property

import io.reactivex.rxjava3.core.Observable

import com.flameshine.rx.generator.ErrorGenerator
import com.flameshine.rx.data.model.Hotel
import com.flameshine.rx.data.model.Property

class PropertyDataSource(private val errorGenerator: ErrorGenerator) {

    private companion object {
        private const val ERROR_PROBABILITY = 5
    }

    fun load(hotel: Hotel): Observable<List<Property>> {

        return Observable.create { emitter ->

            Thread.sleep(3000) // mock real response awaiting delay

            errorGenerator.generate(ERROR_PROBABILITY)?.let(emitter::onError)

            emitter.onNext(
                listOf(
                    Property("1", "${hotel.id}-dummy-property-name-1", 1),
                    Property("2", "${hotel.id}-dummy-property-name-2", 2),
                    Property("3", "${hotel.id}-dummy-property-name-3", 3),
                    Property("4", "${hotel.id}-dummy-property-name-4", 4),
                    Property("5", "${hotel.id}-dummy-property-name-5", 5),
                )
            )

            emitter.onComplete()
        }
    }
}