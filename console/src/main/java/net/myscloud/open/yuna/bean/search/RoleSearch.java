package net.myscloud.open.yuna.bean.search;

import lombok.Data;
import net.myscloud.open.yuna.common.framework.PaginationParam;

/**
 * Created by genesis on 2016/12/29.
 */
@Data
public class RoleSearch extends PaginationParam {
    private String code;
    private String name;
}
