package cz.frantisekhlinka.amiright.coredata.constants

object FirestoreKeys {
    object Common {
        const val UID = "uid"
        const val CREATED_AT = "createdAt"
        const val UPDATED_AT = "updatedAt"
        const val POST_ID = "postId"
    }

    const val POSTS = "posts"

    object Post {
        const val TEXT = "text"
        const val AUTHOR_UID = Common.UID
        const val CREATED_AT = Common.CREATED_AT
        const val UPDATED_AT = Common.UPDATED_AT
        const val AGREE_UIDS = "agreeUids"
        const val DISAGREE_UIDS = "disagreeUids"
    }
}