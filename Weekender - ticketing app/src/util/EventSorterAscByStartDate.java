package util;

import entities.Ticket;

import java.util.Comparator;
import entities.Event;

public class EventSorterAscByStartDate implements Comparator<Event>{

    @Override
    public int compare(Event t1, Event t2){
        return t1.getStartTime().compareTo(t2.getStartTime());
    }

}
