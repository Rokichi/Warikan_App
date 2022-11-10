package com.tbdeveloper.warikanapp.feature_roulette.domain.repository

import com.tbdeveloper.warikanapp.feature_roulette.domain.model.MemberEntity
import com.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Member

/**
 * MemberEntity -> Member
 */
object MemberFactory {
    /**
     * MemberEntityList -> MemberList
     * idが一致するものをまとめる
     * @param memberEntities DBから取得してきたデータ
     * @return id毎に分けたメンバーリストリスト
     */
    fun create(memberEntities: List<MemberEntity>): List<List<Member>> {
        val membersMap = mutableMapOf<Long, MutableList<Member>>()
        for (memberEntity in memberEntities) {
            val id = memberEntity.RouletteId
            val member = convert2Member(memberEntity)
            if (membersMap.containsKey(id)) {
                membersMap[id]?.add(member)
            } else {
                membersMap[id] = mutableListOf(member)
            }
        }
        val result = membersMap.toSortedMap()
        return result.map { it.value }
    }

    private fun convert2Member(memberEntity: MemberEntity): Member {
        return Member(
            name = memberEntity.name,
            color = memberEntity.color
        )
    }
}