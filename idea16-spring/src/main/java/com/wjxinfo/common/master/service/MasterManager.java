package com.wjxinfo.common.master.service;

import com.wjxinfo.common.master.dao.MasterDao;
import com.wjxinfo.common.master.dao.MasterTypeDao;
import com.wjxinfo.common.master.model.Master;
import com.wjxinfo.common.master.model.MasterType;
import com.wjxinfo.core.base.constants.Constants;
import com.wjxinfo.core.base.service.BaseManager;
import com.wjxinfo.core.base.service.DBUtilsManager;
import com.wjxinfo.core.base.utils.common.CollectionHelper;
import com.wjxinfo.core.base.utils.common.StringUtils;
import com.wjxinfo.core.base.vo.LabelValue;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import javax.persistence.metamodel.EntityType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service("masterManager")
public class MasterManager extends BaseManager<Master> {
    private static final Logger logger = LoggerFactory.getLogger(MasterManager.class);

    @Qualifier("masterDao")
    @Autowired
    private MasterDao masterDao;

    @Qualifier("masterTypeDao")
    @Autowired
    private MasterTypeDao masterTypeDao;

    @Qualifier("dbUtilsManager")
    @Autowired
    private DBUtilsManager dbUtilsManager;

    @Qualifier("masterTypeManager")
    @Autowired
    private MasterTypeManager masterTypeManager;

//    @Qualifier("ldapManager")
//    @Autowired
//    private LdapManager ldapManager;

    @Autowired
    private EntityManagerFactory entityManagerFactory;


    public List<Master> findByMasterType(MasterType masterType) {
        if (null == masterType || null == masterType.getId()) {
            return null;
        }

        return masterDao.findByMasterTypeId(masterType.getId());
    }

    public List<Master> findByMasterType(String type) {
        List<MasterType> list = masterTypeDao.findByType(type);
        if (list != null && list.size() > 0) {

            return findByMasterType(list.get(0));
        }

        return new ArrayList<Master>();
    }

    public List<Master> findByMasterTypeAndParentValue(String type, String parentValue) {
        List<MasterType> list = masterTypeDao.findByType(type);
        if (list != null && list.size() > 0) {

            if (StringUtils.isNotBlank(parentValue)) {
                return masterDao.findByMasterTypeIdAndParentValue(list.get(0).getId(), parentValue);
            } else {
                return masterDao.findByMasterTypeId(list.get(0).getId());
            }
        }

        return new ArrayList<Master>();
    }

    public Master findByMasterTypeAndParentValueAndValue(String type, String parentValue, String value) {
        if (StringUtils.isNotEmpty(parentValue) && StringUtils.isNotEmpty(value)) {
            List<Master> list = findByMasterTypeAndParentValue(type, parentValue);
            for (Master m : list) {
                if (value.equals(m.getValue())) {
                    return m;
                }
            }
        }
        return new Master();
    }

    public List<LabelValue> getLabelValueList(String type) {
        // AD User List
        if (type != null && type.equalsIgnoreCase(Constants.LABEL_VALUE_AD_USER)) {
            return getAdUserList();
        }

        // Entity List
        if (type != null && type.equalsIgnoreCase(Constants.LABEL_VALUE_ENTITY)) {
            return getEntityList();
        }

        // Specified Label Value
        if (ArrayUtils.contains(Constants.SPECIFIED_LABEL_VALUE, type)) {
            return dbUtilsManager.getLabelValues(type);
        }

        // Generic Master
        List<Master> list = findByMasterType(type);
        List<LabelValue> result = new ArrayList<LabelValue>();

        for (Master master : list) {
            result.add(convert(master));
        }

        return result;
    }

    public List<LabelValue> getFullLabelValueList(String type) {
        // AD User List
        if (type != null && type.equalsIgnoreCase(Constants.LABEL_VALUE_AD_USER)) {
            return getAdUserList();
        }

        // Entity List
        if (type != null && type.equalsIgnoreCase(Constants.LABEL_VALUE_ENTITY)) {
            return getEntityList();
        }

        // Specified Label Value
        if (ArrayUtils.contains(Constants.SPECIFIED_LABEL_VALUE, type)) {
            return dbUtilsManager.getLabelValues(type);
        }

        // Generic Master
        List<Master> list = findByMasterType(type);
        List<LabelValue> result = new ArrayList<LabelValue>();

        for (Master master : list) {
            result.add(convertFull(master));
        }

        return result;
    }

    public List<LabelValue> getLabelValueListByTerm(String type, String term) {
        List<LabelValue> labelValueList = null;
        List<LabelValue> result = null;
        if (StringUtils.isNotBlank(type)) {
            labelValueList = getLabelValueList(type);
        }
        if (CollectionHelper.isNotEmpty(labelValueList)) {
            result = getLabelValueListByTerm(labelValueList, term);
        }
        return result;
    }

    public List<LabelValue> getLabelValueListByTerm(List<LabelValue> labelValueList, String term) {
        List<LabelValue> result = new ArrayList<LabelValue>();
        if (CollectionHelper.isNotEmpty(labelValueList)) {
            for (LabelValue labelValue : labelValueList) {
                if (labelValue != null && StringUtils.isNotBlank(labelValue.getLabel())
                        && labelValue.getLabel().toLowerCase().startsWith(term.trim().toLowerCase())) {
                    result.add(labelValue);
                }
            }
        }
        return result;
    }

