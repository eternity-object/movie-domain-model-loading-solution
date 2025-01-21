package org.eternity.domainmodel.movie.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.eternity.domainmodel.generic.Money;

import java.util.HashSet;
import java.util.Set;

@Entity
@NamedEntityGraphs(
        @NamedEntityGraph(
                name="Policy.conditions",
                attributeNodes = @NamedAttributeNode("conditions")
        )
)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="policy_type")
@NoArgsConstructor @Getter
public abstract class DiscountPolicy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name="POLICY_ID")
    private Set<DiscountCondition> conditions = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "POLICY_PRICES", joinColumns = @JoinColumn(name="POLICY_ID"))
    @org.hibernate.annotations.Fetch(org.hibernate.annotations.FetchMode.SELECT)
    private Set<Money> prices = new HashSet<>();

    public DiscountPolicy(Set<DiscountCondition> conditions) {
        this.conditions = conditions;
    }
}
