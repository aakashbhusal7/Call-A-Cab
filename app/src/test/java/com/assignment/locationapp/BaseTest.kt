package com.assignment.locationapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.MockKAnnotations
import org.junit.Before
import org.junit.Rule

abstract class BaseTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    //set up of mockk library
    @Before
    fun setupTest() {
        MockKAnnotations.init(this)
        preTest()
    }

    abstract fun preTest()
}