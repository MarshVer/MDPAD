package com.zhuze.springboot.service.impl;

import com.zhuze.springboot.entity.Inventory;
import com.zhuze.springboot.mapper.InventoryMapper;
import com.zhuze.springboot.service.IInventoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2023-05-04
 */
@Service
public class InventoryServiceImpl extends ServiceImpl<InventoryMapper, Inventory> implements IInventoryService {

}
