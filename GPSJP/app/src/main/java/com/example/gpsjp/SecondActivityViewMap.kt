package com.example.gpsjp


import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class SecondActivityViewMap : AppCompatActivity() {



    //@SuppressLint("SetJavaScriptEnabled")
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_second_view_map)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val wb: WebView = findViewById(R.id.wv)
        wb.settings.javaScriptEnabled = true


        when(localizacao.flagMarker){
            0->wb.loadUrl("file:///android_asset/mapa.html?${localizacao.flagMarker}&${localizacao.lat}&${localizacao.lon}")
            1->wb.loadUrl("file:///android_asset/mapa.html?${localizacao.flagMarker}&${localizacao.lat}&${localizacao.lon}")
            2->
                if(localizacao.flagPoligono[0]&&localizacao.flagPoligono[1]){
                    wb.loadUrl("file:///android_asset/mapa.html?${localizacao.flagMarker}&${localizacao.latSalvas[0]}&${localizacao.lonSalvas[0]}&${localizacao.latSalvas[1]}&${localizacao.lonSalvas[1]}")
                }else if(localizacao.flagPoligono[0]&&localizacao.flagPoligono[2]){
                    wb.loadUrl("file:///android_asset/mapa.html?${localizacao.flagMarker}&${localizacao.latSalvas[0]}&${localizacao.lonSalvas[0]}&${localizacao.latSalvas[2]}&${localizacao.lonSalvas[2]}")
                }else if(localizacao.flagPoligono[0]&&localizacao.flagPoligono[3]){
                    wb.loadUrl("file:///android_asset/mapa.html?${localizacao.flagMarker}&${localizacao.latSalvas[0]}&${localizacao.lonSalvas[0]}&${localizacao.latSalvas[3]}&${localizacao.lonSalvas[3]}")
                }else if(localizacao.flagPoligono[1]&&localizacao.flagPoligono[2]){
                    wb.loadUrl("file:///android_asset/mapa.html?${localizacao.flagMarker}&${localizacao.latSalvas[1]}&${localizacao.lonSalvas[1]}&${localizacao.latSalvas[2]}&${localizacao.lonSalvas[2]}")
                }else if(localizacao.flagPoligono[1]&&localizacao.flagPoligono[3]){
                    wb.loadUrl("file:///android_asset/mapa.html?${localizacao.flagMarker}&${localizacao.latSalvas[1]}&${localizacao.lonSalvas[1]}&${localizacao.latSalvas[3]}&${localizacao.lonSalvas[3]}")
                }else if(localizacao.flagPoligono[2]&&localizacao.flagPoligono[3]){
                    wb.loadUrl("file:///android_asset/mapa.html?${localizacao.flagMarker}&${localizacao.latSalvas[2]}&${localizacao.lonSalvas[2]}&${localizacao.latSalvas[3]}&${localizacao.lonSalvas[3]}")
                }
            3->
                if(localizacao.flagPoligono[0]&& localizacao.flagPoligono[1] && localizacao.flagPoligono[2]){
                    wb.loadUrl("file:///android_asset/mapa.html?${localizacao.flagMarker}&${localizacao.latSalvas[0]}&${localizacao.lonSalvas[0]}&${localizacao.latSalvas[1]}&${localizacao.lonSalvas[1]}&${localizacao.latSalvas[2]}&${localizacao.lonSalvas[2]}")
                }else if(localizacao.flagPoligono[0]&& localizacao.flagPoligono[1] && localizacao.flagPoligono[3]){
                    wb.loadUrl("file:///android_asset/mapa.html?${localizacao.flagMarker}&${localizacao.latSalvas[0]}&${localizacao.lonSalvas[0]}&${localizacao.latSalvas[1]}&${localizacao.lonSalvas[1]}&${localizacao.latSalvas[3]}&${localizacao.lonSalvas[3]}")
                }else if(localizacao.flagPoligono[0] && localizacao.flagPoligono[2] && localizacao.flagPoligono[3]){
                     wb.loadUrl("file:///android_asset/mapa.html?${localizacao.flagMarker}&${localizacao.latSalvas[0]}&${localizacao.lonSalvas[0]}&${localizacao.latSalvas[2]}&${localizacao.lonSalvas[2]}&${localizacao.latSalvas[3]}&${localizacao.lonSalvas[3]}")
                }else if(localizacao.flagPoligono[1] && localizacao.flagPoligono[2] && localizacao.flagPoligono[3]){
                      wb.loadUrl("file:///android_asset/mapa.html?${localizacao.flagMarker}&${localizacao.latSalvas[1]}&${localizacao.lonSalvas[1]}&${localizacao.latSalvas[2]}&${localizacao.lonSalvas[2]}&${localizacao.latSalvas[3]}&${localizacao.lonSalvas[3]}")
                }
            4-> wb.loadUrl("file:///android_asset/mapa.html?${localizacao.flagMarker}&${localizacao.latSalvas[0]}&${localizacao.lonSalvas[0]}&${localizacao.latSalvas[1]}&${localizacao.lonSalvas[1]}&${localizacao.latSalvas[2]}&${localizacao.lonSalvas[2]}&${localizacao.latSalvas[3]}&${localizacao.lonSalvas[3]}")
        }



        wb.webViewClient = object : WebViewClient() {
            @Deprecated("Deprecated in Java")
            override fun shouldOverrideUrlLoading(view: WebView?, url: String): Boolean {
                view?.loadUrl(url)
                return true
            }
        }


    }

}
