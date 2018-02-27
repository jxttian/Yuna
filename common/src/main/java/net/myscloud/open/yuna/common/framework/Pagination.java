package net.myscloud.open.yuna.common.framework;

import com.google.common.collect.Lists;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Pagination<R> implements Serializable {
    private List<R> rows;
    private long total;

    private Pagination(List<R> rows, long total) {
        this.rows = rows;
        this.total = total;
    }

    public static <R> Pagination<R> build(List<R> rows, long total) {
        return new Pagination<>(rows, total);
    }

    public static <R> Pagination<R> empty() {
        return new Pagination<>(Lists.newArrayList(), 0);
    }
}
