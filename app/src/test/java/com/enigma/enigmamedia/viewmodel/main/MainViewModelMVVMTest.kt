package com.enigma.enigmamedia.viewmodel.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.enigma.enigmamedia.repository.Repository
import com.enigma.enigmamedia.utils.DummyData
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class MainViewModelMVVMTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: Repository
    private lateinit var storyViewModelMVVM: MainViewModelMVVM

    @Before
    fun setUp() {
        storyViewModelMVVM = MainViewModelMVVM(repository)
    }

    @Test
    fun `when Get StoryPagingFromViewModel Should Return PagingData`() {

        val token = "Dummy Token"


        val storyPagingSource = DummyData.dummyListStoryItems
        Mockito.`when`(repository.getStoryPagingSource(token))
    }

}