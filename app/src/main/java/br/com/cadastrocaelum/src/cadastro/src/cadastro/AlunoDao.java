package br.com.cadastrocaelum.src.cadastro.src.cadastro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.com.cadastrocaelum.src.cadastro.src.modelo.Aluno;

/**
 * Created by Bruno - PC on 29/08/2015.
 */
public class AlunoDao extends SQLiteOpenHelper{

    private static final String DATABASE = "CadastroCaelum";


    public AlunoDao(Context context) {
        super(context, DATABASE, null, 1);


    }

    public List<Aluno> getLista(){
        String[] colunas = {"id", "nome", "telefone", "endereco", "foto", "nota", "site"};
        Cursor cursor = getWritableDatabase().query("Alunos", colunas, null, null, null, null, null);

        ArrayList<Aluno> alunos = new ArrayList<Aluno>();


        while(cursor.moveToNext()){
            Aluno aluno = new Aluno();
            aluno.setId(cursor.getInt(0));
            String nome = cursor.getString(1);
            aluno.setNome(nome);
            aluno.setTelefone(cursor.getString(2));
            aluno.setEndereco(cursor.getString(3));
            aluno.setFoto(cursor.getString(4));
            aluno.setNota(cursor.getDouble(5));
            aluno.setSite(cursor.getString(6));

            alunos.add(aluno);
        }

        return alunos;
    }

    public void salva(Aluno aluno){
        ContentValues values =  new ContentValues();
        values.put("nome", aluno.getNome());
        values.put("endereco", aluno.getEndereco());
        values.put("site",aluno.getSite());
        values.put("foto", aluno.getFoto());
        values.put("nota", aluno.getNota());
        values.put("telefone", aluno.getTelefone());
            getWritableDatabase().insert("Alunos" ,null,values );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String ddl = "CREATE TABLE Alunos (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " nome TEXT, telefone TEXT, " +
                " endereco TEXT, site TEXT, foto TEXT, nota REAL); ";
        db.execSQL(ddl);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String ddl  = "DROP TABLE IF EXISTS Alunos";
        db.execSQL(ddl);
        this.onCreate(db);
    }

    public void deletar(Aluno aluno) {
        String[] args = {String.valueOf(aluno.getId())};
        getWritableDatabase().delete("Alunos", "id=?", args);
    }

    public void altera(Aluno aluno) {

        ContentValues values =  new ContentValues();
        values.put("nome", aluno.getNome());
        values.put("endereco", aluno.getEndereco());
        values.put("site",aluno.getSite());
        values.put("foto", aluno.getFoto());
        values.put("nota", aluno.getNota());
        values.put("telefone", aluno.getTelefone());
        String[] args = {String.valueOf(aluno.getId())};

        getWritableDatabase().update("Alunos", values, "id=?", args);
    }

    public boolean isAluno(String telefone) {
        String[] args = {telefone};
        Cursor cursor = getWritableDatabase().rawQuery("SELECT id FROM Alunos WHERE telefone = ?", args);
        boolean b = cursor.moveToFirst();
        return b;
    }
}
