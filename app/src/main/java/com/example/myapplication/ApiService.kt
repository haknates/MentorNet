package com.example.myapplication

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

// Python'a gidecek istek şablonu
data class EslestirmeIstegi(val mentor_id: Int, val ogrenci_id: Int)

// Python'dan dönecek yanıt şablonu
data class EslestirmeYaniti(
    val durum: String,
    val uyum_orani: Int,
    val eslesme_onaylandi: Boolean,
    val gerekce: String
)

interface ApiService {
    @POST("/api/eslestir")
    fun mentorEslestir(@Body requestBody: EslestirmeIstegi): Call<EslestirmeYaniti>
}