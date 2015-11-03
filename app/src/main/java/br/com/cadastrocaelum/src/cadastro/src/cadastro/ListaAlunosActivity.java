package br.com.cadastrocaelum.src.cadastro.src.cadastro;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;

import br.com.cadastrocaelum.R;
import br.com.cadastrocaelum.src.cadastro.src.modelo.Aluno;

/**
 * Created by Bruno - PC on 28/08/2015.
 */
public class ListaAlunosActivity extends Activity {
    ListView lista;
    private Aluno aluno;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listagem_alunos);
        lista  = (ListView)findViewById(R.id.lista);
        registerForContextMenu(lista);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Aluno alunoClicado = (Aluno)parent.getItemAtPosition(position);
                Intent intent = new Intent(ListaAlunosActivity.this, Formulario.class);
                intent.putExtra("alunoSelecionado", alunoClicado);

                startActivity(intent);


            }
        });

        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

         @Override
         public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
             aluno = (Aluno)parent.getItemAtPosition(position);

             return false;
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem ligar = menu.add("Ligar");
        ligar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent irDiscagem = new Intent(Intent.ACTION_CALL);
                Uri discarPara = Uri.parse("tel:"+aluno.getTelefone());
                irDiscagem.setData(discarPara);

                startActivity(irDiscagem);

                return false;
            }
        });


        menu.add("Enviar SMS");
        MenuItem site =   menu.add("Navegar no Site");
        site.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
              Intent irSite = new Intent(Intent.ACTION_VIEW);

              Uri siteAluno = Uri.parse("http://:" + aluno.getSite());
              irSite.setData(siteAluno);
              startActivity(irSite);


                return false;
            }
        });
        menu.add("Ver no Mapa");
        MenuItem deletar =  menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
           @Override
           public boolean onMenuItemClick(MenuItem item) {
               AlunoDao dao = new AlunoDao(ListaAlunosActivity.this);
               dao.deletar(aluno);
               carregarLista();
               dao.close();
               return false;
           }
       });
        menu.add("Enviar Email");
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    protected void onResume() {
        super.onResume();

        carregarLista();

    }

    private void carregarLista() {
        AlunoDao dao =  new AlunoDao(this);
        List<Aluno> alunos = dao.getLista();
        dao.close();
        //Especialista que entende Java e XML


        ListaAlunosAdapter adapter =  new ListaAlunosAdapter(alunos, this);

        lista.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.novo:
                Intent irParaForm = new  Intent(this, Formulario.class);
                startActivity(irParaForm);

                break;

            case R.id.sincronizar:

                EnviaAlunosTask enviaAlunosTask = new EnviaAlunosTask(ListaAlunosActivity.this);
                enviaAlunosTask.execute();


                break;
        }

        return super.onOptionsItemSelected(item);

    }
}
