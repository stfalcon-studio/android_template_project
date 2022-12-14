package com.typicode.android.common.models

import android.os.Parcelable
import com.template.domain.models.ModelMapper
import com.typicode.domain.models.User
import kotlinx.parcelize.Parcelize

/**
 * @author andrii.zhumela
 * Created 14.12.2022
 */
@Parcelize
class UserModel(
    val userId: Int,
    val albumId: Int,
    val name: String?,
    val thumbnailUrl: String?,
    val url: String?,
    val postCount: Int?
): Parcelable {
    companion object: ModelMapper<User, UserModel> {
        override fun mapTo(model: User): UserModel {
            return with(model) {
                UserModel(
                    userId = userId,
                    albumId = albumId,
                    name = name,
                    thumbnailUrl = thumbnailUrl,
                    url = url,
                    postCount = postCount
                )
            }
        }

        override fun mapToDomain(model: UserModel): User {
            return with(model) {
                User(
                    userId = userId,
                    albumId = albumId,
                    name = name,
                    thumbnailUrl = thumbnailUrl,
                    url = url,
                    postCount = postCount
                )
            }
        }

    }
}