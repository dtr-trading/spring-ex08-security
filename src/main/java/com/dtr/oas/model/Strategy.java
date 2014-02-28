package com.dtr.oas.model;

import java.io.Serializable;

import com.google.common.base.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "STRATEGY")
public class Strategy extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 96285180113476324L;

    @NotNull(message = "{error.strategy.type.null}")
    @NotEmpty(message = "{error.strategy.type.empty}")
    @Size(max = 20, message = "{error.strategy.type.max}")
    @Column(name = "TYPE", length = 20)
    private String type;

    @NotNull(message = "{error.strategy.name.null}")
    @NotEmpty(message = "{error.strategy.name.empty}")
    @Size(max = 20, message = "{error.strategy.name.max}")
    @Column(name = "NAME", length = 20)
    private String name;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("%s(id=%d, type='%s', name=%s)", 
                this.getClass().getSimpleName(), 
                this.getId(), this.getType(), this.getName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;

        if (o instanceof Strategy) {
            final Strategy other = (Strategy) o;
            return Objects.equal(getId(), other.getId())
                    && Objects.equal(getType(), other.getType())
                    && Objects.equal(getName(), other.getName());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId(), getType(), getName());
    }
}
