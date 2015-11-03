package br.com.cadastrocaelum.src.cadastro.src.cadastro;

import org.json.JSONException;
import org.json.JSONStringer;

import java.util.List;

import br.com.cadastrocaelum.src.cadastro.src.modelo.Aluno;

/**
 * Created by Bruno - PC on 31/08/2015.
 */
public class AlunoConverter  {


    public String toJSON(List<Aluno> alunos) {

        try {

            JSONStringer jsonStringer = new JSONStringer();

            jsonStringer.object().key("list").array();
            jsonStringer.object().key("aluno").array();
            for(Aluno aluno : alunos){
                jsonStringer.object();

                jsonStringer.key("nome").value(aluno.getNome());
                jsonStringer.key("nota").value(aluno.getNota());


                jsonStringer.endObject();
            }

            jsonStringer.endArray().endObject();

            jsonStringer.endArray().endObject();

            return jsonStringer.toString();

        } catch (JSONException e) {
            throw  new RuntimeException(e);
        }
    }
}
