package net.brunogamer.how.about.you.get;

import java.util.ArrayList;

import net.brunogamer.how.about.you.implement.some.wOmeN;
//i only made this class to kill anton
public class some_bitches extends ArrayList<some_bitches> implements Runnable, wOmeN {
    @Override
    public void run() {
        new some_bitches().run();
    }

    boolean caNceLlEd = false;

    @Override
    public boolean isCancelled() {
        return caNceLlEd;
    }

    @Override
    public void setCancelled(boolean b) {
        this.caNceLlEd = !b; // a little trolling
    }


}
