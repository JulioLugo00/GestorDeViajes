package mx.itesm.noobmasters.gestordeviajes.model




data class Evento(val nombre:String?="",
             val tipo:EventoTipo?=EventoTipo.VIAJES,
             val presupuesto:Int?=0,
             val ubicacion:String?="",
             val imagen:String?="",
             val fecha:String?=""
) {
}