package com.epartner.domain;

import org.springframework.web.servlet.tags.form.OptionsTag;

import javax.persistence.*;
import java.util.Optional;

/**
 * Created by mbritez on 24/09/16.
 */
@Entity
public class TechnicalSpecification {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String key;
    private String value;
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="product_id")
    private Product product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {

        if(sameAsFormer(product))
            return ;
        Product oldProduct = this.product;

        this.product = product;

        if(oldProduct != null){

            oldProduct.removeTechinicalSpecification(this);
        }

        this.product = product;
    }

    private boolean sameAsFormer(Product product){

        return this.product == null ? product == null : this.product.equals(product);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TechnicalSpecification that = (TechnicalSpecification) o;

        return !(id != null ? !id.equals(that.id) : that.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public boolean isNew() {

        return !Optional.ofNullable(this.id).isPresent();
    }

    public TechnicalSpecification merge(TechnicalSpecification technicalSpecification) {

        this.key = technicalSpecification.getKey();
        this.value = technicalSpecification.getValue();

        return this;
    }
}
