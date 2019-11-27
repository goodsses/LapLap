package com.sh.common.impl;

import com.sh.common.anno.CriteriaWhere;
import com.sh.common.bean.PageReq;
import com.sh.common.bean.SortRes;
import com.sh.common.entity.BaseEntity;
import com.sh.common.enu.WhereType;
import com.sh.common.service.CommonService;
import com.sh.common.service.Criteriable;
import com.sh.common.utils.DateTime;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.persistence.EmbeddedId;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.Transient;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author lxy 953250192@qq.com
 * @version 2019/4/10 16:36
 */
@Slf4j
public abstract class CommonServiceImpl<T extends Serializable, ID extends Serializable> implements CommonService<T, ID> {

    /**
     * public T save(T var1){
     * T t = getCommonService.save(var1);
     * return t;
     * }
     * 写到这有个想法，如果我的接口直接调用此封装好的方法，调用成功反参，如何告诉前端接口调用成功了呢？
     * 同时，后台与前台的交互需要一个规范化的反参结果集，里面的字段有：code(错误码)，suceess（成功与否），total（数量），result（结果集）
     * 这段注解就不删，留作纪念
     */

    private EntityManager em;

    /**
     * 实体类型
     */
    private Class beanClazz;

    /**
     * id类型
     */
    private Class idClazz;

    private Map<String, Field> fieldMap;

    private List<PropertyDescriptor> propertyDescriptors;

    private List<PropertyDescriptor> idPropertyDescriptors = new ArrayList<>();

    private CriteriaBuilder cb;

    private Root<T> listRoot;

    private Root<T> countRoot;

    private CriteriaQuery<T> listQuery;

    private CriteriaQuery<Long> countQuery;

    private Criteriable countCriteriable;

    private Criteriable criteriable;

    @PersistenceContext
    @SuppressWarnings("all")
    public void setEm(EntityManager em) {
        this.em = em;
        this.cb = em.getCriteriaBuilder();
        this.listQuery = cb.createQuery(beanClazz);
        this.countQuery = cb.createQuery(Long.class);
        this.listRoot = listQuery.from(beanClazz);
        this.countRoot = countQuery.from(beanClazz);
        //在criteriaQuery上设置查询表达式
        this.countQuery.select(cb.count(countRoot));
        //在criteriaQuery上设置查询表达式
        this.listQuery.select(listRoot);
        //这里是个重点，这里会先调用toPredicate接口，接口调用实现方法getCriteriable 博客<><><>:https://www.cnblogs.com/rever/p/9773743.html
        this.countCriteriable = t -> getCriteriable(t, countRoot);
        this.criteriable = t -> getCriteriable(t, listRoot);
        System.out.println("setEm执行了");
    }

    @SuppressWarnings("all")
    private Predicate getCriteriable(Object t, Root root) {
        List<Predicate> conditions = new ArrayList<>();
        for (PropertyDescriptor pd : propertyDescriptors) {
            Method readMethod = pd.getReadMethod();
            CriteriaWhere cAnno = readMethod.getAnnotation(CriteriaWhere.class);
            Transient trans = readMethod.getAnnotation(Transient.class);
            Object obj = null;
            String str = null;
            try {
                obj = readMethod.invoke(t);
            } catch (Exception e) {
            }
            if (trans == null && obj != null && !"".equals(str = obj.toString())) {
                Predicate predicate = null;
                String[] strs = null;
                if (cAnno != null) {
                    WhereType type = cAnno.type();
                    switch (type) {
                        case EQUAL:
                            predicate = cb.equal(root.get(pd.getName()), obj);
                            break;
                        case LIKE:
                            predicate = cb.like(root.get(pd.getName()), "%" + obj.toString() + "%");
                            break;
                        case BETWEEN:
                            strs = str.split(cAnno.linkChar(), -1);
                            if (strs.length == 1) {
                                predicate = cb.equal(root.get(pd.getName()), strs[0]);
                            } else {
                                if (StringUtils.isNotBlank(strs[0]) || StringUtils.isNotBlank(strs[1])) {
                                    if (StringUtils.isBlank(strs[0])) {
                                        predicate = cb.lessThanOrEqualTo(root.get(pd.getName()), strs[1]);
                                    } else if (StringUtils.isBlank(strs[1])) {
                                        predicate = cb.greaterThanOrEqualTo(root.get(pd.getName()), strs[0]);
                                    } else {
                                        predicate = cb.between(root.get(pd.getName()), strs[0], strs[1]);
                                    }
                                }
                            }
                            break;
                        case OR:
                            strs = str.split(cAnno.linkChar(), -1);
                            if (strs.length == 1) {
                                predicate = cb.equal(root.get(pd.getName()), strs[0]);
                            } else {
                                Predicate[] allPredicate = new Predicate[strs.length];
                                for (int i = 0; i < strs.length; i++) {
                                    allPredicate[i] = cb.equal(root.get(pd.getName()), strs[i]);
                                }
                                predicate = cb.or(allPredicate);
                            }
                            break;
                        case IN:
                            strs = str.split(cAnno.linkChar(), -1);
                            CriteriaBuilder.In in = cb.in(root.get(pd.getName()));
                            for (String s : strs) {
                                in.value(s);
                            }
                            predicate = cb.and(in);
                            break;
                        case GREATER:
                            predicate = cb.greaterThan(root.get(pd.getName()), str);
                            break;
                        default:
                    }
                } else {
                    // 默认为EQUAL
                    predicate = cb.equal(root.get(pd.getName()), obj);
                }
                if (null != predicate) {
                    conditions.add(predicate);
                }
            }
        }
        Predicate condition = cb.and(conditions.toArray(new Predicate[conditions.size()]));
        return condition;
    }


