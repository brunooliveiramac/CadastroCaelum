package br.com.cadastrocaelum.src.cadastro.src.cadastro;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import java.io.ByteArrayOutputStream;

import br.com.cadastrocaelum.R;
import br.com.cadastrocaelum.src.cadastro.src.modelo.Aluno;

/**
 * Created by Bruno - PC on 29/08/2015.
 */
public class FormularioHelper {

    EditText nome;
    EditText site;
    EditText telefone;
    EditText endereco;
    RatingBar nota;
    ImageView imagem;
    private Aluno aluno;

    public FormularioHelper(Formulario formulario){

        nome = (EditText)formulario.findViewById(R.id.nome);
        site = (EditText)formulario.findViewById(R.id.site);
        telefone = (EditText) formulario.findViewById(R.id.telefone);
        endereco = (EditText)formulario.findViewById(R.id.endereco);
        nota = (RatingBar) formulario.findViewById(R.id.nota);
        imagem = (ImageView)formulario.findViewById(R.id.foto);
        aluno = new Aluno();
    }

    public Aluno pegaAulunoForm(){
        aluno.setNome(nome.getText().toString());
        aluno.setSite(site.getText().toString());
        aluno.setEndereco(endereco.getText().toString());
        aluno.setTelefone(telefone.getText().toString());
        aluno.setNota(Double.valueOf(nota.getRating()));




        return aluno;
    }

    public void colocaAlunoForm(Aluno alunoserializable) {
        aluno = alunoserializable;
        nome.setText(alunoserializable.getNome());
        site.setText(alunoserializable.getSite());
        telefone.setText(alunoserializable.getTelefone());
        endereco.setText(alunoserializable.getEndereco());
        nota.setRating(alunoserializable.getNota().floatValue());

        if(aluno.getFoto() != null){
            carregaImagem(alunoserializable.getFoto());
        }
    }


    public ImageView getFoto(){
        return imagem;
    }

    public void carregaImagem(String caminhoSD) {
        aluno.setFoto(caminhoSD);
        Bitmap bitmap = BitmapFactory.decodeFile(caminhoSD);
        Bitmap imgReduzida =   Bitmap.createScaledBitmap(bitmap, 100, 100, true);
        imagem.setImageBitmap(imgReduzida);

    }
}
