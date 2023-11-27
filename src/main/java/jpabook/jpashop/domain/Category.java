package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue
    @Column(name = "category_id") // _ 붙이는 이유 : 테이블은 타입이 없기 때문에, DBA 분들도 이렇게 씀
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "category_item",
            joinColumns = @JoinColumn(name = "category_id"),
    inverseJoinColumns = @JoinColumn(name = "item_id"))
    //다 대 다 관계일 경우, 중간에 풀어줄 수 있는 중간 테이블이 필요
    // 실무에서는 사용할 수 없음. Why? 이러한 구조밖에 사용할 수 없기 때문에 (단순하게 매핑하는 경우는 거의 없기 때문)
    private List<Item> items = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();
}
