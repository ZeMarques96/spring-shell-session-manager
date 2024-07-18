package com.host_tracking.services;

import com.host_tracking.model.entities.OpennedTime;
import com.host_tracking.exceptions.BreakException;
import com.host_tracking.exceptions.OpenningException;
import com.host_tracking.util.Utils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Service
public class Session {

    private DateTimeFormatter ftm = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private LocalDateTime openTime; // LocalDateTime.of(2024, 07, 17, 14, 00);
    private LocalDateTime breakTime;
    private LocalDateTime openBreak; //LocalDateTime.of(2024, 07, 17, 23, 16);
    private LocalDateTime advOpen; //LocalDateTime.of(2024, 07, 18, 16, 59);
    private OpennedTime opennedTime = new OpennedTime(0L, 0L);
    private boolean status = false;



    public LocalDateTime getOpenTime() {
        return openTime;
    }

    public LocalDateTime getBreakTime() {
        return breakTime;
    }

    public LocalDateTime getadvOpen() {
        return advOpen;
    }

    public OpennedTime getOpennedTime() {
        return opennedTime;
    }


    public void setBreakTime(LocalDateTime breakTime) {
        this.breakTime = breakTime;
    }

    public void setOpennedTime(OpennedTime opennedTime) {
        this.opennedTime = opennedTime;
    }

    public boolean isStatus() {
        return status;
    }

    public void open(){
        if (isStatus()){
            throw new OpenningException("You are already open.");
        }
        openTime = LocalDateTime.now();
        if (opennedTime == null){
            opennedTime = new OpennedTime(0L, 0L);
        }
        opennedTime.resetOpennedTime();
        String timeFormated = openTime.format(ftm);
        this.status = true;
        System.out.println("Open time recorded: " + timeFormated);
    }

    public OpennedTime hostTimeSession(){
        if (getOpenTime() == null){
            throw new IllegalArgumentException("You have not openned");
        }
        LocalDateTime time = LocalDateTime.now();
        Long diff = ChronoUnit.MINUTES.between(getOpenTime(), time);
        OpennedTime timeSession = Utils.convertTime(diff);
        if (opennedTime == null){
            opennedTime = new OpennedTime(0L, 0L);
        }
        opennedTime.setCurrentSession(timeSession);
        return opennedTime;
    }

    public void breakTime(Long time){
        Long breaktime = breakCheck();
        hostTimeSession();
        setBreakTime(LocalDateTime.now());
        openBreak = getBreakTime();
        if (opennedTime.getHours() < 1){
            throw new BreakException("You are not avaliable for a break");
        }
        if (time > breakCheck()){
            throw new BreakException("You dont have this time to break your current break time is " + breaktime + " minutes");
        }
        getOpennedTime().setCurrentSession(new OpennedTime(0L, 0L));
        status = false;
        openBreak.plusMinutes(time);

    }

    public void openBreak(){
        if (openBreak == null){
            throw new OpenningException("You are not on break");
        }
        LocalDateTime validade = LocalDateTime.of(LocalDateTime.now().getYear(),LocalDateTime.now().getMonth(), LocalDateTime.now().getDayOfMonth() , LocalDateTime.now().getHour(), LocalDateTime.now().getMinute() );

        if(isStatus()){
            throw new OpenningException("You are already open.");
        }
        if (openBreak.isBefore(validade)){
            // could be a new class called fines
            throw new OpenningException("You have been fined, because you not open on time");
        }
        if (!openBreak.equals(validade)){
            throw new OpenningException("You are not on time to reopen");
        }
        openTime = LocalDateTime.now();
        open();

    }

    public Long breakCheck(){
        if (!isStatus()){
            throw new BreakException("You are not avaliable for a break");
        }
        LocalDateTime time = LocalDateTime.now();
        Long diff = ChronoUnit.MINUTES.between(getOpenTime(), time);
        return diff / 4;

    }

    public String setAdvOpen(Long minutes){
        if (minutes < 180){
            throw new OpenningException("You need more than 3 hours in advance to set an Advanced Open");
        }
        this.advOpen = LocalDateTime.now().plusMinutes(minutes);
        String advOpenStr = advOpen.format(ftm);
        return advOpenStr;
    }


    public OpennedTime close() {
        if (openTime == null ){
            throw new OpenningException("You are not open to close.");
        }
        if (opennedTime == null){
            hostTimeSession();
        }
        if (opennedTime.getHours() < 1){
            throw new IllegalArgumentException("You need host at least for One Hour");
        }
        if (!isStatus()){
            throw new OpenningException("You are not open");
        }
        LocalDateTime closeTime = LocalDateTime.now();
        Long diff = ChronoUnit.MINUTES.between(getOpenTime(), closeTime);
        OpennedTime timeSession = Utils.convertTime(diff);
        this.status = false;
        openTime = null;
        return timeSession;

    }

    public void openAdv(){
        if (advOpen == null){
            throw new OpenningException("You dont have any adv open");
        }
        LocalDateTime validade = LocalDateTime.of(LocalDateTime.now().getYear(),LocalDateTime.now().getMonth(), LocalDateTime.now().getDayOfMonth() , LocalDateTime.now().getHour(), LocalDateTime.now().getMinute() );

        if(isStatus()){
            throw new OpenningException("You are already open.");
        }
        if (advOpen.isAfter(validade.plusMinutes(16))){
            // could be a new class called fines
            throw new OpenningException("You have been fined, because you not open on time");
        }
        if (!advOpen.isBefore(validade)){
            throw new OpenningException("You are not on time to reopen");
        }
        openTime = LocalDateTime.now();
        open();
    }
}
