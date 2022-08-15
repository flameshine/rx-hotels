package com.flameshine.rx.data.hotel

import io.reactivex.rxjava3.core.Observable

import com.flameshine.rx.data.model.Hotel

interface HotelDataSource {
    fun loadHotels(): Observable<List<Hotel>>
}