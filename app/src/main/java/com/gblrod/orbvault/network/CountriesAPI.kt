package com.gblrod.orbvault.network

import com.gblrod.orbvault.dto.CountriesDto
import retrofit2.http.GET
import retrofit2.http.Path

interface CountriesAPI {
    @GET(value = "v3.1/name/{name}?fields=name,capital,population,flags,region")
    suspend fun findCountries(
        @Path(value = "name") name: String
    ) : List<CountriesDto>
}