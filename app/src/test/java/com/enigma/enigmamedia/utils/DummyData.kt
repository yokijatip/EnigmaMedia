package com.enigma.enigmamedia.utils

import androidx.paging.PagingData
import com.enigma.enigmamedia.data.remote.response.ListStoryItem

object DummyData {

    val dummyListStoryItems = PagingData.from(
        listOf(
            ListStoryItem(
                "https://example.com/1.jpg",
                "2022-01-01T00:00:00.000Z",
                "Ahmad Alucard",
                "Hero Jahanam",
                -123456,
                231.2,
                -3123123
            ),
            ListStoryItem(
                "https://example.com/2.jpg",
                "2022-01-02T00:00:00.000Z",
                "Rahmat Zilong",
                "Yaudah lah ya asal ada aja ini hero di mobile lejen",
                3123231,
                -11.212,
                -15.002
            ),
            ListStoryItem(
                "https://example.com/3.jpg",
                "2022-01-02T00:00:00.000Z",
                "Slebew",
                "Kangen jeje citayem fashion week",
                -123,
                12.212,
                -14.002
            ),
        )
    )
}