    @SuppressWarnings("all")
    public CommonServiceImpl() {
        System.out.println("执行了！！！");
        Class thisClazz = this.getClass();
        Type genericSuperclass = null;
        do {
            if (thisClazz.equals(Object.class)) {
                break;
            }
            //Type是java变成语言中所有类型的公共高级接口，其中包括原始类型，参数化类型，数组类型，类型变量，基本类型
            genericSuperclass = thisClazz.getGenericSuperclass();
            //getSuperclass()获得该类的父类
            thisClazz = thisClazz.getSuperclass();
        } while (!(genericSuperclass instanceof ParameterizedType));
        //ParameterizedType参数化类型，即泛型
        ParameterizedType type = (ParameterizedType) genericSuperclass;
        Type[] types = type.getActualTypeArguments();
        this.beanClazz = (Class) types[0];
        this.idClazz = (Class) types[1];
        Class clazzTemp = this.beanClazz;
        fieldMap = new HashMap<>();
        BeanInfo beanInfo = null;
        try {
            //在javabean上内省，了解所有公开方法和事件
            beanInfo = Introspector.getBeanInfo(this.beanClazz, Object.class);
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        for (Field field : clazzTemp.getDeclaredFields()) {
            fieldMap.put(field.getName(), field);
        }
        //这里其实就是实体类中属性的集合
        this.propertyDescriptors = Arrays.asList(beanInfo.getPropertyDescriptors());
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            Method readMethod = propertyDescriptor.getReadMethod();
            Id idAnno = readMethod.getAnnotation(Id.class);
            //将嵌入式主键类使用@Embeddable标注，表示这个是一个嵌入式类
            EmbeddedId emIdAnno = readMethod.getAnnotation(EmbeddedId.class);
            String fieldName = propertyDescriptor.getName();
            if (idAnno == null && emIdAnno == null) {
                if (fieldMap.containsKey(fieldName)) {
                    Field field = fieldMap.get(fieldName);
                    idAnno = field.getAnnotation(Id.class);
                    emIdAnno = readMethod.getAnnotation(EmbeddedId.class);
                }
            }
            if (idAnno != null || emIdAnno != null) {
                idPropertyDescriptors.add(propertyDescriptor);
            }
        }
    }

    @Override
    public List<T> findAll() {
        return getJpaRepository().findAll();
    }

