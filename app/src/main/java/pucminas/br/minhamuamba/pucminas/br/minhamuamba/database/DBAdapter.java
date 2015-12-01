package pucminas.br.minhamuamba.pucminas.br.minhamuamba.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {

    private static final String TAG = "DBAdapter";

    private static final String DATABASE_NAME = "MinhaMuambaDB";

    public static final String MUAMBA_TABLE = "muamba";
    public static final String MUAMBA_KEY_ROWID = "_identificador";
    public static final String MUAMBA_KEY_NOME = "nome";
    public static final String MUAMBA_KEY_VALORPAGO = "valorpago";
    public static final String MUAMBA_KEY_VALORATUAL = "valoratual";

    public static final String GAMES_TABLE = "games";
    public static final String GAMES_KEY_ROWID = "_id";
    public static final String GAMES_KEY_CONSOLE = "console";
    public static final String GAMES_KEY_ID_MUAMBA = "id_muamba";

    public static final String ACTION_FIGURES_TABLE = "actionfigures";
    public static final String ACTION_FIGURES_KEY_ROWID = "_id";
    public static final String ACTION_FIGURES_KEY_MARCA = "marca";
    public static final String ACTION_FIGURES_KEY_QUANTIDADE = "quantidade";
    public static final String ACTION_FIGURES_KEY_DEFEITOS = "defeitos";
    public static final String ACTION_FIGURES_KEY_ID_MUAMBA = "id_muamba";

    public static final String MANGAS_TABLE = "mangas";
    public static final String MANGAS_KEY_ROWID = "_id";
    public static final String MANGAS_KEY_VOLUME = "volume";
    public static final String MANGAS_KEY_ID_MUAMBA = "id_muamba";

    private static final int DATABASE_VERSION = 4;

    private static final String DATABASE_CREATE =
            "create table muamba (_identificador integer primary key autoincrement, nome text not null, valorpago real, valoratual real);"+
            "create table games (_id integer primary key autoincrement, console integer, id_muamba integer, FOREIGN KEY(id_muamba) REFERENCES muamba(_identificador));"+
            "create table mangas (_id integer primary key autoincrement, volume integer, id_muamba integer, FOREIGN KEY(id_muamba) REFERENCES muamba(_identificador));"+
            "create table actionfigures (_id integer primary key autoincrement, marca text, quantidade integer, defeitos text, id_muamba integer, FOREIGN KEY(id_muamba) REFERENCES muamba(_identificador));";

    private final Context context;

    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBAdapter(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            try {
                String[] queries = DATABASE_CREATE.split(";");
                for(String query : queries){
                    db.execSQL(query);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + GAMES_TABLE);
            db.execSQL("DROP TABLE IF EXISTS "+ACTION_FIGURES_TABLE);
            db.execSQL("DROP TABLE IF EXISTS "+MANGAS_TABLE);
            db.execSQL("DROP TABLE IF EXISTS "+MUAMBA_TABLE);
            onCreate(db);
        }
    }

    //---opens the database---
    public DBAdapter open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---closes the database---
    public void close()
    {
        DBHelper.close();
    }

    //---inserindo muamba no banco de dados---
    public long insertMuamba(String nome, Double valorPago, Double valorAtual)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(MUAMBA_KEY_NOME, nome);
        initialValues.put(MUAMBA_KEY_VALORPAGO, valorPago);
        initialValues.put(MUAMBA_KEY_VALORATUAL, valorAtual);

        return db.insert(MUAMBA_TABLE, null, initialValues);
    }

    //---deletando uma muamba---
    public boolean deleteMuamba(long rowId)
    {
        return db.delete(MUAMBA_TABLE, MUAMBA_KEY_ROWID + "=" + rowId, null) > 0;
    }

    //---recuperando todas as muambas---
    public Cursor getAllMuamba()
    {
        return db.query(MUAMBA_TABLE, new String[] {MUAMBA_KEY_ROWID, MUAMBA_KEY_NOME,
                MUAMBA_KEY_VALORPAGO, MUAMBA_KEY_VALORATUAL}, null, null, null, null, null);
    }

    //---obtendo uma muamba especifica---
    public Cursor getMuamba(long rowId) throws SQLException
    {
        Cursor mCursor =
                db.query(true, MUAMBA_TABLE, new String[] {MUAMBA_KEY_ROWID, MUAMBA_KEY_NOME,
                                MUAMBA_KEY_VALORPAGO, MUAMBA_KEY_VALORATUAL}, MUAMBA_KEY_ROWID + "=" + rowId, null,
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //---alterando uma muamba---
    public boolean updateMuamba(long rowId, String nome, Double valorPago, Double valorAtual)
    {
        ContentValues args = new ContentValues();
        args.put(MUAMBA_KEY_NOME, nome);
        args.put(MUAMBA_KEY_VALORPAGO, valorPago);
        args.put(MUAMBA_KEY_VALORATUAL, valorAtual);
        return db.update(MUAMBA_TABLE, args, MUAMBA_KEY_ROWID + "=" + rowId, null) > 0;
    }


    //---inserindo games no banco de dados---
    public long insertGames(Integer console, Long idMuamba)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(GAMES_KEY_CONSOLE, console);
        initialValues.put(GAMES_KEY_ID_MUAMBA, idMuamba);

        return db.insert(GAMES_TABLE, null, initialValues);
    }

    //---deletando uma games---
    public boolean deleteGames(long rowId)
    {
        return db.delete(GAMES_TABLE, GAMES_KEY_ROWID + "=" + rowId, null) > 0;
    }

    //---recuperando todas as games---
    public Cursor getAllGames()
    {
        return db.query(GAMES_TABLE, new String[] {GAMES_KEY_ROWID, GAMES_KEY_CONSOLE,
                GAMES_KEY_ID_MUAMBA}, null, null, null, null, null);
    }

    //---obtendo uma game especifico---
    public Cursor getGames(long rowId) throws SQLException
    {
        Cursor mCursor =
                db.query(true, GAMES_TABLE, new String[] {GAMES_KEY_ROWID, GAMES_KEY_CONSOLE,
                                GAMES_KEY_ID_MUAMBA}, GAMES_KEY_ROWID + "=" + rowId, null,
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //---alterando um game---
    public boolean updateGames(long rowId, Integer console, Long idMuamba)
    {
        ContentValues args = new ContentValues();
        args.put(GAMES_KEY_CONSOLE, console);
        args.put(GAMES_KEY_ID_MUAMBA, idMuamba);
        return db.update(GAMES_TABLE, args, GAMES_KEY_ROWID + "=" + rowId, null) > 0;
    }


    //---inserindo mangas no banco de dados---
    public long insertMangas(Integer volume, Long idMuamba)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(MANGAS_KEY_VOLUME, volume);
        initialValues.put(MANGAS_KEY_ID_MUAMBA, idMuamba);

        return db.insert(MANGAS_TABLE, null, initialValues);
    }

    //---deletando um manga---
    public boolean deleteMangas(long rowId)
    {
        return db.delete(MANGAS_TABLE, MANGAS_KEY_ROWID + "=" + rowId, null) > 0;
    }

    //---recuperando todas os mangas---
    public Cursor getAllMangas()
    {
        return db.query(MANGAS_TABLE, new String[] {MANGAS_KEY_ROWID, MANGAS_KEY_VOLUME,
                MANGAS_KEY_ID_MUAMBA}, null, null, null, null, null);
    }

    //---obtendo uma manga especifico---
    public Cursor getMangas(long rowId) throws SQLException
    {
        Cursor mCursor =
                db.query(true, MANGAS_TABLE, new String[] {MANGAS_KEY_ROWID, MANGAS_KEY_VOLUME,
                                MANGAS_KEY_ID_MUAMBA}, MANGAS_KEY_ROWID + "=" + rowId, null,
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //---alterando um manga---
    public boolean updateMangs(long rowId, Integer volume, Long idMuamba)
    {
        ContentValues args = new ContentValues();
        args.put(MANGAS_KEY_VOLUME, volume);
        args.put(MANGAS_KEY_ID_MUAMBA, idMuamba);
        return db.update(MANGAS_TABLE, args, MANGAS_KEY_ROWID + "=" + rowId, null) > 0;
    }



    //---inserindo action figure no banco de dados---
    public long insertActionFigures(String marca, Integer quantidade, String defeitos, Long idMuamba)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(ACTION_FIGURES_KEY_MARCA, marca);
        initialValues.put(ACTION_FIGURES_KEY_QUANTIDADE, quantidade);
        initialValues.put(ACTION_FIGURES_KEY_DEFEITOS, defeitos);
        initialValues.put(ACTION_FIGURES_KEY_ID_MUAMBA, idMuamba);

        return db.insert(ACTION_FIGURES_TABLE, null, initialValues);
    }

    //---deletando um action figure---
    public boolean deleteActionFigures(long rowId)
    {
        return db.delete(ACTION_FIGURES_TABLE, ACTION_FIGURES_KEY_ROWID + "=" + rowId, null) > 0;
    }

    //---recuperando todas os Action Figures---
    public Cursor getAllActionFigures()
    {
        return db.query(ACTION_FIGURES_TABLE, new String[] {ACTION_FIGURES_KEY_ROWID, ACTION_FIGURES_KEY_MARCA, ACTION_FIGURES_KEY_QUANTIDADE, ACTION_FIGURES_KEY_DEFEITOS,
                ACTION_FIGURES_KEY_ID_MUAMBA}, null, null, null, null, null);
    }

    //---obtendo uma Action Figures especifica---
    public Cursor getActionFigures(long rowId) throws SQLException
    {
        Cursor mCursor =
                db.query(true, ACTION_FIGURES_TABLE, new String[] {ACTION_FIGURES_KEY_ROWID, ACTION_FIGURES_KEY_MARCA, ACTION_FIGURES_KEY_QUANTIDADE, ACTION_FIGURES_KEY_DEFEITOS,
                                ACTION_FIGURES_KEY_ID_MUAMBA}, ACTION_FIGURES_KEY_ROWID + "=" + rowId, null,
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //---alterando um Action Figure---
    public boolean updateActionFigures(long rowId, String marca, Integer quantidade, String defeitos, Long idMuamba)
    {
        ContentValues args = new ContentValues();
        args.put(ACTION_FIGURES_KEY_MARCA, marca);
        args.put(ACTION_FIGURES_KEY_QUANTIDADE, quantidade);
        args.put(ACTION_FIGURES_KEY_DEFEITOS, defeitos);
        args.put(ACTION_FIGURES_KEY_ID_MUAMBA, idMuamba);
        return db.update(ACTION_FIGURES_TABLE, args, ACTION_FIGURES_KEY_ROWID + "=" + rowId, null) > 0;
    }

}
