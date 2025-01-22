package org.eternity.domainmodel.persistence;

import jakarta.persistence.EntityManager;
import org.eternity.domainmodel.generic.Money;
import org.eternity.domainmodel.movie.domain.AmountDiscountPolicy;
import org.eternity.domainmodel.movie.domain.DiscountPolicy;
import org.eternity.domainmodel.movie.domain.PercentDiscountPolicy;
import org.eternity.domainmodel.movie.domain.SequenceCondition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@DataJpaTest(showSql = false)
public class JpaLazyTest {
	@Autowired
	private EntityManager em;

	@Test
	public void add_discount_condition() {
		fixture().forEach(em::persist);
		em.flush();
		em.clear();

		List<DiscountPolicy> policies = em.createQuery("select p from DiscountPolicy p", DiscountPolicy.class).getResultList();

		for(DiscountPolicy policy : policies) {
			policy.getConditions().size();
		}
	}

	List<DiscountPolicy> fixture() {
		return List.of(
				new AmountDiscountPolicy(
						Money.wons(1000),
						IntStream.range(0,100).mapToObj(SequenceCondition::new).collect(Collectors.toSet())),
				new PercentDiscountPolicy(
						0.1,
						IntStream.range(0,100).mapToObj(SequenceCondition::new).collect(Collectors.toSet())),
				new AmountDiscountPolicy(
						Money.wons(1000),
						IntStream.range(0,100).mapToObj(SequenceCondition::new).collect(Collectors.toSet())),
				new PercentDiscountPolicy(
						0.1,
						IntStream.range(0,100).mapToObj(SequenceCondition::new).collect(Collectors.toSet()))
				);
	}
}
