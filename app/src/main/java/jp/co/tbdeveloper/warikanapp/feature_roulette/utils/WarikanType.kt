package jp.co.tbdeveloper.warikanapp.feature_roulette.utils

import android.os.Bundle
import androidx.navigation.NavType
import com.google.gson.Gson
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Warikan

class WarikanType : NavType<Warikan>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): Warikan? {
        return bundle.getParcelable(key)
    }
    override fun parseValue(value: String): Warikan {
        return Gson().fromJson(value, Warikan::class.java)
    }
    override fun put(bundle: Bundle, key: String, value: Warikan) {
        bundle.putParcelable(key, value)
    }
}