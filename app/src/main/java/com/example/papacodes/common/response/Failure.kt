package com.example.papacodes.common.response

/**
 * Base Class for handling errors/failures/exceptions.
 * Every feature specific failure should extend [PresentationFailure] class.
 */
//TODO We should use own Failure for each layer (domain, data, presentation)
sealed class Failure {

    /**
     * Extend this class for domain specific failures.
     */
    abstract class NetworkFailure : Failure() {
        object NetworkConnection : NetworkFailure()
        object EmptyNetworkResponse : NetworkFailure()
        object UnknownHostFailure : NetworkFailure()
        class ServerError(val code: String) : Failure()
    }

    /**
     * Extend this class for presentation specific failures.
     */
    abstract class PresentationFailure : Failure()

    /**
     * Extend this class for database specific failures.
     */
    abstract class DatabaseFailure : Failure() {
    }

    object MappingFailure : Failure()
    object SharedPreferencesFailure : Failure()

    object None : Failure()
}