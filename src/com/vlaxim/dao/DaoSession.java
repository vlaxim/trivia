package com.vlaxim.dao;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.vlaxim.dao.User;
import com.vlaxim.dao.Question;
import com.vlaxim.dao.Game;
import com.vlaxim.dao.Score;

import com.vlaxim.dao.UserDao;
import com.vlaxim.dao.QuestionDao;
import com.vlaxim.dao.GameDao;
import com.vlaxim.dao.ScoreDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig userDaoConfig;
    private final DaoConfig questionDaoConfig;
    private final DaoConfig gameDaoConfig;
    private final DaoConfig scoreDaoConfig;

    private final UserDao userDao;
    private final QuestionDao questionDao;
    private final GameDao gameDao;
    private final ScoreDao scoreDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        userDaoConfig = daoConfigMap.get(UserDao.class).clone();
        userDaoConfig.initIdentityScope(type);

        questionDaoConfig = daoConfigMap.get(QuestionDao.class).clone();
        questionDaoConfig.initIdentityScope(type);

        gameDaoConfig = daoConfigMap.get(GameDao.class).clone();
        gameDaoConfig.initIdentityScope(type);

        scoreDaoConfig = daoConfigMap.get(ScoreDao.class).clone();
        scoreDaoConfig.initIdentityScope(type);

        userDao = new UserDao(userDaoConfig, this);
        questionDao = new QuestionDao(questionDaoConfig, this);
        gameDao = new GameDao(gameDaoConfig, this);
        scoreDao = new ScoreDao(scoreDaoConfig, this);

        registerDao(User.class, userDao);
        registerDao(Question.class, questionDao);
        registerDao(Game.class, gameDao);
        registerDao(Score.class, scoreDao);
    }
    
    public void clear() {
        userDaoConfig.getIdentityScope().clear();
        questionDaoConfig.getIdentityScope().clear();
        gameDaoConfig.getIdentityScope().clear();
        scoreDaoConfig.getIdentityScope().clear();
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public QuestionDao getQuestionDao() {
        return questionDao;
    }

    public GameDao getGameDao() {
        return gameDao;
    }

    public ScoreDao getScoreDao() {
        return scoreDao;
    }

}
