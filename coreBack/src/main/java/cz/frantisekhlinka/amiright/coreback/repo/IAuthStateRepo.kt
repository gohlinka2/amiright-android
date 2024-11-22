package cz.frantisekhlinka.amiright.coreback.repo

import kotlinx.coroutines.flow.Flow

/**
 * Repository for accessing the current state of authentication.
 */
interface IAuthStateRepo {
    fun getCurrentUidFlow(): Flow<String?>
    fun getCurrentUid(): String?
}