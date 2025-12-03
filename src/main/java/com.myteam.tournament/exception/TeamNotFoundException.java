

public class TeamNotFoundException extends TournamentException {

    public TeamNotFoundException(String id) {
        super("Team not found with id: " + id);
    }
}