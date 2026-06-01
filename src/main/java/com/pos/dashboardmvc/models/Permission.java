package com.pos.dashboardmvc.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Permission extends BaseModel {

    @Column(unique = true, nullable = false)
    private String name;
    private String description;
}
