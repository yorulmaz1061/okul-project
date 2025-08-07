package com.ozan.okulproject.entity.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Where(clause = "is_deleted=false")
@DiscriminatorValue("MANAGER")
public class Manager extends User{

    private String getFormattedManagerNumber() {
        return String.format("MAN%02d", getId());
    }

}
