package com.host_tracking;

import com.host_tracking.services.Session;
import com.host_tracking.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class SessionCommands {

    private final Session session;

    @Autowired
    private SessionCommands(Session session){
        this.session = session;
    }


    @ShellMethod
    public void open(){
        session.open();
    }

    @ShellMethod(key = "session")
    public String opennedTime(){
        session.hostTimeSession();
        return "You have been open for " + session.hostTimeSession();
    }

    @ShellMethod(key = "break")
    public void breakSession(Long time){
        session.breakTime(time);
        System.out.println("You are on Break. " + opennedTime());
    }

    @ShellMethod(key = "openbreak")
    public void openBreak(){
        session.openBreak();
    }

    @ShellMethod(key = "breakcheck")
    public String breakCheck(){
        return "Has " + session.breakCheck() + " Mins Break time avaliable.";
    }

    @ShellMethod(key = "adv")
    public String setAdv(Long minutes){
        String advOpenStr = session.setAdvOpen(minutes);
        return "Your Advanced open is in: " + Utils.convertTime(minutes)+ " Hours at " + advOpenStr ;
    }

    @ShellMethod(key = "close")
    public String close(){
        session.close();
        return "Your rank has been removed.";
    }

    @ShellMethod(key = "openadv")
    public void openAdv(){
        session.openAdv();
    }
}
