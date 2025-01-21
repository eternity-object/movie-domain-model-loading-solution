package org.eternity.domainmodel.movie.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@DiscriminatorValue("PERIOD")
@NoArgsConstructor @Getter
public class PeriodCondition extends DiscountCondition {
    private DayOfWeek dayOfWeek;
    private LocalTime startTime, endTime;

    public PeriodCondition(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
