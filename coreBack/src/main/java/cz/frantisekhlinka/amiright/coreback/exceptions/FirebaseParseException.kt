package cz.frantisekhlinka.amiright.coreback.exceptions

/**
 * Occurs when parsing documents from firestore, if the parsed data cannot be converted to the expected format.
 */
class FirestoreParseException : IllegalStateException("Error parsing document from Firestore")
