package org.example.domain.imbed;

import net.bytebuddy.asm.Advice;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Embeddable
public class Period {

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public Period(){}

    public Boolean isWork(LocalDateTime lcd) {
        return this.getStartDate().isBefore(lcd);
    }
    public Period(LocalDateTime startDate, LocalDateTime endDate){
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /*
    * getter setter
    * */

    public LocalDateTime getStartDate() {
        return startDate;
    }

    private void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    private void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
}
