package com.logic.jellyfish.http

import com.logic.jellyfish.data.MemberList
import retrofit2.http.GET

interface HospitalService {

    @GET("inpatient_ward_video/terminal/getTerMembersList")
    suspend fun getTerMembersList(): MemberList

}