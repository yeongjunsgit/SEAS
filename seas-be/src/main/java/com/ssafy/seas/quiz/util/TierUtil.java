package com.ssafy.seas.quiz.util;

public enum TierUtil {

    sailor(0, 99),
    catcher(100, 199),
    boatswain(200, 299),
    mate(300, 399),
    chif(400, 499),
    deputy_captain(500, 599),
    captain(600, Integer.MAX_VALUE);

    Integer min;
    Integer max;

    TierUtil(int min, int max) {
        this.min = min;
        this.max = max;
    }



}