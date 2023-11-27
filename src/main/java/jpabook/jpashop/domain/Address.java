package jpabook.jpashop.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.Getter;

@Embeddable
@Getter //변경을 불가능하도록 Setter 추가 안함
public class Address {

    private String city;
    private String street;
    private String zipcode;

    //public 으로 해놓으면 사람들이 많이 호출하기 때문에
    // 일부로 protected 로 하여 오류 제거 및 사람들이 많이 사용하지 못하도록 하기 위함.
    // JPA 스펙상 Embeddabl로 하면 public, protected 로 설정해야됨
    protected Address() {
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}

