package com.vietnam.lottery.business.actingHierarchyRelation.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vietnam.lottery.business.actingHierarchyRelation.entity.ActingHierarchyRelation;
import org.apache.ibatis.annotations.Mapper;

/**
 * 代理层级关系(ActingHierarchyRelation)表数据库访问层
 *
 * @author Gyan
 * @since 2022-02-16 11:41:43
 */
@Mapper
public interface ActingHierarchyRelationMapper extends BaseMapper<ActingHierarchyRelation> {
}

