package com.mrmf.util;

import org.apache.commons.beanutils.BeanPredicate;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.PredicateUtils;
import org.apache.commons.collections.functors.EqualPredicate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Util {

    public static Object   checkList(List list,String tableColumnName,String agers){
        List templist = new ArrayList();
        EqualPredicate parameter = new EqualPredicate(agers);
        BeanPredicate tableCoulmn_paramerter = new BeanPredicate(tableColumnName, parameter);
        Predicate[] allPredicateArray = {tableCoulmn_paramerter };
        Predicate allPredicate = PredicateUtils.allPredicate(allPredicateArray);
        Collection filteredCollection = CollectionUtils.select(list, allPredicate);
        Iterator<Object> dddd = filteredCollection.iterator();

        return dddd.next();
    }

}
