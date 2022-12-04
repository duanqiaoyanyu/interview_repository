package com.cskaoyan.config;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.UpdateChainWrapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 继承该类可以拥有 mapper 层 选装件
 * @see <a href="https://baomidou.com/pages/49cc81/#mapper-%E5%B1%82-%E9%80%89%E8%A3%85%E4%BB%B6">官方文档</a>
 * </p>
 *
 * @author duanqiaoyanyu
 * @date 2022/12/02 15点04分
 */
public interface MyBaseMapper<T> extends BaseMapper<T> {

    /* ↓↓↓↓↓↓↓↓↓↓↓↓↓↓  ↓↓↓↓↓↓↓↓↓↓↓↓↓↓ */

    /**
     * 以下定义的 4个 default method, copy from {@link com.baomidou.mybatisplus.extension.toolkit.ChainWrappers}
     */
    default QueryChainWrapper<T> queryChain() {
        return new QueryChainWrapper<>(this);
    }

    default LambdaQueryChainWrapper<T> lambdaQueryChain() {
        return new LambdaQueryChainWrapper<>(this);
    }

    default UpdateChainWrapper<T> updateChain() {
        return new UpdateChainWrapper<>(this);
    }

    default LambdaUpdateChainWrapper<T> lambdaUpdateChain() {
        return new LambdaUpdateChainWrapper<>(this);
    }

    /**
     * 以下定义的是内置的选装件 {@link com.baomidou.mybatisplus.extension.injector.methods.InsertBatchSomeColumn}
     */
    int insertBatchSomeColumn(List<T> entityList);

    /**
     * {@link com.baomidou.mybatisplus.extension.injector.methods.AlwaysUpdateSomeColumnById}
     *
     * @param entity
     * @return
     */
    int alwaysUpdateSomeColumnById(@Param(Constants.ENTITY) T entity);

    /**
     * {@link com.baomidou.mybatisplus.extension.injector.methods.LogicDeleteByIdWithFill}
     *
     * @param entity
     * @return
     */
    int deleteByIdWithFill(T entity);

}
