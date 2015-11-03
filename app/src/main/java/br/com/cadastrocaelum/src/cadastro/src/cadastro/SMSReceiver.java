package br.com.cadastrocaelum.src.cadastro.src.cadastro;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;

import br.com.cadastrocaelum.R;

/**
 * Created by Bruno - PC on 31/08/2015.
 */
public class SMSReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
            Object[] mensagem = (Object[]) intent.getExtras().get("pdus");
            byte[]  primeiraMsg = (byte[]) mensagem[0];

            SmsMessage sms = SmsMessage.createFromPdu(primeiraMsg);
            String telefone = sms.getDisplayOriginatingAddress();

            AlunoDao dao = new AlunoDao(context);

            boolean eAluno = dao.isAluno(telefone);
            dao.close();

            if(eAluno){
                 // Toca Audio
                 // MediaPlayer mediaPlayer = MediaPlayer.create(context, R.pasta.audiomp3);
                 // mediaPlayer.start();
            }




    }
}
