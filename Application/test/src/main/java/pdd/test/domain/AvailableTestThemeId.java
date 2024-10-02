package pdd.test.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class AvailableTestThemeId implements Serializable {
    private static final long serialVersionUID = 1893741548703531333L;
    @Column(name = "at_id", nullable = false)
    private Integer atId;

    @Column(name = "qt_id", nullable = false)
    private Integer qtId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AvailableTestThemeId entity = (AvailableTestThemeId) o;
        return Objects.equals(this.atId, entity.atId) &&
                Objects.equals(this.qtId, entity.qtId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(atId, qtId);
    }

}