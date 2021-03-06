package win.doyto.query.module.menu;

import lombok.Getter;
import lombok.Setter;
import win.doyto.query.common.BeanUtil;
import win.doyto.query.entity.EntityRequest;

/**
 * MenuRequest
 *
 * @author f0rb
 */
@Getter
@Setter
public class MenuRequest implements EntityRequest<MenuEntity> {

    private Integer id;

    private String platform;

    private Integer parentId;

    private String menuName;

    private String memo;

    private Boolean valid = true;

    public MenuEntity toEntity() {
        return BeanUtil.copyFields(this, MenuEntity.class);
    }
}
