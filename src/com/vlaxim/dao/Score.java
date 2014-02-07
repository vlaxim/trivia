package com.vlaxim.dao;

import java.util.List;

import com.vlaxim.dao.DaoSession;

import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table SCORE.
 */
public class Score {

    private Long id;
    private Integer score;
    private long gameId;
    private long userId;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient ScoreDao myDao;

    private List<Score> scoreToGame;
    private List<Score> scoreToUser;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public Score() {
    }

    public Score(Long id) {
        this.id = id;
    }

    public Score(Long id, Integer score, long gameId, long userId) {
        this.id = id;
        this.score = score;
        this.gameId = gameId;
        this.userId = userId;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getScoreDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    public List<Score> getScoreToGame() {
        if (scoreToGame == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ScoreDao targetDao = daoSession.getScoreDao();
            List<Score> scoreToGameNew = targetDao._queryScore_ScoreToGame(id);
            synchronized (this) {
                if(scoreToGame == null) {
                    scoreToGame = scoreToGameNew;
                }
            }
        }
        return scoreToGame;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    public synchronized void resetScoreToGame() {
        scoreToGame = null;
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    public List<Score> getScoreToUser() {
        if (scoreToUser == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ScoreDao targetDao = daoSession.getScoreDao();
            List<Score> scoreToUserNew = targetDao._queryScore_ScoreToUser(id);
            synchronized (this) {
                if(scoreToUser == null) {
                    scoreToUser = scoreToUserNew;
                }
            }
        }
        return scoreToUser;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    public synchronized void resetScoreToUser() {
        scoreToUser = null;
    }

    /** Convenient call for {@link AbstractDao#delete(Object)}. Entity must attached to an entity context. */
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.delete(this);
    }

    /** Convenient call for {@link AbstractDao#update(Object)}. Entity must attached to an entity context. */
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.update(this);
    }

    /** Convenient call for {@link AbstractDao#refresh(Object)}. Entity must attached to an entity context. */
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.refresh(this);
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}
