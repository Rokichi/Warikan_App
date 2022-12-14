package com.tbdeveloper.warikanapp.feature_roulette.domain.use_case.member

data class MemberUseCases(
    val getMembersById: GetMembersById,
    val addMember: AddMember,
    val deleteMember: DeleteMember,
    val deleteMembers: DeleteMembers,
    val getAllMembers: GetAllMembers,
    val memberValidation: MemberValidation
)