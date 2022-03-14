package com.dain.domain.entity;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Embeddable
public class Alarms {

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Alarm> alarms;

    public Alarms(){
        this(new ArrayList<>());
    }

    public Alarms(final List<Alarm> alarms) {
        this.alarms = alarms;
    }

    public void addAlarm(Alarm alarm){
        alarms.add(alarm);
    }
}
