package com.typicode.data.api.models.responses
import com.google.gson.annotations.SerializedName
import com.template.domain.models.ModelMapper
import com.typicode.domain.models.User


/**
 * @author andrii.zhumela
 * Created 13.12.2022
 */
data class UserResponse(
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("albumId")
    val albumId: Int,
    @SerializedName("name")
    val name: String?,
    @SerializedName("thumbnailUrl")
    val thumbnailUrl: String?,
    @SerializedName("url")
    val url: String?
) {
    companion object : ModelMapper<User, UserResponse> {
        override fun mapTo(model: User): UserResponse {
            throw IllegalStateException("Unused convertation")
        }

        override fun mapToDomain(model: UserResponse): User {
            return with(model) {
                User(
                    userId = userId,
                    albumId = albumId,
                    name = name,
                    thumbnailUrl = thumbnailUrl,
                    url = url,
                    postCount = 0
                )
            }
        }

    }
}