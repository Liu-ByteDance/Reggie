package com.itliu.reggie.dto;

import com.itliu.reggie.entity.Setmeal;
import com.itliu.reggie.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
