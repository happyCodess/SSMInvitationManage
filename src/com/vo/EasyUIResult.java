package com.vo;

import java.util.List;

/**
 * 
 * @ClassName:  EasyUIResult   
 * @Description: ���ݱ�񷵻ظ�ʽ
 * @author: xyc 
 * @date:   2017��2��15�� ����2:06:30   
 *
 */
public class EasyUIResult {

    
    private Long total;
    
    private List<?> rows;
    
    public EasyUIResult() {}

    public EasyUIResult(Long total, List<?> rows) {
        this.total = total;
        this.rows = rows;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }
    
    
}

