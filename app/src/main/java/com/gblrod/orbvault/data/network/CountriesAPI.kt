package com.gblrod.orbvault.data.network

import com.gblrod.orbvault.data.dto.CountriesDto
import com.gblrod.orbvault.data.network.ApiFields.BORDER_FIELDS
import com.gblrod.orbvault.data.network.ApiFields.COUNTRY_DETAILS_FIELDS
import com.gblrod.orbvault.data.network.ApiFields.COUNTRY_LIST_FIELDS
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CountriesAPI {
    @GET(value = "v3.1/name/{name}")
    suspend fun findCountry(
        @Path(value = "name") name: String?,
        @Query(value = "fullText") fullText: Boolean = true,
        @Query(value = "fields") fields: String = COUNTRY_DETAILS_FIELDS
    ): List<CountriesDto>

    @GET(value = "v3.1/all")
    suspend fun getAllCountries(
        @Query(value = "fields") fields: String = COUNTRY_LIST_FIELDS
    ): List<CountriesDto>

    @GET(value = "v3.1/all")
    suspend fun getRandomCountry(
        @Query(value = "fields") fields: String = COUNTRY_DETAILS_FIELDS
    ): List<CountriesDto>

    @GET(value = "v3.1/alpha")
    suspend fun getBordersCountries(
        @Query(value = "codes") codes: String?,
        @Query(value = "fields") fields: String = BORDER_FIELDS
    ): List<CountriesDto>

    @GET(value = "v3.1/alpha/{code}")
    suspend fun findCountryByCode(
        @Path(value = "code") code: String?,
        @Query(value = "fields") fields: String = COUNTRY_DETAILS_FIELDS
    ): CountriesDto
}