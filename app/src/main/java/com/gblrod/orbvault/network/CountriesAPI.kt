package com.gblrod.orbvault.network

import com.gblrod.orbvault.dto.CountriesDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CountriesAPI {
    @GET(value = "v3.1/name/{name}")
    suspend fun findCountries(
        @Path(value = "name") name: String,
        @Query(value = "fields") fields: String = "name,capital,population,flags,region,subregion,currencies,languages,timezones"
    ) : List<CountriesDto>
}