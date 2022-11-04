package jp.co.tbdeveloper.warikanapp.feature_roulette.domain.use_case.member

import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.InvalidMemberException
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Member

class MemberValidation {
    @Throws(InvalidMemberException::class)
    operator fun invoke(members: List<Member>) {
        if ((members.filter { member -> member.name.isEmpty() }).size > 0) {
            throw InvalidMemberException("Member name must not be empty")
        }
    }
}