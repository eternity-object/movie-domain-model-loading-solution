package org.eternity.domainmodel.movie.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.eternity.domainmodel.generic.Money;

import java.util.HashSet;
import java.util.Set;

@Entity
@NamedEntityGraphs(
        @NamedEntityGraph(
                name="Movie.policy",
                attributeNodes = {
                        @NamedAttributeNode(
                                value = "discountPolicy",
                                subgraph = "policy.conditions")
                },
                subgraphs = {
                        @NamedSubgraph(
                                name = "policy.conditions",
                                attributeNodes = @NamedAttributeNode("conditions")
                        )      }
        )
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Movie {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Integer runningTime;
    private Money fee;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="POLICY_ID")
    private DiscountPolicy discountPolicy;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "POLICY_PRICES", joinColumns = @JoinColumn(name="POLICY_ID"))
    @org.hibernate.annotations.Fetch(org.hibernate.annotations.FetchMode.SELECT)
    private Set<Money> prices = new HashSet<>();

    public Movie(String title, Integer runningTime, Money fee, DiscountPolicy discountPolicy) {
        this.title = title;
        this.runningTime = runningTime;
        this.fee = fee;
        this.discountPolicy = discountPolicy;
    }

    public Money getFee() {
        return fee;
    }
}