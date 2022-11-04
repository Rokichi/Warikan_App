package jp.co.tbdeveloper.warikanapp.feature_roulette.parser

import android.os.Bundle
import androidx.navigation.NavType
import com.google.gson.Gson
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Member

class MemberArrayType : NavType<Array<Member>>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): Array<Member>? {
        return bundle.getParcelableArray(key) as Array<Member>
    }

    override fun parseValue(value: String): Array<Member> {
        return Gson().fromJson(value, Array<Member>::class.java)
    }

    override fun put(bundle: Bundle, key: String, values: Array<Member>) {
        bundle.putParcelableArray(key, values)
    }
}