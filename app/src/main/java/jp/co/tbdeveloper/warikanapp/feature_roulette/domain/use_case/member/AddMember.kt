package jp.co.tbdeveloper.warikanapp.feature_roulette.domain.use_case.member

import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.InvalidMemberException
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.MemberEntity
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.repository.MemberRepository

class AddMember(
    private val repository: MemberRepository
) {
    @Throws(InvalidMemberException::class)
    suspend operator fun invoke(member: MemberEntity) {
        if(member.name.isBlank()){
            throw InvalidMemberException("The name of the member can't be empty.")
        }
        repository.insertMember(member)
    }
}