    public String getValueByTypeAndLabel(String type, String label) {
        Master master = masterDao.getMasterByLabel(type, label);
        if (master != null) {
            return master.getValue();
        } else {
            return "";
        }
    }

    public String getLabelByTypeAndValue(String type, String value) {
        Master master = masterDao.getMasterByValue(type, value);
        if (master != null) {
            return master.getLabel();
        } else {
            return "";
        }
    }

    public Master getMasterByTypeAndValue(String type, String value) {
        return masterDao.getMasterByValue(type, value);
    }

    public String getAdditionalValueByTypeAndValue(String type, String value, Integer seq) {
        Master master = masterDao.getMasterByValue(type, value);
        if (master != null) {
            if (seq == null) {
                seq = 1;
            }
            if (seq == 1) {
                return master.getAdditionalValue();
            } else if (seq == 2) {
                return master.getAdditionalValue2();
            } else if (seq == 3) {
                return master.getAdditionalValue3();
            } else {
                return master.getAdditionalValue();
            }
        }
        return "";
    }

    public List<LabelValue> getCascadeFullLabelValueList(String type, String parentValue) {
        List<Master> list = findByMasterTypeAndParentValue(type, parentValue);
        List<LabelValue> result = new ArrayList<LabelValue>();

        for (Master master : list) {
            result.add(convertFull(master));
        }
        return result;
    }

    public List<LabelValue> getCascadeLabelValueList(String type, String parentValue) {
        List<Master> list = findByMasterTypeAndParentValue(type, parentValue);
        List<LabelValue> result = new ArrayList<LabelValue>();

        for (Master master : list) {
            result.add(convert(master));
        }
        return result;
    }

    public List<LabelValue> getLabelValueListByFilter(String type, String filter) {
        List<LabelValue> result = new ArrayList<LabelValue>();
        List<Master> masters = masterDao.findByTypeAndValue(type, filter);
        for (Master master : masters) {
            result.addAll(convert(master, ","));
        }
        return result;
    }

    public List<LabelValue> getLabelListByFilter(String type, String filter) {
        List<LabelValue> result = new ArrayList<LabelValue>();
        List<Master> masters = masterDao.findByTypeAndValue(type, filter);
        for (Master master : masters) {
            String label = master.getLabel();
            String[] list = label.split(",");
            for (String lab : list) {
                LabelValue lv = new LabelValue();
                lv.setLabel(lab);
                lv.setValue(lab);
                result.add(lv);
            }
        }
        return result;
    }

    public LabelValue convertFull(Master master) {
        if (master == null) {
            return null;
        }

        LabelValue labelValue = new LabelValue();
        labelValue.setLabel(master.getValue() + " - " + master.getLabel());
        labelValue.setValue(master.getValue());

        return labelValue;
    }

    public LabelValue convert(Master master) {
        if (master == null) {
            return null;
        }

        LabelValue labelValue = new LabelValue();
        labelValue.setLabel(master.getLabel());
        labelValue.setValue(master.getValue());

        return labelValue;
    }

    public List<LabelValue> convert(Master master, String delimiter) {
        if (master == null) {
            return null;
        }
        List<LabelValue> list = new ArrayList<>();
        String label = master.getLabel();
        String value = master.getValue();
        String[] labList = label.split(delimiter);
        for (String lab : labList) {
            LabelValue lv = new LabelValue();
            lv.setLabel(lab);
            lv.setValue(value);
            list.add(lv);
        }
        return list;
    }

    private List<LabelValue> getEntityList() {
        List<LabelValue> list = new ArrayList<LabelValue>();

        LabelValue labelValue1 = new LabelValue();
        labelValue1.setLabel("--- Please Select ---");
        labelValue1.setValue("");
        list.add(labelValue1);

        for (EntityType<?> entity : entityManagerFactory.getMetamodel().getEntities()) {
            LabelValue labelValue = new LabelValue();
            labelValue.setLabel(entity.getName());
            labelValue.setValue(entity.getJavaType().getName());
            list.add(labelValue);
        }

        labelValueSort(list);
        return list;
    }

    private List<LabelValue> getAdUserList() {
        List<LabelValue> list = new ArrayList<LabelValue>();
        return list;
    }

    public void labelValueSort(List labelValueList) {
        Collections.sort(labelValueList, new Comparator<LabelValue>() {

            public int compare(LabelValue o1, LabelValue o2) {
                int result = o1.getLabel().compareTo(o2.getLabel());
                return result;
            }
        });
    }

    public Master findByLabel(String mastType, String label) {
        return masterDao.getMasterByLabel(mastType, label);
    }

    //bt:12657
    public List<LabelValue> getLabelValueListByValues(String type, String values) {
        List<LabelValue> result = new ArrayList<LabelValue>();
        List<Master> masters = null;
        if (StringUtils.isNotEmpty(values)) {
            String[] valueArr = values.split(",");
            for (String value : valueArr) {
                masters = masterDao.findByTypeAndValue(type, value);
                for (Master master : masters) {
                    String masterLabel = master.getLabel();
                    String[] list = masterLabel.split(",");
                    for (String lab : list) {
                        LabelValue lv = new LabelValue();
                        lv.setLabel(lab);
                        lv.setValue(master.getValue());
                        result.add(lv);
                    }
                }
            }
        }
        return result;
    }

    public List<LabelValue> getCascadeLabelValueListBySqlXml(String type, String parentValue) {
        return dbUtilsManager.getLabelValues(type, parentValue);
    }
}
