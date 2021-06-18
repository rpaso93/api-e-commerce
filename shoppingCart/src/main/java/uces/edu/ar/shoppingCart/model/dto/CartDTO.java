package uces.edu.ar.shoppingCart.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;

public class CartDTO {
	private Long id;
	private String fullName;
	private String email;
	@JsonFormat(pattern="dd-MM-yyyy")
	private LocalDate creationDate;
	@JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
	private LocalDateTime checkoutDate;
	private String status;
	private BigDecimal total;
	private Set<ProductInCartDTO> products = new HashSet<>();
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public LocalDate getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public Set<ProductInCartDTO> getProducts() {
		return products;
	}
	public void setProducts(Set<ProductInCartDTO> products) {
		this.products = products;
	}
	public void setProducts(ProductInCartDTO productDto) {
		this.products.add(productDto);	
	}
	public LocalDateTime getCheckoutDate() {
		return checkoutDate;
	}
	public void setCheckoutDate(LocalDateTime checkoutDate) {
		this.checkoutDate = checkoutDate;
	}
}
