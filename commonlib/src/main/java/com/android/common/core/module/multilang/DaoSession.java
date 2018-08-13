package com.android.common.core.module.multilang;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.android.common.core.module.download.DownInfo;
import com.android.common.core.module.multilang.entity.LangEntity;

import com.android.common.core.module.multilang.DownInfoDao;
import com.android.common.core.module.multilang.LangEntityDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig downInfoDaoConfig;
    private final DaoConfig langEntityDaoConfig;

    private final DownInfoDao downInfoDao;
    private final LangEntityDao langEntityDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        downInfoDaoConfig = daoConfigMap.get(DownInfoDao.class).clone();
        downInfoDaoConfig.initIdentityScope(type);

        langEntityDaoConfig = daoConfigMap.get(LangEntityDao.class).clone();
        langEntityDaoConfig.initIdentityScope(type);

        downInfoDao = new DownInfoDao(downInfoDaoConfig, this);
        langEntityDao = new LangEntityDao(langEntityDaoConfig, this);

        registerDao(DownInfo.class, downInfoDao);
        registerDao(LangEntity.class, langEntityDao);
    }
    
    public void clear() {
        downInfoDaoConfig.clearIdentityScope();
        langEntityDaoConfig.clearIdentityScope();
    }

    public DownInfoDao getDownInfoDao() {
        return downInfoDao;
    }

    public LangEntityDao getLangEntityDao() {
        return langEntityDao;
    }

}