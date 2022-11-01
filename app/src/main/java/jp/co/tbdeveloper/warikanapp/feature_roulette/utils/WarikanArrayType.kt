package jp.co.tbdeveloper.warikanapp.feature_roulette.utils

import android.os.Bundle
import androidx.navigation.NavType
import com.google.gson.Gson
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Warikan

class WarikanArrayType : NavType<Array<Warikan>>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): Array<Warikan>? {
        return bundle.getParcelableArray(key) as Array<Warikan>
    }
    override fun parseValue(value: String): Array<Warikan> {
        return Gson().fromJson(value, Array<Warikan>::class.java)
    }
    override fun put(bundle: Bundle, key: String, values: Array<Warikan>) {
        bundle.putParcelableArray(key, values)
    }
}