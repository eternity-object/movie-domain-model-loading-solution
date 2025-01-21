package org.eternity.domainmodel.movie.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.eternity.domainmodel.generic.Money;

import java.util.Set;

@Entity
@DiscriminatorValue("AMOUNT")
@NoArgsConstructor @Getter
public class AmountDiscountPolicy extends DiscountPolicy {
    private Money discountAmount;

    public AmountDiscountPolicy(Money discountAmount,
                                Set<DiscountCondition> conditions,
                                Set<Money> prices) {
        super(conditions, prices);
        this.discountAmount = discountAmount;
    }
}
