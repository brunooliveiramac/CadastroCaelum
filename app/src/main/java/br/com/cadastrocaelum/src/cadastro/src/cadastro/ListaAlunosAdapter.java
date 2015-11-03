package br.com.cadastrocaelum.src.cadastro.src.cadastro;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.cadastrocaelum.R;
import br.com.cadastrocaelum.src.cadastro.src.modelo.Aluno;

/**
 * Created by Bruno - PC on 31/08/2015.
 */
public class ListaAlunosAdapter extends BaseAdapter{

    private List<Aluno> alunos;
    private Activity activity;

    public ListaAlunosAdapter(List<Aluno> alunos, Activity activity) {

        this.alunos = alunos;
        this.activity = activity;
    }

    @Override
    public int getCount() {//Quantas linhas
        return alunos.size();
    }

    @Override
    public Object getItem(int position) {
        return alunos.get(position);
    }

    @Override
    public long getItemId(int position) {
        Aluno aluno = alunos.get(position);
        return aluno.getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Aluno aluno = alunos.get(position);
        LayoutInflater layoutInflater = activity.getLayoutInflater();

        View linha = layoutInflater.inflate(R.layout.linha_listagem, null);

        TextView nome = (TextView) linha.findViewById(R.id.nome);
        nome.setText(aluno.getNome());
        ImageView image = (ImageView) linha.findViewById(R.id.foto);
        if(aluno.getFoto() != null){
            Bitmap fotoaluno = BitmapFactory.decodeFile(aluno.getFoto());
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(fotoaluno, 100, 100, true);
            image.setImageBitmap(scaledBitmap);
        }else{

            Drawable semFoto = activity.getResources().getDrawable(R.mipmap.ic_classmate);
            image.setImageDrawable(semFoto);
        }




        return linha;
    }
}
