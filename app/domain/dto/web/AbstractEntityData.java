package domain.dto.web;

/**
 * Created by Hatake on 2015-03-25.
 */
public abstract class AbstractEntityData {

    public Long id;
    public String idString;

    public AbstractEntityData(Long id) {
        this.id = id;
        this.idString = id.toString();
    }
}
