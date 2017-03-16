package com.wjxinfo.common.master.dao;

import com.wjxinfo.common.master.model.Master;
import com.wjxinfo.core.base.dao.BaseDao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by MZJ on 14-5-12.
 */
@Repository("masterDao")
public interface MasterDao extends BaseDao<Master> {
    //@Query("select o from Master o left join o.masterType p where p.id=?1 order by o.seq, o.label")
    @Query("select o from Master o where o.masterType.id = ?1 order by o.seq")
    List<Master> findByMasterTypeId(String id);

    @Query("select o from Master o where o.masterType.id = ?1 and o.parent.value = ?2 order by o.seq, o.label")
    List<Master> findByMasterTypeIdAndParentValue(String typeId, String parentValue);

    @Query("select o from Master o where o.masterType.type = ?1 and o.label = ?2")
    Master getMasterByLabel(String mastType, String label);

    @Query("select o from Master o where o.masterType.type = ?1 and o.value = ?2")
    Master getMasterByValue(String mastType, String value);

    @Query("select distinct o.label from Master o left join o.masterType p where p.type = ?1 and o.value = ?2")
    String findLabelByTypeAndValue(String masterType, String value);

    @Query(nativeQuery = true, value = "SELECT (SELECT DISTINCT m.label FROM t_bas_master m LEFT OUTER JOIN t_bas_master_type t ON m.master_type_id = t.id WHERE t.master_type = ?1 AND m.value IN ?2 FOR XML PATH(''),TYPE).value('.','nvarchar(max)') as label")
    String findLabelByTypeAndValues(String masterType, List<String> values);

    @Query("select distinct o from Master o left join o.masterType p where p.type = ?1 and o.value = ?2 order by o.seq, o.label")
    List<Master> findByTypeAndValue(String masterType, String value);
}
