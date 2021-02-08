package org.dnd4.yorijori.domain.common;

import lombok.Data;

import java.util.List;

@Data
public class ResultList<T> extends BaseResult{
    private List<T> list;

    public ResultList(List<T> data){
        this.setList(data);
    }
}
