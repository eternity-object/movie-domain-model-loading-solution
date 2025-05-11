package org.eternity.domainmodel.persistence;

import jakarta.persistence.EntityManager;
import org.eternity.domainmodel.generic.Money;
import org.eternity.domainmodel.movie.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(showSql = false)
public class JpaLazyTest {
	@Autowired
	private EntityManager em;

	@Test
	public void fetch_join() {
		DiscountPolicy policy =
				new AmountDiscountPolicy(Money.wons(1000),
						Set.of(
								new PeriodCondition(DayOfWeek.MONDAY, LocalTime.of(9,0), LocalTime.of(11, 0)),
								new SequenceCondition(1),
								new SequenceCondition(3)));

		em.persist(policy);
		em.flush();
		em.clear();


		List<DiscountPolicy> policies =
				em.createQuery("select p from DiscountPolicy p join fetch p.conditions", DiscountPolicy.class)
						.getResultList();

		assertThat(policies.size()).isEqualTo(1);
	}

	@Test
	public void discount_policy_fetch() {
		DiscountPolicy policy =
				new AmountDiscountPolicy(Money.wons(1000),
						Set.of(
								new PeriodCondition(DayOfWeek.MONDAY, LocalTime.of(9,0), LocalTime.of(11, 0)),
								new SequenceCondition(1),
								new SequenceCondition(3)));

		em.persist(policy);
		em.flush();
		em.clear();

		Map<String, Object> hints = new HashMap<>();
		hints.put("javax.persistence.fetchgraph", em.getEntityGraph("Policy.conditions"));

		em.find(DiscountPolicy.class, policy.getId(), hints);
	}

	@Test
	public void movie_fetch() {
		Movie movie = new Movie("영화", 120, Money.wons(10000),
				new AmountDiscountPolicy(Money.wons(1000),
						Set.of(
								new PeriodCondition(DayOfWeek.MONDAY, LocalTime.of(9,0), LocalTime.of(11, 0)),
								new SequenceCondition(1),
								new SequenceCondition(3))));

		em.persist(movie);
		em.flush();
		em.clear();

		Map<String, Object> hints = new HashMap<>();
		hints.put("javax.persistence.fetchgraph", em.getEntityGraph("Movie.policy"));

		em.find(Movie.class, movie.getId(), hints);
	}

	@Test
	public void movie_load() {
		Movie movie = new Movie("영화", 120, Money.wons(10000),
				new AmountDiscountPolicy(Money.wons(1000),
						Set.of(
								new PeriodCondition(DayOfWeek.MONDAY, LocalTime.of(9,0), LocalTime.of(11, 0)),
								new SequenceCondition(1),
								new SequenceCondition(3))));

		em.persist(movie);
		em.flush();
		em.clear();

		Map<String, Object> hints = new HashMap<>();
		hints.put("javax.persistence.loadgraph", em.getEntityGraph("Movie.policy"));

		em.find(Movie.class, movie.getId(), hints);
	}

}
