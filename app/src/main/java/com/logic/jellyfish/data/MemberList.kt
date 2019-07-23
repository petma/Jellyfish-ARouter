package com.logic.jellyfish.data

data class MemberList(
    val code: Int = 0,
    val message: String = "",
    val result: List<Result> = listOf(),
    val success: Boolean = false,
    val total: String = ""
) {
    data class Result(
        val creationTime: String = "",
        val duration: Int = 0,
        val id: String = "",
        val name: String = "",
        val price: Int = 0,
        val turnoverTime: String = "",
        val type: Int = 0,
        val unitsName: String = "",
        val unitsState: Int = 0
    )
}