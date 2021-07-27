package com.candy.bean.mybean;

import com.candy.bean.Permission;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Candy
 * @create 2021-07-07 16:48
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PermissionTree extends Permission {

    private Boolean open;
    private List<PermissionTree> children = new ArrayList<>();

}
