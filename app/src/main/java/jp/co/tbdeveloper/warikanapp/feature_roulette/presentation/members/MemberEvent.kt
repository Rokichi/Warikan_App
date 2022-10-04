package jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.members


sealed class MemberEvent {
    object AddMember: MemberEvent()
    data class DeleteMemberEvent(val index:Int):MemberEvent()
    data class EditMemberEvent(val value: String, val index:Int): MemberEvent()
    data class EditTotalEvent(val value: String): MemberEvent()
    object NextPageEvent: MemberEvent()
}