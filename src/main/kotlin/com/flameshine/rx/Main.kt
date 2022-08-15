package com.flameshine.rx

import com.flameshine.rx.processor.AvailabilityProcessor
import com.flameshine.rx.processor.impl.AvailabilityProcessorImpl
import com.flameshine.rx.generator.impl.ErrorGeneratorImpl
import com.flameshine.rx.data.hotel.impl.ExpediaDataSource
import com.flameshine.rx.data.hotel.impl.LegacyDataSource
import com.flameshine.rx.data.hotel.impl.TripadvisorDataSource
import com.flameshine.rx.data.property.PropertyDataSource

fun main() {

    val errorGenerator = ErrorGeneratorImpl()

    val dataSources = listOf(
        ExpediaDataSource(errorGenerator),
        TripadvisorDataSource(errorGenerator)
    )

    val hotelOperator: AvailabilityProcessor = AvailabilityProcessorImpl(
        dataSources,
        LegacyDataSource(errorGenerator),
        PropertyDataSource(errorGenerator)
    )

    val disposable = hotelOperator.process()

    println("The hotels' data processing has been started...")

    while (!disposable.isDisposed) {

        println("Do you want to cancel all running operations (yes/no)?")

        when (readln()) {
            "yes" -> {
                disposable.dispose()
                println("The hotels' data processing was interrupted by a user!")
            }
        }
    }

    println("The hotels' data processing has been finished...")
}