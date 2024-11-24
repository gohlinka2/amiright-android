package cz.frantisekhlinka.amiright.coredata

/**
 * Represents a post in the app.
 */
data class Post(
    val id: String,
    val text: String,
    val authorUid: String,
    val createdAt: Long,
    val updatedAt: Long,
    val agreeUids: List<String>,
    val disagreeUids: List<String>,
) {
    /**
     * Uids of all users who have reacted to this post.
     */
    val reactorUids: List<String>
        get() = agreeUids.plus(disagreeUids);

    /**
     * Uids of all users who are involved in this post, either as reactors or as author.
     */
    val involvedUids: List<String>
        get() = reactorUids.plus(authorUid);
}