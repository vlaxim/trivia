package com.vlaxim.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.vlaxim.dao.User;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/**
 * DAO for table USER.
 */
public class UserDao extends AbstractDao<User, Long> {

	public static final String TABLENAME = "USER";

	/**
	 * Properties of entity User.<br/>
	 * Can be used for QueryBuilder and for referencing column names.
	 */
	public static class Properties {
		public final static Property Id = new Property(0, Long.class, "id",
				true, "_id");
		public final static Property FirstName = new Property(1, String.class,
				"firstName", false, "FIRST_NAME");
		public final static Property LastName = new Property(2, String.class,
				"lastName", false, "LAST_NAME");
		public final static Property Pseudo = new Property(3, String.class,
				"pseudo", false, "PSEUDO");
		public final static Property Password = new Property(4, String.class,
				"password", false, "PASSWORD");
		public final static Property Mail = new Property(5, String.class,
				"mail", false, "MAIL");
	};

	private DaoSession daoSession;

	public UserDao(DaoConfig config) {
		super(config);
	}

	public UserDao(DaoConfig config, DaoSession daoSession) {
		super(config, daoSession);
		this.daoSession = daoSession;
	}

	/** Creates the underlying database table. */
	public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
		String constraint = ifNotExists ? "IF NOT EXISTS " : "";
		db.execSQL("CREATE TABLE " + constraint + "'USER' (" + //
				"'_id' INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
				"'FIRST_NAME' TEXT," + // 1: firstName
				"'LAST_NAME' TEXT," + // 2: lastName
				"'PSEUDO' TEXT," + // 3: pseudo
				"'PASSWORD' TEXT," + // 4: password
				"'MAIL' TEXT);"); // 5: mail
	}

	/** Drops the underlying database table. */
	public static void dropTable(SQLiteDatabase db, boolean ifExists) {
		String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'USER'";
		db.execSQL(sql);
	}

	/** @inheritdoc */
	@Override
	protected void bindValues(SQLiteStatement stmt, User entity) {
		stmt.clearBindings();

		Long id = entity.getId();
		if (id != null) {
			stmt.bindLong(1, id);
		}

		String firstName = entity.getFirstName();
		if (firstName != null) {
			stmt.bindString(2, firstName);
		}

		String lastName = entity.getLastName();
		if (lastName != null) {
			stmt.bindString(3, lastName);
		}

		String pseudo = entity.getPseudo();
		if (pseudo != null) {
			stmt.bindString(4, pseudo);
		}

		String password = entity.getPassword();
		if (password != null) {
			stmt.bindString(5, password);
		}

		String mail = entity.getMail();
		if (mail != null) {
			stmt.bindString(6, mail);
		}
	}

	@Override
	protected void attachEntity(User entity) {
		super.attachEntity(entity);
		entity.__setDaoSession(daoSession);
	}

	/** @inheritdoc */
	@Override
	public Long readKey(Cursor cursor, int offset) {
		return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
	}

	/** @inheritdoc */
	@Override
	public User readEntity(Cursor cursor, int offset) {
		User entity = new User(
				//
				cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
				cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // firstName
				cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // lastName
				cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // pseudo
				cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // password
				cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5) // mail
		);
		return entity;
	}

	/** @inheritdoc */
	@Override
	public void readEntity(Cursor cursor, User entity, int offset) {
		entity.setId(cursor.isNull(offset + 0) ? null : cursor
				.getLong(offset + 0));
		entity.setFirstName(cursor.isNull(offset + 1) ? null : cursor
				.getString(offset + 1));
		entity.setLastName(cursor.isNull(offset + 2) ? null : cursor
				.getString(offset + 2));
		entity.setPseudo(cursor.isNull(offset + 3) ? null : cursor
				.getString(offset + 3));
		entity.setPassword(cursor.isNull(offset + 4) ? null : cursor
				.getString(offset + 4));
		entity.setMail(cursor.isNull(offset + 5) ? null : cursor
				.getString(offset + 5));
	}

	/** @inheritdoc */
	@Override
	protected Long updateKeyAfterInsert(User entity, long rowId) {
		entity.setId(rowId);
		return rowId;
	}

	/** @inheritdoc */
	@Override
	public Long getKey(User entity) {
		if (entity != null) {
			return entity.getId();
		} else {
			return null;
		}
	}

	/** @inheritdoc */
	@Override
	protected boolean isEntityUpdateable() {
		return true;
	}

}
