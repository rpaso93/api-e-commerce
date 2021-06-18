package uces.edu.ar.shoppingCart.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.math.BigDecimal;

@Entity
public class Product {
	
	private @Id @GeneratedValue Long id;
	private String description;
	private BigDecimal unitPrice;
	private Integer stock;
		
	public Product() {
		super();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	public void subtractToStock(Integer quantity) {
		this.stock -= quantity;
	}
	public void addToStock(Integer quantity) {
		this.stock += quantity;
	}	
}