    @SuppressWarnings("all")
    @Override
    public List<T> findAll(T var1, PageReq pageReq) {
        Predicate countCondition = this.countCriteriable.toPredicate(var1),
            condition = this.criteriable.toPredicate(var1);
        countQuery.where(countCondition);
        listQuery.where(condition);
        if (pageReq.getSorts() != null && !pageReq.getSorts().isEmpty()) {
            List<Order> orders = new ArrayList<>();
            for (SortRes sortRes : pageReq.getSorts()) {
                orders.add(
                    "desc".equalsIgnoreCase(sortRes.getDir()) ? cb.desc(listRoot.get(sortRes.getName())) : cb.asc(listRoot.get(sortRes.getName())));
            }
            listQuery.orderBy(orders);
        }
        TypedQuery<T> typedQuery = em.createQuery(listQuery);
        //hibernate 分页
        typedQuery.setFirstResult(pageReq.getPage() * pageReq.getSize());
        typedQuery.setMaxResults(pageReq.getSize());
        List<T> resultList = typedQuery.getResultList();
        return resultList;
    }

    @Override
    public T findById(ID var1) {
        Optional<T> t = getJpaRepository().findById(var1);
        return t.get();
    }

    @Override
    public List<T> findAllById(List<ID> var1) {
        return getJpaRepository().findAllById(var1);
    }

    @Override
    public T save(T var1) {
        //创建基础日期等数据
        wrapEntity(var1);
        return getJpaRepository().save(var1);
    }

    @Override
    public List<T> saveAll(List<T> var1) {
        List<T> tList = new ArrayList<>();
        for (T t : var1) {
            wrapEntity(t);
            T t1 = getJpaRepository().save(t);
            tList.add(t1);
        }
        return tList;
    }

    @Override
    public void deleteAll() {
        getJpaRepository().deleteAll();
    }

    @Override
    public void deleteAll(List<T> var1) {
        getJpaRepository().deleteAll(var1);
    }

    @Override
    public void deleteById(ID var1) {
        getJpaRepository().deleteById(var1);
    }

    @Override
    public void delete(T var1) {
        getJpaRepository().delete(var1);
    }

    @SuppressWarnings("all")
    @Override
    public T saveOrUpdate(T var1) {
        wrapEntity(var1);
        ID id = null;
        try {
            if (idPropertyDescriptors.size() > 1) {
                id = (ID) idClazz.newInstance();
                for (PropertyDescriptor propertyDescriptor : idPropertyDescriptors) {
                    Object invoke = propertyDescriptor.getReadMethod().invoke(var1);
                    idClazz.getMethod(propertyDescriptor.getWriteMethod().getName(), propertyDescriptor.getReadMethod().getReturnType()).invoke(id, invoke);
                }
            } else {
                id = (ID) idPropertyDescriptors.get(0).getReadMethod().invoke(var1);
            }
        } catch (Exception e) {
            log.error("反射获取id失败，错误原因<{}>", idPropertyDescriptors, e.getMessage());
            System.out.println(e.getMessage());
        }
        Optional<T> optional;
        if (id != null && (optional = getJpaRepository().findById(id)).isPresent()) {
            T t = optional.get();
            // todo copy传入的对象的属性到查询到的对象属性中取并保存，已达成修改
//            return getJpaRepository().save();
            return null;
        } else {
            return getJpaRepository().save(var1);
        }
    }

    @Override
    public List<T> saveOrUpdateAll(List<T> var1) {
        for (T t : var1) {
            wrapEntity(t);
            saveOrUpdate(t);
        }
        return var1;
    }

    @SuppressWarnings("all")
    @Override
    public boolean exists(T var1) {
        Predicate predicate = criteriable.toPredicate(var1);
        listQuery.where(predicate);
        T t = em.createQuery(listQuery).getSingleResult();
        return t != null;
    }

    @Override
    public boolean existsById(ID var1) {
        return getJpaRepository().existsById(var1);
    }

    @SuppressWarnings("all")
    @Override
    public long count(T var1) {
        Predicate predicate = this.countCriteriable.toPredicate(var1);
        countQuery.where(predicate);
        Long total = em.createQuery(countQuery).getSingleResult();
        return total;
    }

    @Override
    public long count() {
        return getJpaRepository().count();
    }

    private void wrapEntity(T var1) {
        if (var1 instanceof BaseEntity) {
            saveBase((BaseEntity) var1);
        }
    }

    private void saveBase(BaseEntity baseEntity) {
        if (StringUtils.isBlank(baseEntity.getId())) {
            baseEntity.setId(null);
        }
        baseEntity.setState(1);
        baseEntity.setCdate(DateTime.getStringYMD(new Date()));
        baseEntity.setCtime(DateTime.getStringYMDHMS(new Date()));
    }

}
