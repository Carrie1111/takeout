package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {
    /**
     * 新增菜品和对应口味
     * @param dishDTO
     */
    public void saveWithFlavor(DishDTO dishDTO);

    /**
     * 菜品分页查询
     * @param dishPageQueryDTO
     * @return
     */
    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 菜品批量删除
     * @param ids
     */
    void deleteBatch(List<Long> ids);

    /**
     * 根据id查询菜品
     * @param id
     * @return
     */
    DishVO getByIdWithFlavor(Long id);

    /**
     * 修改菜品
     * @param dishDTO
     */
    void updateWithFlavor(DishDTO dishDTO);

    /**
     * 根据分类id查看菜品
     * @param categoryId
     * @return
     */
    List<Dish> list(long categoryId);

    /**
     * 根据菜品ID查询关联的套餐ID列表
     * @param dishId 菜品ID
     * @return 套餐ID列表
     */
    List<Long> getSetmealIdsByDishId(Long dishId);

    /**
     * 起售停售菜品
     * @param status
     * @param id
     * @return
     */
    void startOrStop(Integer status, Long id);

}
