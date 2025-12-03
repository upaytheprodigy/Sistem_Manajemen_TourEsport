public class MatchNotFoundException extends TournamentException {

    public MatchNotFoundException(String id) {
        super("Match not found with id: " + id);
    }
}