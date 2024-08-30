package com.example.gpsjp

import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt


class Local {
    //atributos
    var lat: Double = -31.76689291
    var lon: Double = -52.35216626
    var nomesSalvos = arrayOf("", "", "", "")
    var latSalvas = arrayOf(-31.76689291, -31.76689291, -31.76689291, -31.76689291)
    var lonSalvas = arrayOf(-52.35216626, -52.35216626, -52.35216626, -52.35216626)
    var flagMarker: Int = 0
    var flagPoligono= arrayOf(false, false, false, false, false)

    //Metodos
    fun separadorDeString(teste: String, c: Int) {
        val separada: List<String> = teste.split(";")
        nomesSalvos[c] = separada[0]
        latSalvas[c] = separada[1].toDouble()
        lonSalvas[c] = separada[2].toDouble()
    }


    fun distanciaDePontos(l1: Int, l2: Int): Double {
        var lat1: Double = latSalvas[l1]
        val lon1: Double = lonSalvas[l1]
        var lat2: Double = latSalvas[l2]
        val lon2: Double = lonSalvas[l2]

        val dLat = (lat2 - lat1) * Math.PI / 180
        val dLon = (lon2 - lon1) * Math.PI / 180

        lat1 = lat1 * Math.PI / 180
        lat2 = lat2 * Math.PI / 180

        val a = sin(dLat / 2) * sin(dLat / 2) +
                sin(dLon / 2) * sin(dLon / 2) * cos(lat1) * cos(lat2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return (6371 * c) * 1000
    }

    private fun alinhamento(l1: Int, l2: Int, l3: Int): Boolean {
        val lat1: Int = latSalvas[l1].toInt()
        val lon1: Int = lonSalvas[l1].toInt()
        val lat2: Int = latSalvas[l2].toInt()
        val lon2: Int = lonSalvas[l2].toInt()
        val lat3: Int = latSalvas[l3].toInt()
        val lon3: Int = lonSalvas[l3].toInt()
        if (((lat1 * lon2) + (lat3 * lon1) + (lat2 * lon3) - (lat3 * lon2) - (lat1 * lon3) - (lat2 * lon1)) == 0) {
            return true

        } else {
            return false

        }

    }

    fun areaTriangulo(l1: Int, l2: Int, l3: Int): Double {

        val d1 = distanciaDePontos(l1, l2)
        val d2 = distanciaDePontos(l1, l3)
        val d3 = distanciaDePontos(l2, l3)
        if(alinhamento(l1, l2, l3) ){

            val s = (d1 + d2 + d3) / 2
            return sqrt(s * (s - d1) * (s - d2) * (s - d3))
        }else{

            return 1.0
        }

    }

    fun areaQuadrilatero(): Double {

        if (alinhamento(0, 1, 2) || alinhamento(0, 2, 3) || alinhamento(1,2,3) || alinhamento(0, 2,3)) {
            val l = arrayOf(0, 1, 2, 3)
            val distancia = arrayOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
            var contador = 0
            for (i in 0..2) {
                for (j in 0 + i..3) {
                    if (j != i) {
                        distancia[contador] = distanciaDePontos(l[i], l[j])
                        contador++
                    }
                }
            }

            val resultado = areaTriangulo(0, 1, 2) * 2
            return resultado
        } else {
            return 0.0
        }

    }
}



val localizacao=Local()