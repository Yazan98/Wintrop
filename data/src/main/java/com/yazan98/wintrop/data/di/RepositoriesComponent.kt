package com.yazan98.wintrop.data.di

import com.yazan98.wintrop.data.repository.JordanRepository

@motif.Scope
interface RepositoriesComponent {

    fun getJordanRepository(): JordanRepository

    @motif.Objects
    open class RepositoriesObjects {

        fun getJordanRepo(): JordanRepository {
            return JordanRepository()
        }

    }

}
