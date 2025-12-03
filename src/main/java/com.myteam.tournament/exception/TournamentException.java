/**
 * Base exception for tournament-related errors.
 * Clean and simple to maintain.
 */
public class TournamentException extends RuntimeException {

    public TournamentException(String message) {
        super(message);
    }
}
