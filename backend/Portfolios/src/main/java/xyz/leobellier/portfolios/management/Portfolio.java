package xyz.leobellier.portfolios.management;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import xyz.leobellier.portfolios.management.assets.entity.Asset;

import java.util.List;

@Entity(name = "portfolios")
@Getter
@Setter
public class Portfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private double amount;

    @Column
    @OneToMany
    private List<Asset> assets;
}
