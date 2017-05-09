package com.company.db;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 *
 */

public class User extends RealmObject {
    @PrimaryKey
    private String id;
    private String name;
}
