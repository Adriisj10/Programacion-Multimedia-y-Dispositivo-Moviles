package com.example.ligas.model

import java.io.Serializable

class LigaJSON(var idLeague: String? = null, var strLeague: String? = null, val strSport: String? = null): Serializable { // Ponemos ? = null, porque si nome lo pasas le damos el valor de nulo
}