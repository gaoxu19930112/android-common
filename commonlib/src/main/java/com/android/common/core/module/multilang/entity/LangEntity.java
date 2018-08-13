package com.android.common.core.module.multilang.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Entity mapped to table "LANGENTITY".
 */
@Entity(createInDb = false, indexes = {
        @Index(value = "key DESC", unique = true)
})
public class LangEntity {
    @Id
    private Long id;
    @NotNull
    @Unique
    private String key;
    @NotNull
    private String value;
@Generated(hash = 575374429)
public LangEntity(Long id, @NotNull String key, @NotNull String value) {
    this.id = id;
    this.key = key;
    this.value = value;
}
@Generated(hash = 2066206437)
public LangEntity() {
}
public Long getId() {
    return this.id;
}
public void setId(Long id) {
    this.id = id;
}
public String getKey() {
    return this.key;
}
public void setKey(String key) {
    this.key = key;
}
public String getValue() {
    return this.value;
}
public void setValue(String value) {
    this.value = value;
}

}
