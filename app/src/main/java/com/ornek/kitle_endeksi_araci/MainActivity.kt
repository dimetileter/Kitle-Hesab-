package com.ornek.kitle_endeksi_araci


import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ornek.kitle_endeksi_araci.R
import kotlinx.android.synthetic.main.activity_main.txt_boyveri
import kotlinx.android.synthetic.main.activity_main.txt_idealkilosonuc
import kotlinx.android.synthetic.main.activity_main.txt_kilo
import kotlinx.android.synthetic.main.activity_main.txt_kiloveri
import kotlinx.android.synthetic.main.activity_main.txt_sonucbilgimetni
import kotlinx.android.synthetic.main.activity_main.txt_vkesonuc
import kotlinx.android.synthetic.main.activity_main.txt_vyasonuc
import kotlin.time.times

class MainActivity : AppCompatActivity() {

    private var Cinsiyet: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cinsiyet : RadioGroup = findViewById(R.id.cinsiyetRadioGroup)
        cinsiyet.setOnCheckedChangeListener { _ , checkedId ->
            when(checkedId)
            {
                R.id.erkekRadioButton -> {Cinsiyet = "Erkek"}
                R.id.kadınRadioButton -> {Cinsiyet = "Kadın"}
            }
        }

    }

    /**
        Aşağıda yer alan tüm fonksiyonlara kilo ve boy null kontrolü yapılıp gidildiği için !! koydum.
        En başta null olabilir şeklinde tanımladığımız için çarpma ya da bölmelerde hatalar veriyordu.
        Null değer ile matematiksel işlem yapılamaz şeklinde
    */

    //Vücut kitle endeksi hesabı
    private fun vke(boycm: Int , kilo: Float): String
    {
        val boy : Float = (boycm.toFloat()/100)
        return (kilo/(boy * boy)).toString().take(5)
    }

    //Vücut yüzey alanı hesabı
    private fun vya(boycm: Int , kilo: Float): String
    {
        return kotlin.math.sqrt((boycm * kilo).toFloat()/3600).toString().take(4)
    }

    //İdeal kilo hesabı
    private fun IdealKilo(boycm: Int): String
    {
        if(Cinsiyet == "Erkek")
        {
            return (50 + 2.4*((boycm/2.54)-60)).toFloat().toString().take(4)
        }
        else if(Cinsiyet == "Kadın")
        {
            return (45.5 + 2.4*((boycm/2.54).toFloat()-60)).toString().take(4)
        }
        return ""
    }

    //Kontroller
    private fun kontrollerVeSonuc(boycm: Int? , kilo: Float?)
    {
        if((boycm==null || kilo==null))
        {
            Toast.makeText(this,"Değerleri giriniz",Toast.LENGTH_SHORT).show()
        }
        else if(boycm<=0 || kilo<=0)
        {
            Toast.makeText(this,"Gçerli bir sayı giriniz",Toast.LENGTH_SHORT).show()
        }
        else if(Cinsiyet==null)
        {
            Toast.makeText(this,"Cinsiyetinizi seçiniz",Toast.LENGTH_SHORT).show()
        }
        else
        {
            txt_vkesonuc.text = vke(boycm,kilo)
            txt_vyasonuc.text = vya(boycm,kilo)
            txt_idealkilosonuc.text = IdealKilo(boycm)
        }
    }

    //Hespla butonu
    fun btn_hesapla(view: View)
    {
        var boycm = txt_boyveri.text.toString().toIntOrNull()
        var kilo = txt_kiloveri.text.toString().toFloatOrNull()

        kontrollerVeSonuc(boycm,kilo)
        //Eğer null ise boş dönsün ki çökmesin
        var aralik = txt_vkesonuc.text.toString().toFloatOrNull() ?: return


        //Kilo aralığına göre bilgi metni
        if (aralik < 18.5){
            txt_sonucbilgimetni.text="İdeal kilo eşiğinin altındasınız. Kilo almayı deneyin"
        }
        else if(18.5<=aralik  && aralik<=24.9)
        {
            txt_sonucbilgimetni.text="İdela kilo aralığındasınız. Kilonuzu korumak için dengeli ve sağlıklı beslenmeye çalışın"
        }
        else if(24.9<aralik && aralik<=29.9)
        {
            txt_sonucbilgimetni.text="İdeal kilo eşiğinin üstündesiniz. Kilo vermeyi deneyin"
        }
        else if(29.9<aralik && aralik<=34.9)
        {
            txt_sonucbilgimetni.text="1. dereceden obazite aralığındasınız. Sağlığınızı korumak için kilo vermelisiniz"
        }
        else if(34.9<aralik && aralik<=39.9)
        {
            txt_sonucbilgimetni.text="2. dereceden obazite aralığındasınız. Sağlığınızı korumak için kilo vermelisiniz. Bir uzmandan yardım alabilirsiniz"
        }
        else
        {
            txt_sonucbilgimetni.text="3. dereceden obazite aralığındasınız. Sağlığınızı korumak için derhal bir uzmandan yardım almalı ve kilo vermelisiniz"
        }

    }

    //Sıfırla butonu
    fun btn_sifirla(view: View)
    {
        txt_boyveri.text = null
        txt_kiloveri.text = null
        txt_vkesonuc.text = null
        txt_vyasonuc.text = null
        txt_idealkilosonuc.text = null
        txt_sonucbilgimetni.text = null
    }
}


