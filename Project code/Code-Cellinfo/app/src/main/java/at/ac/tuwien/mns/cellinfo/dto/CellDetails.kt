package at.ac.tuwien.mns.cellinfo.dto

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem
import java.io.Serializable



class CellDetails(c: Cell) : Cell(c), ClusterItem, Serializable{
    var lat: Double = Double.NaN
    var lon: Double = Double.NaN

    constructor() : this(Cell())

    override fun getPosition(): LatLng {
        return LatLng(lat, lon)
    }

    fun setLocation(latlon: LatLng) {
        lat = latlon.latitude
        lon = latlon.longitude
    }

    override fun toString(): String {
        return "CellDetails(radio='$radio' ,strength=${strength}, lac=$lac, cid=$cid, mnc=$mnc, mcc=$mcc registered=$registered)"
    }

    fun toShortString(): String {
        return "radio='$radio' ,strength=${strength}, mnc=$mnc, mcc=$mcc"
    }
}
