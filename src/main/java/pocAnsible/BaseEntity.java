package pocAnsible;

import java.io.Serializable;
import java.security.SecureRandom;

public abstract class BaseEntity implements Serializable {

    public static final Long nullId = 0L;

    protected Long id;

    protected BaseEntity() {
        this.id = new SecureRandom().nextLong();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof BaseEntity))
            return false;
        BaseEntity that = (BaseEntity) o;
        if (id != null && that.id != null)
            return id.equals(that.id);
        if (id != null || that.id != null)
            return false;
        return this == o;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : super.hashCode();
    }

}
