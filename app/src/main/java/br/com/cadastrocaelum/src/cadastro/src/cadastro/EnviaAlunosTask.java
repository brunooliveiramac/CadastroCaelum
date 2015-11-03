package br.com.cadastrocaelum.src.cadastro.src.cadastro;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;

import br.com.cadastrocaelum.src.cadastro.src.modelo.Aluno;

/**
 * Created by Bruno - PC on 31/08/2015.
 */
public class EnviaAlunosTask extends AsyncTask<Integer, Double, String> {

    private Activity activity;
    private   ProgressDialog aguarde;

    public EnviaAlunosTask(Activity activity) {
        this.activity = activity;
    }


    @Override
    protected void onPreExecute() {
      aguarde = ProgressDialog.show(activity, "Aguarde", "Enviando..", true, true);
    }

    @Override
    protected String doInBackground(Integer... params) {
        String Url = "http://caelum.com.br/mobile";

        AlunoDao dao = new AlunoDao(activity);
        List<Aluno> alunos = dao.getLista();
        dao.close();

        String dadosJSON  = new AlunoConverter().toJSON(alunos);//Converter Alunos para JSON

        WebCliente cliente = new WebCliente(Url);
        String respostaJson =  cliente.post(dadosJSON);

        return respostaJson;
    }

    @Override
    protected void onPostExecute(String s) {
        Toast.makeText(activity, s, Toast.LENGTH_LONG).show();
        aguarde.dismiss();
    }
}
