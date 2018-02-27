package net.myscloud.open.yuna.common.framework;

import lombok.Data;
import net.myscloud.open.yuna.common.framework.base.BaseBean;

@Data
public class PaginationParam extends BaseBean {
    private String order = "desc";
    private Integer limit = Integer.MAX_VALUE;
    private Long offset = 0L;
    private String sort = "id";
}
