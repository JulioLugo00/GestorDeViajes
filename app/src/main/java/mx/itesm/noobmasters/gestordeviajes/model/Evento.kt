package mx.itesm.noobmasters.gestordeviajes.model




class Evento(val nombre:String?=""
             , val tipo:Salida?=Salida.VIAJES,
             val presupuesto:Int?=0,
             val ubicacion:String?="",
             val imagen:String?="",
             val fecha:String?=""
) {
}