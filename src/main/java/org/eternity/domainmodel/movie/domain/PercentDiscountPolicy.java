package org.eternity.domainmodel.movie.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@DiscriminatorValue("PERCENT")
@NoArgsConstructor @Getter
public class PercentDiscountPolicy extends DiscountPolicy {
    private double percent;

    public PercentDiscountPolicy(double percent,
                                 Set<DiscountCondition> conditions) {
        super(conditions);
        this.percent = percent;
    }
}
