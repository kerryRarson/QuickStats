package com.montana.quickstats.app.tests;

import android.test.InstrumentationTestCase;

import com.montana.quickstats.app.Player;

/**
 * Created by klarsen on 5/27/2014.
 */
public class PlayerTest extends InstrumentationTestCase {
    public void testPlayerConstruct(){
        Player player = new Player();
        assertTrue( player != null);

        player = null;
        player = new Player("Test Player","MSU","TE","5.09", "165", "8.30");
        assertEquals(player.getPosition(), "TE");
    }
}
