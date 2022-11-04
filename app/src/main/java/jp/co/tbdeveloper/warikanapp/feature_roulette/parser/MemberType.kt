package jp.co.tbdeveloper.warikanapp.feature_roulette.utils

import android.os.Bundle
import androidx.navigation.NavType
import com.google.gson.Gson
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Member

class MemberType : NavType<Member>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): Member? {
        return bundle.getParcelable(key)
    }
    override fun parseValue(value: String): Member {
        return Gson().fromJson(value, Member::class.java)
    }
    override fun put(bundle: Bundle, key: String, value: Member) {
        bundle.putParcelable(key, value)
    }
}