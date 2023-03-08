package com.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.reggie.common.CustomException;
import com.reggie.entity.Category;
import com.reggie.entity.Dish;
import com.reggie.entity.Setmeal;
import com.reggie.mapper.CategoryMapper;
import com.reggie.service.CategoryService;
import com.reggie.service.DishService;
import com.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;
    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> dishWrapper=new LambdaQueryWrapper<>();
        dishWrapper.eq(Dish::getCategoryId,id);
        long count = dishService.count(dishWrapper);
        if(count>0){
            //已关联菜品，抛出异常
            throw new CustomException("当前分类项已关联菜品");
        }
        LambdaQueryWrapper<Setmeal> setmealWrapper=new LambdaQueryWrapper<>();
        setmealWrapper.eq(Setmeal::getCategoryId,id);
        count=setmealService.count(setmealWrapper);
        if(count>0){
            //已关联菜品，抛出异常
            throw new CustomException("当前分类项已关联菜品");
        }
        super.removeById(id);

    }
}
