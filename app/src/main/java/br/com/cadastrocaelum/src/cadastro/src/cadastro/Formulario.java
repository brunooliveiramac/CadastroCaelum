package br.com.cadastrocaelum.src.cadastro.src.cadastro;

import android.app.Activity;
import android.content.Intent;
import android.media.Rating;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import java.io.File;
import java.io.Serializable;

import br.com.cadastrocaelum.R;
import br.com.cadastrocaelum.src.cadastro.src.modelo.Aluno;

/**
 * Created by Bruno - PC on 29/08/2015.
 */
public class Formulario extends Activity {

    private FormularioHelper formularioHelper;
    private  String caminhoSD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formulario);


          Intent intent = getIntent();
          final Aluno alunoserializable = (Aluno) intent.getSerializableExtra("alunoSelecionado");


          formularioHelper = new FormularioHelper(this);







          Button btsalvar = (Button) findViewById(R.id.btnsalvar);

        if(alunoserializable != null){
            btsalvar.setText("Alterar");
            formularioHelper.colocaAlunoForm(alunoserializable);
        }

          btsalvar.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Aluno pegaAluno = formularioHelper.pegaAulunoForm();
                  AlunoDao dao = new AlunoDao(Formulario.this);



                      if(alunoserializable == null){
                          dao.salva(pegaAluno);
                          dao.close();
                          Toast.makeText(Formulario.this, "Salvo ", Toast.LENGTH_LONG).show();
                          finish();
                      }else{
                          pegaAluno.setId(alunoserializable.getId());
                          dao.altera(pegaAluno);
                          finish();
                      }
          }

          //camera


      });

            ImageView foto =  formularioHelper.getFoto();
            foto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent irCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    caminhoSD = Environment.getExternalStorageDirectory().toString()
                            +"/"+System.currentTimeMillis()+".png";
                    File arquivo =   new File(caminhoSD);
                    Uri fromFile =  Uri.fromFile(arquivo);
                    irCamera.putExtra(MediaStore.EXTRA_OUTPUT, fromFile);

                    startActivityForResult(irCamera, 123);//clicou OK


                }
            });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if(requestCode == 123){
        if(resultCode == Activity.RESULT_OK);
        formularioHelper.carregaImagem(caminhoSD);
    }else{
            caminhoSD = null;
    }
    }
}
