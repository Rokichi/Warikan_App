package jp.co.tbdeveloper.warikanapp.feature_roulette.domain.repository

import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.MemberEntity
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Member

/**
 * Member -> Entity
 */
object MemberEntityFactory {
    /**
     * MemberList -> MemberEntityList
     *
     * @param rouletteId ひも付きルーレットID
     * @param members 保存したいデータ
     * @return MemberEntityList
     */
    fun create(rouletteId: Long, members: List<Member>): List<MemberEntity> {
        return List(members.size) { i ->
            MemberEntity(0, rouletteId, members[i].name, members[i].color)
        }
    }
}