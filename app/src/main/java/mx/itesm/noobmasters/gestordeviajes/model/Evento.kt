package mx.itesm.noobmasters.gestordeviajes.model

import java.io.Serializable


data class Evento(val nombre:String?="",
             val tipo:EventoTipo?=EventoTipo.VIAJES,
             val presupuesto:Int?=0,
             val ubicacion:String?="",
             val imagen:String?="",
             val fechaInicio:String?="",
                  val fechaFin:String?="",
                  val idUnico:String?="",
                  var cosasPorLlevar: Map<String, String>?=emptyMap<String,String>()
) :Serializable
