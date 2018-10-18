package cz.fatty.mannheim.repo

/**
 * Base wrapper for API data.
 * State indicates whether the request is running, has failed, or indicates network errors.
 *
 * @author Josef Hruska
 */
class ApiData<T>(

    /**
     * The state is changed after
     */
    var state: State = State.IN_PROGRESS,

    /**
     * Nullable data.
     * Is null mostly when #state is not equal to #OK.
     */
    var data: T? = null
) {

    enum class State(val code: Int) {
        IN_PROGRESS(1),

        ERROR(2), // General error, uses for example in multiple request handling

        // Http codes
        OK(200), // all [200, 300) codes are mapped to this

        BAD_REQUEST(400),

        UNAUTHORIZED(401),

        FORBIDDEN(403),

        NOT_FOUND(404),

        ENTITY_TOO_LARGE(413),

        SERVER_ERROR(500),

        OTHER(1000),

        // Network statuses
        NET_NO_NETWORK(10),

        NET_TIMEOUT(11),

        NET_IO_ERROR(12),

        NET_OTHER_ERROR(13);

        fun isNetworkError() =
            this == State.NET_NO_NETWORK || this == State.NET_TIMEOUT || this == State.NET_IO_ERROR || this == State.NET_OTHER_ERROR
    }

    fun isInProgress() = state == State.IN_PROGRESS

}