package com.example.gpsjp

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.gpsjp.R.*
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStreamReader
import kotlin.also as also1


class MainActivity : AppCompatActivity(), LocationListener
{
    private val locaisSalvos=arrayOf("arquivo0","arquivo1","arquivo2","arquivo3") // Nomes dos arquivos
    private var contador: Int=0
    private lateinit var locationManager: LocationManager
    private lateinit var tvGpsLocation: TextView
    private val locationPermissionCode = 2


    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)
        title = "Location App"



        getLocation()
        //Escreve os locais salvos na tela ao inciar o app
        for(i in 0..3){
            try {
                lerArquivo()
            }catch (e:Exception){
                try {
                    openFileOutput(locaisSalvos[contador], Context.MODE_PRIVATE)
                } catch (e: FileNotFoundException){
                    e.printStackTrace()
                }catch (e: NumberFormatException){
                    e.printStackTrace()
                }catch (e: IOException){
                    e.printStackTrace()
                }catch (e: Exception){
                    e.printStackTrace()
                }
            }
        }

        //Processo de achar localização GPS
        val btnshow = findViewById<Button>(id.btnshow)
        btnshow.setOnClickListener {
            getLocation()
        }//Fim do processo de achar GPS

        //Inicio dos processos de salvamento e leitura de arquivo
        val fileName = findViewById<EditText>(id.editFile)
        val btnSave = findViewById<Button>(id.btnSave)

        btnSave.setOnClickListener{

                val file:String = locaisSalvos[contador]
                localizacao.nomesSalvos[contador] = fileName.text.toString()
                val data:String = localizacao.nomesSalvos[contador]+";"+localizacao.lat.toString()+";"+ localizacao.lon.toString()
                val fileOutputStream:FileOutputStream
                try {
                    fileOutputStream = openFileOutput(file, Context.MODE_PRIVATE)
                    fileOutputStream.write(data.toByteArray())
                } catch (e: FileNotFoundException){
                    e.printStackTrace()
                }catch (e: NumberFormatException){
                    e.printStackTrace()
                }catch (e: IOException){
                    e.printStackTrace()
                }catch (e: Exception){
                    e.printStackTrace()
                }
                Toast.makeText(applicationContext,"Local salvo",Toast.LENGTH_LONG).show()
                fileName.text.clear()

            lerArquivo()

        }//Fim dos processos de salvamento e leitura de arquivo


        //Inicio da seleção e calculo da distancia entre pontos
        val radioGroup1 = findViewById<RadioGroup>(R.id.radioGroup1)
        val radioGroup2 = findViewById<RadioGroup>(R.id.radioGroup2)
        val btnCalculoDistancia = findViewById<Button>(id.btnDistancia)

        btnCalculoDistancia.setOnClickListener {

            val radioID1 = radioGroup1.checkedRadioButtonId
            val radiobtnP10 = findViewById<View>(id.localP1_0) as RadioButton
            val radiobtnP11 = findViewById<View>(id.localP1_1) as RadioButton
            val radiobtnP12 = findViewById<View>(id.localP1_2) as RadioButton
            val radiobtnP13 = findViewById<View>(id.localP1_3) as RadioButton
            val radiobtnP20 = findViewById<View>(id.localP2_0) as RadioButton
            val radiobtnP21 = findViewById<View>(id.localP2_1) as RadioButton
            val radiobtnP22 = findViewById<View>(id.localP2_2) as RadioButton
            val radiobtnP23 = findViewById<View>(id.localP2_3) as RadioButton

            var l1 = 1
            var l2 = 0


            when(radioID1.toString()){
                radiobtnP10.id.toString()-> l1=0
                radiobtnP11.id.toString()-> l1=1
                radiobtnP12.id.toString()-> l1=2
                radiobtnP13.id.toString()-> l1=3
            }
            val radioID2 = radioGroup2.checkedRadioButtonId
            println(radioID1)
            println(l1)

            when(radioID2.toString()){
                radiobtnP20.id.toString() -> l2=0
                radiobtnP21.id.toString() -> l2=1
                radiobtnP22.id.toString() -> l2=2
                radiobtnP23.id.toString() -> l2=3
            }
            println(radioID2)
            println(l2)
            findViewById<TextView>(id.distancia).also1{
                var resultado=localizacao.distanciaDePontos(l1,l2)
                if((resultado*10).toInt()%2!=0 && l1!=l2)
                {
                    resultado=(resultado+1)/10
                }
                it.text= resultado.toInt().toString()+" metros"

            }
        }
        //Fim da seleção e calculo da distancia entre pontos


        //Inicio do processo de calculo de áreas

        val checkbox0= findViewById<CheckBox>(id.ponto1)
        checkbox0.text = localizacao.nomesSalvos[0]

        val checkbox1= findViewById<CheckBox>(id.ponto2)
        checkbox1.text = localizacao.nomesSalvos[1]

        val checkbox2= findViewById<CheckBox>(id.ponto3)
        checkbox2.text = localizacao.nomesSalvos[2]

        val checkbox3= findViewById<CheckBox>(id.ponto4)
        checkbox3.text = localizacao.nomesSalvos[3]

        val btnarea = findViewById<Button>(R.id.btnarea)
        btnarea.setOnClickListener {
            var resultado=0.0
            if(checkbox0.isChecked && checkbox1.isChecked && checkbox2.isChecked && checkbox3.isChecked){
                resultado= localizacao.areaQuadrilatero()

            }else if(checkbox0.isChecked && checkbox1.isChecked && checkbox2.isChecked){
                resultado= localizacao.areaTriangulo(0,1,2)

            }else if(checkbox0.isChecked && checkbox1.isChecked && checkbox3.isChecked){
                resultado= localizacao.areaTriangulo(0,1,3)

            }else if(checkbox0.isChecked && checkbox2.isChecked && checkbox3.isChecked){
                resultado= localizacao.areaTriangulo(0,2,3)

            }else if(checkbox1.isChecked && checkbox2.isChecked && checkbox3.isChecked){
                resultado= localizacao.areaTriangulo(1,2,3)

            }else{
                Toast.makeText(applicationContext,"Selecione pelo menos 3",Toast.LENGTH_LONG).show()

            }

            findViewById<TextView>(id.area).also1{
                it.text=resultado.toInt().toString()+" metros^2"
            }
        }
        //Fim pro processo de calculo de área

        //Botão de abrir a secondActivityViewMap
        //Botão de marker
        val btnViewMapMarker = findViewById<Button>(id.btnViewMap)
        btnViewMapMarker.setOnClickListener {
            localizacao.flagMarker=0
            val intent =  Intent(this, SecondActivityViewMap::class.java)
            startActivity(intent)
        }
        //Botão de Poligono
        val btnViewMapPoligono = findViewById<Button>(id.btnMapPoligono)
        btnViewMapPoligono.setOnClickListener {
            var c=0
            if(checkbox0.isChecked){
                c++
                localizacao.flagPoligono[0]=true
            }
            if(checkbox1.isChecked){
                c++
                localizacao.flagPoligono[1]=true
            }
            if(checkbox2.isChecked){
                c++
                localizacao.flagPoligono[2]=true
            }
            if(checkbox3.isChecked){
                c++
                localizacao.flagPoligono[3]=true
            }
            localizacao.flagMarker=c

            val intent =  Intent(this, SecondActivityViewMap::class.java)
            startActivity(intent)
        }

    }//Fim do OnCreate

    @SuppressLint("SetTextI18n")
    private fun lerArquivo(){
        //Escreve os locais salvos na tela
        val filename = locaisSalvos[contador]
        if(filename.trim()!=""){
            val fileInputStream: FileInputStream? = openFileInput(filename)
            val inputStreamReader = InputStreamReader(fileInputStream)
            val bufferedReader = BufferedReader(inputStreamReader)
            val stringBuilder: StringBuilder = StringBuilder()
            var text: String?
            while (run {
                    text = bufferedReader.readLine()
                    text
                } != null) {
                stringBuilder.append(text)
            }
            //Mostrando na tela e salvando o num array pro uso durante o uso do aplicativo
            when (contador) {
                0 -> findViewById<TextView>(id.arquivo0).also1 {
                    localizacao.separadorDeString(stringBuilder.toString(), contador)
                    it.text = localizacao.nomesSalvos[contador]+"\n"+ localizacao.latSalvas[contador]+"\n"+ localizacao.lonSalvas[contador]

                }

                1 -> findViewById<TextView>(id.arquivo1).also1 {
                    localizacao.separadorDeString(stringBuilder.toString(), contador)
                    it.text = localizacao.nomesSalvos[contador]+"\n"+ localizacao.latSalvas[contador]+"\n"+ localizacao.lonSalvas[contador]
                }

                2 -> findViewById<TextView>(id.arquivo2).also1 {
                    localizacao.separadorDeString(stringBuilder.toString(), contador)
                    it.text = localizacao.nomesSalvos[contador]+"\n"+ localizacao.latSalvas[contador]+"\n"+ localizacao.lonSalvas[contador]
                }

                3 -> findViewById<TextView>(id.arquivo3).also1 {
                    localizacao.separadorDeString(stringBuilder.toString(), contador)
                    it.text = localizacao.nomesSalvos[contador]+"\n"+ localizacao.latSalvas[contador]+"\n"+ localizacao.lonSalvas[contador]
                }
            }
        }

        if(contador==3) {
            contador=0
        } else {
            contador++
        }
    }


    //Inicio dos processos de conseguir um sinal de gps
    private fun getLocation() {
        Toast.makeText(this, "Aguardando Sinal de GPS", Toast.LENGTH_SHORT).show()

        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        if ((ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED)
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                locationPermissionCode
            )
        }else{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)
        }

    }
    @SuppressLint("SetTextI18n")
    override fun onLocationChanged(location: Location) {
        localizacao.lon=location.longitude
        localizacao.lat=location.latitude


        tvGpsLocation = findViewById(id.textView)
        tvGpsLocation.text = "Latitude: \n" + localizacao.lat + "\nLongitude: \n" + localizacao.lon
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == locationPermissionCode)
        {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permissao concedida", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this, "Permissao negada", Toast.LENGTH_SHORT).show()
            }
        }
    }
    //Fim dos processos de conseguir uma localização de GPS




}