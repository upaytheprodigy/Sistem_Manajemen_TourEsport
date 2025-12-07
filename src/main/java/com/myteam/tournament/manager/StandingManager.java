package com.myteam.tournament.manager;
import com.myteam.tournament.model.Match;
import com.myteam.tournament.model.Standing;
import java.util.*;

public class StandingManager {
    public List<Standing> computeStandings(List<Match> matches){
        // build map by teamId -> Standing(teamId, teamName)
        Map<String,Standing> map = new HashMap<>();
        for(Match m: matches){
            if (!map.containsKey(m.getTeamA().getId()))
                map.put(m.getTeamA().getId(), new Standing(m.getTeamA().getId(), m.getTeamA().getName()));
            if (!map.containsKey(m.getTeamB().getId()))
                map.put(m.getTeamB().getId(), new Standing(m.getTeamB().getId(), m.getTeamB().getName()));

            var opt = m.getResult();
            if (opt.isEmpty()) continue;
            var r = opt.get();
            if (r.getScoreA() > r.getScoreB()){
                map.get(m.getTeamA().getId()).addWin();
                map.get(m.getTeamB().getId()).addLoss();
            } else if (r.getScoreA() < r.getScoreB()){
                map.get(m.getTeamB().getId()).addWin();
                map.get(m.getTeamA().getId()).addLoss();
            } else {
                map.get(m.getTeamA().getId()).addDraw();
                map.get(m.getTeamB().getId()).addDraw();
            }
        }
        List<Standing> list = new ArrayList<>(map.values());
        list.sort(Comparator.comparingInt(Standing::getPoints).reversed());
        return list;
    }
}
