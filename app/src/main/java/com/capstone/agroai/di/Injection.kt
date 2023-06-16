package com.capstone.agroai.di

import com.capstone.agroai.data.Repository

object Injection {
    fun provideRepository() : Repository {
        return Repository.getInstance()
    }
}