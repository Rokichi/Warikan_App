package jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.members

import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Member


sealed class MemberEvent {
    object AddMemberEvent : MemberEvent()
    data class DeleteMemberEvent(val index: Int) : MemberEvent()
    data class EditMemberEvent(val value: String, val index: Int) : MemberEvent()
    data class EditTotalEvent(val value: String) : MemberEvent()
    data class LoadMemberEvent(val members: List<Member>) : MemberEvent()
    object NextPageEvent : MemberEvent()
}