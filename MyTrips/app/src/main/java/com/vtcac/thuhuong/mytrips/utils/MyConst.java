package com.vtcac.thuhuong.mytrips.utils;

import com.vtcac.thuhuong.mytrips.base.MyKeyValue;

import java.util.HashMap;
import java.util.Map;

public class MyConst {
    public static final int REQCD_EDIT_TRAVEL = 1000;
    public static final int REQCD_ADD_TRAVEL = 1001;
    public static final String AC_TRAVEL = "AC_TRAVEL";
    public static final String AC_EDIT_TRAVEL = "REQAC_EDIT_TRAVEL";
    public static final String AC_ADD_TRAVEL = "AC_ADD_TRAVEL";
    public static final String OBJ_TRAVEL = "OBJ_TRAVEL";
    public static final int REQCD_SEARCH_TRAVELS_BY_CITY =3000 ;
    public static final int REQCD_PLACE_AUTOCOMPLETE =2000 ;
    public static final int SORT_BY_DEFAULT = 0;
    public static final int SORT_BY_TITLE_ASC = 1;
    public static final int SORT_BY_TITLE_DSC = 2;
    public static final int SORT_BY_DATE_ASC = 3;
    public static final int SORT_BY_DATE_DSC = 4;
    public static final String SORT_OPTION = "SORT_TRAVEL_OPTION";
    public static final String TRAVEL_SORT_KEY = "TRAVEL_SORT_KEY";
    public static final String TRAVEL_LIST_SIZE = "TRAVEL_LIST_SIZE";

    public static final int REQCD_PLACE_PICKER = 5000;
    public static final String REQKEY_TRAVEL_ID = "REQKEY_TRAVEL_ID";

    public static final String AC_PLAN = "AC_PLAN";
    public static final String AC_ADD_PLAN = "AC_ADD_PLAN";
    public static final String AC_EDIT_PLAN = "AC_EDIT_PLAN";
    public static final int REQCD_ADD_PLAN = 4000;
    public static final int REQCD_EDIT_PLAN = 4001;
    public static final String OBJ_PLAN = "OBJ_PLAN";


    public static final String AC_DIARY = "AC_DIARY";
    public static final String AC_ADD_DIARY = "AC_ADD_DIARY";
    public static final String AC_EDIT_DIARY = "AC_EDIT_DIARY";
    public static final String OBJ_DIARY = "OBJ_DIARY";
    public static final int REQCD_ADD_DIARY = 6000;
    public static final int REQCD_EDIT_DIARY = 6001;
    public static final int REQCD_IMAGE_GALLERY = 7000;
    public static final int REQCD_IMAGE_CAMERA = 7001;

    public static final int REQCD_ACCESS_GALLERY = 8000;
    public static final int REQCD_ACCESS_CAMERA = 8001;
    public static final int REQCD_ACCESS_FINE_LOCATION = 8002;

    public static final String AC_EXPENSE  = "AC_EXPENSE";
    public static final String AC_ADD_EXPENSE= "AC_ADD_EXPENSE";
    public static final String AC_EDIT_EXPENSE = "AC_EDIT_EXPENSE";
    public static final String OBJ_EXPENSE = "OBJ_EXPENSE";
    public static final int REQCD_ADD_EXPENSE = 9000;
    public static final int REQCD_EDIT_EXPENSE = 9001;

    public static final String KEY_ID = "KEY_ID";
    public static final String KEY_DATE = "KEY_DATE";
    public static final String KEY_TITLE = "KEY_TITLE";
    public static final String KEY_SUBTITLE = "KEY_SUBTITLE";
    public static final String KEY_DESC = "KEY_DESC";
    public static final String REQKEY_TRAVEL = "REQKEY_TRAVEL";

    public static final Map<String, MyKeyValue> CURRENCY_CODE = new HashMap<>();
    public static final Map<String, MyKeyValue> BUDGET_CODE = new HashMap<>();

    public static MyKeyValue getCurrencyCode(String key) {
        if (CURRENCY_CODE.containsKey(key)) return CURRENCY_CODE.get(key);
        return new MyKeyValue(0, "NA", "NA");
    }
    public static MyKeyValue getExpTypeCode(String key) {
        if (BUDGET_CODE.containsKey(key)) return BUDGET_CODE.get(key);
        return new MyKeyValue(0, "NA", "NA");
    }
}
