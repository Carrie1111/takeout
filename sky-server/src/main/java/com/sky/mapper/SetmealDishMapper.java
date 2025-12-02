package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface SetmealDishMapper {
    /**
     * 根据菜品id查询对应套餐id
     * @param mealDishIds
     * @return
     */
    List<Long> getSetmealDishIds(List<Long> mealDishIds);

    /**
     * 批量插入菜品
     * @param setmealDishes
     */
    void insertBatch(List<SetmealDish> setmealDishes);

    /**
     * 删除套餐里的菜品信息
     * @param setmealId
     */
    @Delete("delete from setmeal_dish where setmeal_id = #{setmealId}")
    void deleteBySetmealId(Long setmealId);

    /**
     * 根据id查询套餐和套餐菜品关系
     * @param id
     * @return
     */
    @Select("select * from setmeal_dish where setmeal_id = #{setmealId}")
    List<SetmealDish> getSetmealId(Long id);

    /**
     * 根据菜品ID查询关联的套餐ID列表
     * @param dishId 菜品ID
     * @return 套餐ID列表
     */
    @Select("SELECT setmeal_id FROM setmeal_dish WHERE dish_id = #{dishId}")
    List<Long> getSetmealIdsByDishId(Long dishId);
}
