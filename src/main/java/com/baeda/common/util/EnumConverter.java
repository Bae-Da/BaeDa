package com.baeda.common.util;

import com.baeda.user.Team;

public class EnumConverter {
    public static Team convertToTeam(String team) {
        if (team == null || team.trim().isEmpty()) {
            return Team.DEFAULT;
        }
        try {
            return Team.valueOf(team.toUpperCase());
        } catch (IllegalArgumentException e) {
            return Team.DEFAULT;
        }
    }
}
