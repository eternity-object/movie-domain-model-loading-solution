package org.eternity.domainmodel.movie.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.eternity.domainmodel.generic.Money;

import java.util.Set;

@Entity
@DiscriminatorValue("PERCENT")
@NoArgsConstructor @Getter
public class PercentDiscountPolicy extends DiscountPolicy {
    private double percent;

    public PercentDiscountPolicy(double percent,
                                 Set<DiscountCondition> conditions,
                                 Set<Money> prices) {
        super(conditions, prices);
        this.percent = percent;
    }
}
