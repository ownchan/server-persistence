package org.ownchan.server.persistence.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.session.ResultHandler;
import org.ownchan.server.persistence.mapper.PersistableObjectMapper;
import org.ownchan.server.persistence.mapper.generic.ColumnValue;
import org.ownchan.server.persistence.mapper.generic.FilterParam;
import org.ownchan.server.persistence.mapper.generic.LimitParam;
import org.ownchan.server.persistence.mapper.generic.SortingParam;
import org.ownchan.server.persistence.model.PersistableObject;
import org.ownchan.server.persistence.template.EntityTemplate;
import org.ownchan.server.persistence.template.link.EntityLinkTemplate;

public abstract class PersistableObjectDao<T extends PersistableObject<T, ?, ?> & EntityTemplate<?> & EntityLinkTemplate<?>, U extends PersistableObjectMapper<T>> implements PersistableObjectMapper<T> {

  protected abstract U getMapper();

  @Override
  public int insert(T persistableObject) {
    return getMapper().insert(persistableObject);
  }

  @Override
  public T get(long id) {
    return getMapper().get(id);
  }

  @Override
  public int update(T persistableObject) {
    return getMapper().update(persistableObject);
  }

  @Override
  public int delete(long id) {
    return getMapper().delete(id);
  }

  @Override
  public List<T> fetchAll() {
    return getMapper().fetchAll();
  }

  @Override
  public void streamAll(ResultHandler<T> resultHandler) {
    getMapper().streamAll(resultHandler);
  }

  @Override
  public List<T> fetch(
      List<List<FilterParam>> groupedFilterParams,
      List<SortingParam> sortingParams,
      LimitParam limitParam) {
    return getMapper().fetch(groupedFilterParams, sortingParams, limitParam);
  }

  @Override
  public void stream(
      List<List<FilterParam>> groupedFilterParams,
      List<SortingParam> sortingParams,
      LimitParam limitParam,
      ResultHandler<T> resultHandler) {
    getMapper().stream(groupedFilterParams, sortingParams, limitParam, resultHandler);
  }

  @Override
  public long set(
      Map<String, ColumnValue> columnToValueMap,
      List<List<FilterParam>> groupedFilterParams,
      List<SortingParam> sortingParams,
      LimitParam limitParam) {
    return getMapper().set(columnToValueMap, groupedFilterParams, sortingParams, limitParam);
  }

  @Override
  public long purge(
      List<List<FilterParam>> groupedFilterParams,
      List<SortingParam> sortingParams,
      LimitParam limitParam) {
    return getMapper().purge(groupedFilterParams, sortingParams, limitParam);
  }

  @Override
  public long purgeAll() {
    return getMapper().purgeAll();
  }

  @Override
  public List<BatchResult> flushStatements() {
    return getMapper().flushStatements();
  }

}
