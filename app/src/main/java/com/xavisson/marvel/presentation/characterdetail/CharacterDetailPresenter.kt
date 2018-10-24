package com.xavisson.marvel.presentation.characterdetail

import com.xavisson.marvel.base.BasePresenter
import com.xavisson.marvel.domain.logger.Logger

class CharacterDetailPresenter : BasePresenter<CharacterDetailView>() {

    var characterId: Int? = null
        set(value) {
            field = value
            Logger.d { "xtest_id: $characterId" }
        }


}