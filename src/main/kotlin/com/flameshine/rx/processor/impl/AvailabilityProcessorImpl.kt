package com.flameshine.rx.processor.impl

import kotlin.system.exitProcess

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.slf4j.LoggerFactory

import com.flameshine.rx.processor.AvailabilityProcessor
import com.flameshine.rx.data.hotel.HotelDataSource
import com.flameshine.rx.data.model.Hotel
import com.flameshine.rx.data.model.Property
import com.flameshine.rx.data.property.PropertyDataSource

class AvailabilityProcessorImpl(
    private val dataSources: List<HotelDataSource>,
    private val defaultDataSource: HotelDataSource,
    private val propertyDataSource: PropertyDataSource
) : AvailabilityProcessor {

    private companion object {
        private val LOGGER = LoggerFactory.getLogger(Companion::class.java)
    }

    override fun process(): Disposable = Observable.fromIterable(dataSources)
        .subscribeOn(Schedulers.io())
        .flatMap(::loadHotels)
        .filter(List<Hotel>::isNotEmpty)
        .flatMapIterable { it }
        .filter(Hotel::isAvailable)
        .flatMap(::loadProperties)
        .flatMapIterable { it }
        .observeOn(Schedulers.computation())
        .sorted(Comparator.comparingInt(Property::rating).reversed())
        .doOnComplete { exitProcess(0) }
        .forEach(::println)

    private fun loadHotels(dataSource: HotelDataSource) = dataSource.loadHotels()
        .onErrorResumeNext { e ->

            LOGGER.warn(
                "An unexpected error has occurred during the hotel processing: {}. Returning data from the default datasource.",
                e.message
            )

            defaultDataSource.loadHotels()
        }
        .doOnError { e -> LOGGER.error("The default datasource has returned an error: {}. Returning empty list.", e.message) }
        .subscribeOn(Schedulers.io())

    private fun loadProperties(hotel: Hotel) = propertyDataSource.load(hotel)
        .onErrorReturn { e ->

            LOGGER.warn(
                "An unexpected error has occurred during the hotel (id = '{}') properties processing: {}. Skipping invalid hotel.",
                hotel.id,
                e.message
            )

            emptyList()
        }
        .subscribeOn(Schedulers.io())
}