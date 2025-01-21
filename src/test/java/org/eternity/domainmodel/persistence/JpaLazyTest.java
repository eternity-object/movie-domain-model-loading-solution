package org.eternity.domainmodel.persistence;

import jakarta.persistence.EntityManager;
import org.eternity.domainmodel.generic.Money;
import org.eternity.domainmodel.movie.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@DataJpaTest(showSql = false)
public class JpaLazyTest {
	@Autowired
	private EntityManager em;

	@Test
	public void load_single_entity() {
		DiscountPolicy policy =
				new AmountDiscountPolicy(Money.wons(1000),
						Set.of(
								new PeriodCondition(DayOfWeek.MONDAY, LocalTime.of(9,0), LocalTime.of(11, 0)),
								new SequenceCondition(1),
								new SequenceCondition(3)),
						Set.of(Money.wons(100), Money.wons(200), Money.wons(300)));
		em.persist(policy);
		em.flush();
		em.clear();

		em.find(DiscountPolicy.class, policy.getId());
	}

	@Test
	public void load_multiple_entities() {
		fixture().stream().forEach(em::persist);
		em.flush();
		em.clear();

		em.createQuery("select p from DiscountPolicy p", DiscountPolicy.class).getResultList();
	}

	List<DiscountPolicy> fixture() {
		return List.of(
				new AmountDiscountPolicy(
						Money.wons(1000),
						IntStream.range(0,100).mapToObj(SequenceCondition::new).collect(Collectors.toSet()),
						Set.of(Money.wons(100), Money.wons(200), Money.wons(300))),
				new PercentDiscountPolicy(
						0.1,
						IntStream.range(0,100).mapToObj(SequenceCondition::new).collect(Collectors.toSet()),
						Set.of(Money.wons(100), Money.wons(200), Money.wons(300))),
				new AmountDiscountPolicy(
						Money.wons(1000),
						IntStream.range(0,100).mapToObj(SequenceCondition::new).collect(Collectors.toSet()),
						Set.of(Money.wons(100), Money.wons(200), Money.wons(300))),
				new PercentDiscountPolicy(
						0.1,
						IntStream.range(0,100).mapToObj(SequenceCondition::new).collect(Collectors.toSet()),
						Set.of(Money.wons(100), Money.wons(200), Money.wons(300))));
	}
}
