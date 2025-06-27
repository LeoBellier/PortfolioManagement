package xyz.leobellier.portfolios.management.assets.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "assets")
public record Asset(@Id @GeneratedValue long id, String name, String Ticker, double price ) {
}
